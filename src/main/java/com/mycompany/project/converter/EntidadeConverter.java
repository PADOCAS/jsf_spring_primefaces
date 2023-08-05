/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.converter;

import com.mycompany.hibernate.session.HibernateUtil;
import com.mycompany.project.model.Entidade;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author lucia
 */
@Named
@FacesConverter(value = "entidadeConverter", forClass = Entidade.class)
public class EntidadeConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Recebe um código(String value) e retorna objeto (Entidade.class) ->
     * Conversão da tela para o Objeto
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null
                && !value.isEmpty()) {
            return (Entidade) HibernateUtil.getCurrentSession().get(Entidade.class, Long.valueOf(value));
        }

        return null;
    }

    /**
     * Recebe um objeto(Entidade.class) e retorna Código (String) -> Conversão
     * do objeto para tela
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Entidade entidade = (Entidade) value;

            if (entidade.getCodigo() != null
                    && entidade.getCodigo() > 0) {
                return entidade.getCodigo().toString();
            }
        }
        return null;
    }
}
