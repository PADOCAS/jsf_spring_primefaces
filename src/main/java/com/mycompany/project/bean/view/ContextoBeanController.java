/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.model.Entidade;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author lucia
 */
@Scope(value = "session")
//Component - Anotação genérica para utilizar em qualquer lugar do sistema
@Component(value = "contextoBeanController")
public class ContextoBeanController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Retorna todas as informações do usuário que está logado através do Spring
     * Security
     *
     * @return Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    public Entidade getEntidadeLogada() {
        return null;
    }
}
