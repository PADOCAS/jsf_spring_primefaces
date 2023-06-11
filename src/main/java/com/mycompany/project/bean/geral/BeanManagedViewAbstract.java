/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.report.util.BeanReportView;
import org.springframework.stereotype.Component;

/**
 *
 * @author lucia
 */
//@Component para o Spring trabalhar com ela:
@Component
public abstract class BeanManagedViewAbstract extends BeanReportView {

    private static final long serialVersionUID = 1L;

    protected abstract Class<?> getClassImplement();

    protected abstract IInterfaceCrud<?> getController();

    public ObjetoCampoConsulta objetoCampoConsulta;

    public ObjetoCampoConsulta getObjetoCampoConsulta() {
        return objetoCampoConsulta;
    }

    public void setObjetoCampoConsulta(ObjetoCampoConsulta objetoCampoConsulta) {
        this.objetoCampoConsulta = objetoCampoConsulta;
    }

}
