/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.SessionController;
import com.mycompany.srv.interfaces.SrvLogin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.PrimeRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
//@Controller - ManagedBean tem que ser anotado com @Controller do Spring para o gerenciamento:
@Controller
@Scope(value = "request")
@ManagedBean(name = "invalidateSessionController")
public class InvalidateSessionController extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    private String login;

    private String senha;

    //Injeção dependência do controller SessionController:
    @Autowired
    private SessionController sessionController;

    //Injeção dependência do Service SrvLogin:
    @Autowired
    private SrvLogin srvLogin;

    public void encerrarSessaoUsuario() {
        //RequestContext do primefaces 12 (PrimeRequestContext):
        PrimeRequestContext requestContext = PrimeRequestContext.getCurrentInstance();
        Boolean loggedIn = false;

        try {
            if (srvLogin != null
                    && srvLogin.autenticaUsuario(login, senha)
                    && sessionController != null) {
                sessionController.invalidateSession(login);
                loggedIn = true;
                FacesContext.getCurrentInstance().addMessage("messageInvalidate", new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "Encerramento de sessão realizado com sucesso!"));
            } else {
                loggedIn = false;
                FacesContext.getCurrentInstance().addMessage("messageInvalidate", new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Login/Senha incorreto(s)."));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(InvalidateSessionController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("messageInvalidate", new FacesMessage(FacesMessage.SEVERITY_WARN, "ERRO", ex.getMessage()));
        }

        //Adicionando Parametro para receber em tela la no 'args' e tratar se deu certo ou não o encerramento da sessão:
        requestContext.getCallbackParams().put("loggedIn", loggedIn);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
