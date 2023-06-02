/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Cidade;
import com.mycompany.srv.interfaces.ServiceCidade;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
public class CidadeController extends CrudImpl<Cidade> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ServiceCidade serviceCidade;
    
}
