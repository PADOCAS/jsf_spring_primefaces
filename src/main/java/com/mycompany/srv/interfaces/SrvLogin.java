/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.srv.interfaces;

import java.io.Serializable;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucia
 */
//@Service do Spring para indicar que é um serviço:
@Service
public interface SrvLogin extends Serializable {
    
    public Boolean autenticaUsuario(String login, String senha) throws Exception;

}
