/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.util.all;

/**
 *
 * @author lucia
 */
public class RegexUtil {

    public static String getRetiraAcentos(String string) {
        String aux = string;
        aux = aux.replaceAll("[èéêëÈÉÊË]", "e");
        aux = aux.replaceAll("[ûùüúÛÚÙÜ]", "u");
        aux = aux.replaceAll("[ïîíìÏÎÍÌ]", "i");
        aux = aux.replaceAll("[àâáäãÁÀÂÄ]", "a");
        aux = aux.replaceAll("[óòôöõÓÒÔÖ]", "o");
        return aux;
    }
}
