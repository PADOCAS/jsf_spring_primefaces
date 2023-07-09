/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.EstadoController;
import com.mycompany.project.model.Estado;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "view")
@ManagedBean(name = "estadoBeanView")
public class EstadoBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EstadoController estadoController;

    @Override
    protected Class<?> getClassImplement() {
        return Estado.class;
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return estadoController;
    }

    @Override
    public String condicaoAndParaPesquisa() throws Exception {
        return null;
    }

}
