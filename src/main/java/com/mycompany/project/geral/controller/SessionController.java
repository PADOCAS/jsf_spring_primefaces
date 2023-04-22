/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

/**
 * SessionController - Scope de Application (apenas 1 instancia para a aplicação)
 * 
 * @author lucia
 */
@ApplicationScoped
public interface SessionController extends Serializable {

    public void addSession(String keyLoginUsuario, HttpSession httpSession);

    public void invalidateSession(String keyLoginUsuario);

}
