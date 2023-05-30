/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "session")
@ManagedBean(name = "entidadeBeanViewController")
public class EntidadeBeanViewController extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ContextoBeanController contextoBeanController;

    public String getUsuarioLogadoSecurity() {
        if (contextoBeanController != null
                && contextoBeanController.getAuthentication() != null
                && contextoBeanController.getAuthentication().getName() != null) {
            return contextoBeanController.getAuthentication().getName();
        }

        return "";
    }

    public Date getUltimoAcesso() throws Exception {
        if (contextoBeanController != null
                && contextoBeanController.getAuthentication() != null) {

        }

        return new Date(2023, 5, 5);
    }
}
