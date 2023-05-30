/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao.implementacao;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import com.mycompany.repository.interfaces.RepositoryLogin;

/**
 *
 * @author lucia
 */
//@Repository é uma anotação do Spring que entende que é uma conexão com banco de dados:
@Repository
public class DAOLogin extends CrudImpl<Object> implements RepositoryLogin {

    private static final long serialVersionUID = 1L;

    @Override
    public Boolean autenticaUsuario(String login, String senha) throws Exception {
        if (login != null
                && !login.isEmpty()
                && senha != null
                && !senha.isEmpty()) {
            StringBuilder sqlLogin = new StringBuilder();
            sqlLogin.append(" SELECT ent_senha  ");
            sqlLogin.append("   FROM entidade  ");
            sqlLogin.append("  WHERE ent_login = ?  ");

            SqlRowSet sqlRowSetLogin = super.getJdbcTemplate().queryForRowSet(sqlLogin.toString(), new Object[]{login});

            if (sqlRowSetLogin.next()) {
                //Pega a senha do Login do (hash gravado no banco de dados) e compara se o bCrypt é valido com a senha em texto que usuário informou!!
                String hash_senha_banco = sqlRowSetLogin.getString("ent_senha");

                if (BCrypt.checkpw(senha, hash_senha_banco)) {
                    return true;
                }
            }
        }

        return false;
    }
}
