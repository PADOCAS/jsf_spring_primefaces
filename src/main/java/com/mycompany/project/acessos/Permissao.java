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
    //Menus:
    CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - Acessar"),
    MOVIMENTO_ACESSAR("MOVIMENTO_ACESSAR", "Movimento - Acessar"),
    //Cadastros:
    //Cad. Cidade:
    CIDADE_ACESSAR("CIDADE_ACESSAR", "Cidade - Acessar"),
    CIDADE_SALVAR("CIDADE_SALVAR", "Cidade - Salvar"),
    CIDADE_EDITAR("CIDADE_EDITAR", "Bairro - Editar"),
    CIDADE_EXCLUIR("CIDADE_EXCLUIR", "Bairro - Excluir"),   
    //Cad. Usuário:
    USUARIO_ACESSAR("USUARIO_ACESSAR", "Usuário - Acessar"),
    USUARIO_SALVAR("USUARIO_SALVAR", "Usuário - Salvar"),
    USUARIO_EDITAR("USUARIO_EDITAR", "Usuário - Editar"),
    USUARIO_EXCLUIR("USUARIO_EXCLUIR", "Usuário - Excluir"),
    //Movimentos:
    //Título:
    TITULO_ACESSAR("TITULO_ACESSAR", "Título - Acessar"),
    TITULO_SALVAR("TITULO_SALVAR", "Título - Salvar"),
    TITULO_EDITAR("TITULO_EDITAR", "Título - Editar"),
    TITULO_EXCLUIR("TITULO_EXCLUIR", "Título - Excluir");

    private String valor;
    private String descricao;

    private Permissao(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
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
