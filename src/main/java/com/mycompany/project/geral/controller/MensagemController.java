/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Mensagem;
import java.io.Serializable;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
public class MensagemController extends CrudImpl<Mensagem> implements Serializable {

    private static final long serialVersionUID = 1L;

}
