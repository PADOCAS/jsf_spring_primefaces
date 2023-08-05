/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao.implementacao;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import java.util.Date;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mycompany.repository.interfaces.RepositoryEntidade;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author lucia
 */
@Transactional
//@Repository é uma anotação do Spring que entende que é uma conexão com banco de dados:
@Repository
public class DAOEntidade extends CrudImpl<Object> implements RepositoryEntidade {

    private static final long serialVersionUID = 1L;

    @Override
    public Date getUltimoAcessoEntidadeLogada(String name) throws Exception {
        //Spring JDBC para buscar o último acesso com o SqlRowSet (retorna uma linha):
        SqlRowSet sqlRowSet = super.getJdbcTemplate()
                .queryForRowSet("SELECT ent.ultimoacesso FROM public.entidade ent WHERE ent.inativo is false and ent.login = ?", new Object[]{name});

        return sqlRowSet.next() ? sqlRowSet.getDate("ultimoacesso") : null;
    }

    @Override
    public void updateUltimoAcessoUsuario(String name) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE public.entidade ");
        sql.append("   SET ultimoacesso = current_timestamp ");
        sql.append(" WHERE inativo is false and login = ?  ");

        super.getJdbcTemplate().update(sql.toString(), new Object[]{name});

    }

    @Override
    public Boolean existeUsuario(String name) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM public.entidade ent WHERE ent.inativo is false and ent.login = ?");

        Long countUser = super.getJdbcTemplate().queryForObject(sql.toString(), Long.class, new Object[]{name});

        return countUser != null
                && countUser > 0;
    }

}
