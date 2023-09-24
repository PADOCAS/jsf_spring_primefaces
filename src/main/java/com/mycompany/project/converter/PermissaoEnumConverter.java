/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.converter;

import com.mycompany.project.acessos.Permissao;
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
@FacesConverter(value = "permissaoEnumConverter", forClass = Permissao.class)
public class PermissaoEnumConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null
                && !value.isEmpty()) {
            return Permissao.valueOf(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null
                && value instanceof Permissao) {
            return ((Permissao) value).name();
        }

        return null;
    }

}
