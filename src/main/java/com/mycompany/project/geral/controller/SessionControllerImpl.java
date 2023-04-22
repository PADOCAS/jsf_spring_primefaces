/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import java.util.HashMap;
import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lucia
 */
@ApplicationScoped
public class SessionControllerImpl implements SessionController {

    private static final long serialVersionUID = 1L;

    private HashMap<String, HttpSession> mapUsuarios = new HashMap<>();

    /**
     * Adiciona sessão para o usuário
     *
     * @param keyLoginUsuario
     * @param httpSession
     */
    @Override
    public void addSession(String keyLoginUsuario, HttpSession httpSession) {
        if (mapUsuarios == null) {
            mapUsuarios = new HashMap<>();
        }

        mapUsuarios.put(keyLoginUsuario, httpSession);
    }

    /**
     * Encerra a sessão para o usuário:
     * 
     * @param keyLoginUsuario 
     */
    @Override
    public void invalidateSession(String keyLoginUsuario) {
        try {
            if (mapUsuarios != null
                    && !mapUsuarios.isEmpty()) {
                HttpSession httpSession = mapUsuarios.get(keyLoginUsuario);

                if (httpSession != null) {
                    //Encerra Sessão para o usuário:
                    httpSession.invalidate();
                    mapUsuarios.remove(keyLoginUsuario);
                } else {
                    System.out.println("Não tem sessão para o usuário ainda!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
