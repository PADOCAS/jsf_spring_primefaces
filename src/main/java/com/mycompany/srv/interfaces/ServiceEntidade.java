/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.srv.interfaces;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lucia
 */
public interface ServiceEntidade extends Serializable {

    public Date getUltimoAcessoEntidadeLogada(String name) throws Exception;

    public void updateUltimoAcessoUsuario(String name) throws Exception;

    public Boolean existeUsuario(String name) throws Exception;
}
