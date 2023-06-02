/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.model.Entidade;
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
@ManagedBean(name = "entidadeBeanView")
public class EntidadeBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ContextoBean contextoBean;

    public String getUsuarioLogadoSecurity() {
        if (contextoBean != null
                && contextoBean.getAuthentication() != null
                && contextoBean.getAuthentication().getName() != null) {
            return contextoBean.getAuthentication().getName();
        }

        return "";
    }

    public Date getUltimoAcesso() throws Exception {
        if (contextoBean != null) {
            Entidade entidade = contextoBean.getEntidadeLogada();

            if (entidade != null) {
                return entidade.getEnt_ultimoacesso();
            }
        }

        return null;
    }
}
