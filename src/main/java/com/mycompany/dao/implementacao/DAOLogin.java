/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao.implementacao;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.repository.interfaces.RepositoryLogin;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author lucia
 */
public class DAOLogin extends CrudImpl<Object> implements RepositoryLogin {

    private static final long serialVersionUID = 1L;

    @Override
    public Boolean autenticaUsuario(String login, String senha) throws Exception {
        if (login != null
                && !login.isEmpty()
                && senha != null
                && !senha.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT count(1) as autentica  ");
            sql.append("   FROM entidade  ");
            sql.append("  WHERE ent_login = ?  ");
            sql.append("    AND ent_senha = ?  ");

            //Converte o Password para BCrypt para checar no banco de dados:
            String salt = BCrypt.gensalt(12);
            String hashed_password = BCrypt.hashpw(senha, salt);

            SqlRowSet sqlRowSet = super.getJdbcTemplate().queryForRowSet(sql.toString(), new Object[]{login, hashed_password});

            return sqlRowSet.next() ? sqlRowSet.getInt("autentica") > 0 : false;
        }

        return false;
    }
}
