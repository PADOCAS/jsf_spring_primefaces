/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Mensagem;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
public class MensagemController extends CrudImpl<Mensagem> implements Serializable {

    private static final long serialVersionUID = 1L;

    public void saveMensagensResposta(Mensagem mensagem, Mensagem mensagemOrigem) throws Exception {
        try {
            if (getSessionFactory() != null
                    && getSessionFactory().getCurrentSession() != null) {
                validaTransaction();

                //Caso houve resposta:
                if (mensagem != null) {
                    merge(mensagem);
                }

                //Salva mensagem Origem como Lida = true:
                if (mensagemOrigem != null) {
                    merge(mensagemOrigem);
                }

                executeFlushSession();
            }
        } catch (javax.persistence.OptimisticLockException ex) {
            executeRollbackTransactionInError();
            throw new javax.persistence.OptimisticLockException(ex.getMessage());
        } catch (Exception ex) {
            executeRollbackTransactionInError();
            throw new Exception(ex.getMessage());
        }
    }

    public Long getTotalNotificacoesUser(Long codigoUser) {
        try {
            if (getJdbcTemplate() != null) {
                StringBuilder sql = new StringBuilder();
                sql.append(" SELECT count(1) as qtde FROM Mensagem entity ");

                if (codigoUser != null) {
                    sql.append(" WHERE entity.usuario_dest = ").append(codigoUser);
                    sql.append(" AND entity.lida = FALSE ");
                }

                SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql.toString());

                return sqlRowSet.next() ? sqlRowSet.getLong("qtde") : 0L;
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0L;
    }

    /**
     * Retorna uma mensagem pendente para o usuário (priorizando sempre as mais
     * antigas)
     *
     * @param codigoUser
     * @return
     */
    public Mensagem getMensagemPendenteForUser(Long codigoUser) {
        Mensagem mensagem = null;

        StringBuilder sql = new StringBuilder();
        sql.append(" FROM Mensagem entity ");

        if (codigoUser != null) {
            sql.append(" WHERE entity.usuarioDestino.codigo = ").append(codigoUser);
            sql.append("   AND entity.lida = FALSE ");
        }

        sql.append(" ORDER BY entity.dataMensagem ");

        try {
            mensagem = getUniqueObjectByQueryDinamica(sql.toString(), Mensagem.class);
        } catch (Exception ex) {
            Logger.getLogger(MensagemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mensagem;
    }

}
