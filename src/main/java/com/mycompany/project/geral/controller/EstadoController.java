/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Estado;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
public class EstadoController extends CrudImpl<Estado> {

    private static final long serialVersionUID = 1L;

    public List<SelectItem> getListSelectItemEstado() {
        List<SelectItem> listSelectItem = new ArrayList<>();

        try {
            List<Estado> listEstado = findListByQueryDinamica(" FROM Estado ORDER BY nome");

            if (listEstado != null
                    && !listEstado.isEmpty()) {
                for (Estado estado : listEstado) {
                    if (estado.getCodigo() != null
                            && estado.getNome() != null) {
                        listSelectItem.add(new SelectItem(estado, estado.getNome()));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EstadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listSelectItem;
    }
}
