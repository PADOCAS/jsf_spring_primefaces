/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.enums;

/**
 *
 * @author lucia
 */
public enum CondicaoPesquisa {

    CONTEM("Contém"),
    INICIA("Inicia com"),
    TERMINA("Termina com"),
    IGUAL("Igual");

    private final String condicao;

    private CondicaoPesquisa(String condicao) {
        this.condicao = condicao;
    }

    public String getCondicao() {
        return condicao;
    }

    @Override
    public String toString() {
        return condicao;
    }

}
