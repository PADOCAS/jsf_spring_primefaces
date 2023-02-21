/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author lucia
 */
public enum Permissao {

    //Padrão:
    ADMIN("ADMIN", "Administrador"),
    USER("USER", "Usuário Padrão"),
    CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - Acessar"),
    //Especificos:
    FINANCEIRO("FINANCEIRO", "Financeiro - Acessar"),
    MENSAGEM("MENSAGEM", "Mensagem Recebida - Acessar"),
    BAIRRO_ACESSAR("BAIRRO_ACESSAR", "Bairro - Acessar"),
    BAIRRO_NOVO("BAIRRO_NOVO", "Bairro - Novo"),
    BAIRRO_EDITAR("BAIRRO_EDITAR", "Bairro - Editar"),
    BAIRRO_EXCLUIR("BAIRRO_EXCLUIR", "Bairro - Excluir");

    private String valor;
    private String descricao;

    private Permissao(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static List<Permissao> getListPermissao() {
        List<Permissao> listPermissoes = new ArrayList<>();

        for (Permissao perm : Permissao.values()) {
            listPermissoes.add(perm);
        }

        if (!listPermissoes.isEmpty()) {
            Collections.sort(listPermissoes, new Comparator<Permissao>() {
                @Override
                public int compare(Permissao o1, Permissao o2) {
                    if (o1 != null
                            && o1.getDescricao() != null
                            && o2 != null
                            && o2.getDescricao() != null) {
                        return o1.getDescricao().compareTo(o2.getDescricao());
                    }

                    return 0;
                }
            });
        }

        return listPermissoes;
    }

    @Override
    public String toString() {
        return getValor();
    }

}
