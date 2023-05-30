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
        return super.findUniqueByProperty(Entidade.class, name, "ent_login", " and entity.ent_inativo is false");
    }

    public Date getUltimoAcessoEntidadeLogada(String name) {
        return serviceEntidade.getUltimoAcessoEntidadeLogada(name);
    }

    public void updateUltimoAcessoUsuario(String name) {
        serviceEntidade.updateUltimoAcessoUsuario(name);
    }
}
