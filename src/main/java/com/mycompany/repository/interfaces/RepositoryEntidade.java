/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.interfaces;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lucia
 */
public interface RepositoryEntidade extends Serializable {

    public Date getUltimoAcessoEntidadeLogada(String name);

    public void updateUltimoAcessoUsuario(String name);

    public Boolean existeUsuario(String name);
}
