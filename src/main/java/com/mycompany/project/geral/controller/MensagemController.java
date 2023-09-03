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

}
