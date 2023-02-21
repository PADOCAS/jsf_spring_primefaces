/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/**
 * Vamos trabalhar com o ViewScope controlado pelo Spring, por isso vamos criar
 * essa classe e configurar para usa-la.
 *
 * @author lucia
 */
public class ViewScope implements Scope, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

    /**
     * FacesContext.getCurrentInstance().getViewRoot() >> Retorna o componente
     * Raiz que está associado a essa solicitação(request) getViewMap() >>
     * retorna um map que atua como a interface para o armazenamento de dados
     *
     * @return
     */
    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getViewRoot() != null
                ? FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<>();
    }

    //Get retorna a instancia de nosso scope (se já existir retorna ela, se não existir vai adicionar um novo)
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object objInstance = null;

        if (name != null
                && objectFactory != null
                && getViewMap() != null) {
            objInstance = getViewMap().get(name);

            if (objInstance == null) {
                objInstance = objectFactory.getObject();
                getViewMap().put(name, objInstance);
            }
        }

        return objInstance;
    }

    //Remove a instancia do Scope
    @Override
    public Object remove(String name) {
        Object objIntance = null;

        if (getViewMap() != null) {
            objIntance = getViewMap().remove(name);

            if (objIntance != null) {
                //Runnable para executar em segundo plano
                Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);

                if (callBacks != null) {
                    callBacks.remove(name);
                }
            }
        }

        return objIntance;
    }

    //Registra a destruição do Scope (saindo da tela o view scope deve morrer)
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        if (getViewMap() != null) {
            Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);

            if (callBacks != null) {
                callBacks.put(VIEW_SCOPE_CALLBACKS, callback);
            }
        }
    }

    //Resolve a referencia dos objetos do Scope
    @Override
    public Object resolveContextualObject(String key) {
        if (FacesContext.getCurrentInstance() != null) {
            FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(FacesContext.getCurrentInstance());
            return facesRequestAttributes.resolveReference(key);
        }

        return null;
    }

    //Mantem um ID Fixo para cada scope de view
    @Override
    public String getConversationId() {
        if (FacesContext.getCurrentInstance() != null
                && FacesContext.getCurrentInstance().getViewRoot() != null
                && FacesContext.getCurrentInstance().getViewRoot().getViewId() != null) {
            FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(FacesContext.getCurrentInstance());
            return facesRequestAttributes.getSessionId() + "-" + FacesContext.getCurrentInstance().getViewRoot().getViewId();
        }

        return null;
    }

}
