/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.interfaces;

import java.io.Serializable;

/**
 *
 * @author lucia
 */
public interface RepositoryLogin extends Serializable {

    public Boolean autenticaUsuario(String login, String senha) throws Exception;

}
