/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.model.Entidade;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.mycompany.project.geral.controller.SessionController;

/**
 *
 * @author lucia
 */
@Scope(value = "session")
//Component - Anotação genérica para utilizar em qualquer lugar do sistema
@Component(value = "contextoBeanController")
public class ContextoBeanController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EntidadeController entidadeController;

    @Autowired
    private SessionController sessionController;

    /**
     * Retorna todas as informações do usuário que está logado através do Spring
     * Security
     *
     * @return Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Retorna Entidade Logada atual
     *
     * @return
     * @throws java.lang.Exception
     */
    public Entidade getEntidadeLogada() throws Exception {
        Entidade entidade = null;

        if (getExternalContext() != null
                && getExternalContext().getSessionMap() != null) {
            //Lá no filtro já trabalhamos com essa variável (userLogadoSessao)
            entidade = (Entidade) getExternalContext().getSessionMap().get("userLogadoSessao");

            //Caso entidade estiver nula ou for diferente do atual:
            if (entidade == null
                    || (entidade.getEnt_login() != null
                    && !entidade.getEnt_login().equals(getUserPrincipal()))) {
                //Se o usuário foi autenticado:
                if (getAuthentication().isAuthenticated()) {
                    entidadeController.updateUltimoAcessoUsuario(getAuthentication().getName());
                    entidade = entidadeController.findUsuarioLogado(getAuthentication().getName());
                    //Adiciona o usuário logado no externalContext
                    getExternalContext().getSessionMap().put("userLogadoSessao", entidade);
                    //Adiciona Usuário na sessão
                    sessionController.addSession(entidade.getEnt_login(), (HttpSession) getExternalContext().getSession(true));
                }
            }
        }

        return entidade;
    }

    /**
     * Retorna ExternalContext do JSF
     *
     * @return
     */
    public ExternalContext getExternalContext() {
        if (FacesContext.getCurrentInstance() != null
                && FacesContext.getCurrentInstance().getExternalContext() != null) {
            return FacesContext.getCurrentInstance().getExternalContext();
        }

        return null;
    }

    /**
     * Retorna o usuário principal logado
     *
     * @return
     */
    public String getUserPrincipal() {
        if (getExternalContext() != null
                && getExternalContext().getUserPrincipal() != null) {
            return getExternalContext().getUserPrincipal().getName();
        }

        return "";
    }
}
