/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.srv.implementacao;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.srv.interfaces.ServiceEntidade;
import com.mycompany.repository.interfaces.RepositoryEntidade;

/**
 *
 * @author lucia
 */
@Service
public class ServiceEntidadeImpl implements ServiceEntidade {

    private static final long serialVersionUID = 1L;

    //Injeção de dependência do DAOLogin:
    @Autowired
    private RepositoryEntidade repositoryEntidade;

    @Override
    public Date getUltimoAcessoEntidadeLogada(String name) throws Exception {
        return repositoryEntidade.getUltimoAcessoEntidadeLogada(name);
    }

    @Override
    public void updateUltimoAcessoUsuario(String name) throws Exception {
        repositoryEntidade.updateUltimoAcessoUsuario(name);
    }

    @Override
    public Boolean existeUsuario(String name) throws Exception {
        return repositoryEntidade.existeUsuario(name);
    }
}
