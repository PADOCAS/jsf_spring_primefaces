/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.srv.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.srv.interfaces.ServiceLogin;
import com.mycompany.repository.interfaces.RepositoryLogin;

/**
 *
 * @author lucia
 */
@Service
public class ServiceLoginImpl implements ServiceLogin {

    private static final long serialVersionUID = 1L;

    //Injeção de dependência do DAOLogin:
    @Autowired
    private RepositoryLogin repositoryLogin;

    @Override
    public Boolean autenticaUsuario(String login, String senha) throws Exception {
        if (repositoryLogin != null) {
            return repositoryLogin.autenticaUsuario(login, senha);
        }

        return false;
    }
}
