/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import javax.faces.bean.ManagedBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "session")
@ManagedBean(name = "mensagemBeanView")
public class MensagemBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    @Override
    public void initComponentes() {
        super.initComponentes();
    }

    @Override
    protected Class<?> getClassImplement() {
        return null;
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return null;
    }

}
