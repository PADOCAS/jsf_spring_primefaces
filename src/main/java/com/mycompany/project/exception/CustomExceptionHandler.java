/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.exception;

import com.mycompany.hibernate.session.HibernateUtil;
import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;

/**
 * Classe responsável por interceptar os erros pra gente
 *
 * @author lucia
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapperd;

    final FacesContext facesContext = FacesContext.getCurrentInstance();

    final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

    final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

    public CustomExceptionHandler(ExceptionHandler wrapperd) {
        this.wrapperd = wrapperd;
    }

    /**
     * Sobrescreve o método que retorna a pilha de exceções
     *
     * @return
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapperd;
    }

    /**
     * Sobrescreve o método handle que é responsavel por manipular as exceções
     * do JSF
     *
     * @throws FacesException
     */
    @Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

        //Iterator sempre é trabalhado com uma lista, varrendo os dados até o final dela:
        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getContext();

            //Recuperar a exceção do context:
            Throwable exception = context.getException();

            //Trabalhamos a exceção:
            try {
                if (exception != null
                        && facesContext != null) {
                    requestMap.put("exceptionMessage", exception.getMessage());

                    if (exception.getMessage() != null
                            && exception.getMessage().contains("ConstraintViolationException")) {
                        //Erro por constraint não permitir:
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro não pode ser removido por estar associado a outro(s).", "Registro não pode ser removido por estar associado a outro(s)."));
                    } else if (exception.getMessage() != null
                            && exception.getMessage().contains("org.hibernate.StaleObjectStateException")) {
                        //Erro pelo objeto salvo atualmente estar diferente do que você está em memória (foi atualizado ou excluído por outro usuário)
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro foi atualizado ou excluído por outro usuário. Consulte novamente.", "Registro foi atualizado ou excluído por outro usuário. Consulte novamente."));
                    } else {
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "O sistema se recuperou de um erro inesperado.", "O sistema se recuperou de um erro inesperado."));
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você pode continuar usando o sistema normalmente.", "Você pode continuar usando o sistema normalmente."));
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "O erro foi causado por:\n" + exception.getMessage(), "O erro foi causado por:\n" + exception.getMessage()));

                        //Caso quiser dar um alerta javaScript pelo Primefaces ou em Dialog:
                        PrimeFaces.current().executeScript("alert('O sistema se recuperou de um erro inesperado.')");
                        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_FATAL, "O sistema se recuperou de um erro inesperado.", "O sistema se recuperou de um erro inesperado."));
                        //Redirecionamento para página de erro:
                        navigationHandler.handleNavigation(facesContext, null, "/error/error.jsf?faces-redirect=true&expired=true");
                    }

                    //Renderiza a resposta:
                    facesContext.renderResponse();
                }
            } finally {
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

                if (sessionFactory != null
                        && sessionFactory.getCurrentSession() != null
                        && sessionFactory.getCurrentSession().getTransaction() != null
                        && sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }

                if (exception != null) {
                    exception.printStackTrace();
                }
                
                iterator.remove();
            }
        }

        getWrapped().handle();
    }

}
