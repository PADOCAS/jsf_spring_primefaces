/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.converter;

import com.mycompany.project.bean.geral.ObjetoCampoConsulta;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author lucia
 */
@FacesConverter(forClass = ObjetoCampoConsulta.class)
public class ObjetoCampoConsultaConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Recebe um código(String value) e retorna objeto
     * (ObjetoCampoConsulta.class) -> Conversão da tela para o objeto
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
            //Recebe a String separada por '*' feita no método abaixo getAsString, com ela preenche todo os campos do objeto
            String[] valores = value.split("\\*");
            ObjetoCampoConsulta objCamCon = new ObjetoCampoConsulta();
            objCamCon.setCampoNoBanco(valores[0]);
            objCamCon.setClasse(valores[1]);
            objCamCon.setDescricaoEmTela(valores[2]);
            objCamCon.setOrdemEmTela(Integer.valueOf(valores[3]));

            return objCamCon;
        }

        return null;
    }

    /**
     * Recebe um objeto(ObjetoCampoConsulta.class) e retorna Código (String) ->
     * Conversão do objeto para tela
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            ObjetoCampoConsulta objCamCon = (ObjetoCampoConsulta) value;

            //Separa os campos do Objeto com '*' para poder converter o objeto todo no método getAsObject acima!
            if (objCamCon.getCampoNoBanco() != null
                    && objCamCon.getClasse() != null
                    && objCamCon.getDescricaoEmTela() != null
                    && objCamCon.getOrdemEmTela() != null) {
                return objCamCon.getCampoNoBanco() + "*" + objCamCon.getClasse() + "*" + objCamCon.getDescricaoEmTela() + "*" + objCamCon.getOrdemEmTela().toString();
            }

        }

        return null;
    }

}
