/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Entidade;
import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Controller;
import com.mycompany.srv.interfaces.ServiceEntidade;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author lucia
 */
@Controller
public class EntidadeController extends CrudImpl<Entidade> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ServiceEntidade serviceEntidade;

    public Entidade findUsuarioLogado(String name) throws Exception {
        return super.findUniqueByProperty(Entidade.class, name, "login", " and entity.inativo is false");
    }

    public Date getUltimoAcessoEntidadeLogada(String name) throws Exception {
        return serviceEntidade.getUltimoAcessoEntidadeLogada(name);
    }

    public void updateUltimoAcessoUsuario(String name) throws Exception {
        serviceEntidade.updateUltimoAcessoUsuario(name);
    }

    public Boolean getExistsCpf(String cpf) throws Exception {
        if (cpf != null
                && !cpf.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append(" FROM Entidade WHERE cpf = '").append(cpf).append("' ");

            return !findListByQueryDinamica(sql.toString()).isEmpty();
        }

        return false;
    }

    public Boolean getExistsEntidadeLogin(String login) throws Exception {
        if (login != null
                && !login.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT COUNT(1) FROM Entidade entity WHERE entity.login = :login");

            Long count = (Long) getSession()
                    .createQuery(sql.toString())
                    .setParameter("login", login)
                    .getSingleResult();

            if (count > 0) {
                return true;
            }
        }

        return false;
    }

    public List<Entidade> getListEntidadeEnvioMensagem(String query, Long codigoLoginOrigem) {
        List<Entidade> listEntidade = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" FROM Entidade entity ");

        if (codigoLoginOrigem != null) {
            sql.append(" WHERE entity.codigo <> ").append(codigoLoginOrigem);
            sql.append(" AND entity.inativo = FALSE ");

            if (query != null
                    && !query.isEmpty()) {
                sql.append(" AND ((retira_acentos(upper(cast(entity.codigo as text))) ");
                sql.append(" = retira_acentos(upper('").append(query).append("'))) ");
                sql.append("  OR ");
                sql.append(" (retira_acentos(upper(entity.login)) ");
                sql.append(" LIKE retira_acentos(upper('%").append(query).append("%'))) ");
                sql.append("  OR ");
                sql.append(" (retira_acentos(upper(entity.nome)) ");
                sql.append(" LIKE retira_acentos(upper('%").append(query).append("%')))) ");
            }
        }

        sql.append(" ORDER BY entity.login, entity.codigo ");

        try {
            listEntidade = findListByQueryDinamica(sql.toString());
        } catch (Exception ex) {
            Logger.getLogger(EntidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listEntidade;
    }
}
