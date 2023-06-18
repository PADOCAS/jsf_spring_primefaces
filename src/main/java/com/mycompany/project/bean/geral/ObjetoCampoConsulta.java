/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author lucia
 */
public class ObjetoCampoConsulta implements Serializable, Comparator<ObjetoCampoConsulta> {

    private static final long serialVersionUID = 1L;

    private String descricaoEmTela;

    private String campoNoBanco;

    private String classe;

    private Integer ordemEmTela;

    public String getDescricaoEmTela() {
        return descricaoEmTela;
    }

    public void setDescricaoEmTela(String descricaoEmTela) {
        this.descricaoEmTela = descricaoEmTela;
    }

    public String getCampoNoBanco() {
        return campoNoBanco;
    }

    public void setCampoNoBanco(String campoNoBanco) {
        this.campoNoBanco = campoNoBanco;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getOrdemEmTela() {
        return ordemEmTela;
    }

    public void setOrdemEmTela(Integer ordemEmTela) {
        this.ordemEmTela = ordemEmTela;
    }

    @Override
    public int compare(ObjetoCampoConsulta o1, ObjetoCampoConsulta o2) {
        if (o1 != null
                && o1.getOrdemEmTela() != null
                && o2 != null
                && o2.getOrdemEmTela() != null) {
            return o1.getOrdemEmTela().compareTo(o2.getOrdemEmTela());
        }

        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.campoNoBanco);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjetoCampoConsulta other = (ObjetoCampoConsulta) obj;
        return Objects.equals(this.campoNoBanco, other.campoNoBanco);
    }

    @Override
    public String toString() {
        return getDescricaoEmTela();
    }
}
