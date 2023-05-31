/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.PrimeRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.mycompany.srv.interfaces.ServiceLogin;
import com.mycompany.project.geral.controller.SessionController;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private ServiceLogin serviceLogin;

    /**
     * Spring RestFull - Post para invalidar sessão assim que o usuário deslogar (Chamada no javascript (scripty.js))
     * do sistema
     *
     * @param httpServletRequest
     * @throws Exception
     */
    @RequestMapping(value = "**/invalidar_sessao", method = RequestMethod.POST)
    public void invalidarSessaoUsuarioLogout(HttpServletRequest httpServletRequest) throws Exception {
        String userLogadoSessao = null;

        if (httpServletRequest != null) {
            if (httpServletRequest.getUserPrincipal() != null) {
                userLogadoSessao = httpServletRequest.getUserPrincipal().getName();
            }

            if (userLogadoSessao == null
                    || userLogadoSessao.trim().isEmpty()) {
                userLogadoSessao = httpServletRequest.getRemoteUser();
            }

            if (userLogadoSessao != null
                    && !userLogadoSessao.isEmpty()) {
                sessionController.invalidateSession(userLogadoSessao);
            }
        }
    }

    public void encerrarSessaoUsuario() {
        //RequestContext do primefaces 12 (PrimeRequestContext):
        PrimeRequestContext requestContext = PrimeRequestContext.getCurrentInstance();
        Boolean loggedIn = false;

        try {
            if (serviceLogin != null
                    && serviceLogin.autenticaUsuario(login, senha)
                    && sessionController != null) {
                sessionController.invalidateSession(login);
                loggedIn = true;
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
