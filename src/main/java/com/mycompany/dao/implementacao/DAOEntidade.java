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
    public Date getUltimoAcessoEntidadeLogada(String name) {
        return null;
    }

    @Override
    public void updateUltimoAcessoUsuario(String name) {

    }

    @Override
    public Boolean existeUsuario(String name) {
        return false;
    }

}
