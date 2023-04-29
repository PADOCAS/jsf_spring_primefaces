/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.message.util;

/**
 *
 * @author lucia
 */
public enum StatusPersistencia {
    ERRO("Erro"),
    SUCESSO("Sucesso"),
    OBJETO_REFERENCIADO("Esse registro não pode ser apagado por possuir referências ao mesmo");

    private String name;

    private StatusPersistencia(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
