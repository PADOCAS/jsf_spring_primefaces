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
    //Cadastros:
    //Cad. Bairro:
    BAIRRO_ACESSAR("BAIRRO_ACESSAR", "Bairro - Acessar"),
    BAIRRO_SALVAR("BAIRRO_SALVAR", "Bairro - Salvar"),
    BAIRRO_EDITAR("BAIRRO_EDITAR", "Bairro - Editar"),
    BAIRRO_EXCLUIR("BAIRRO_EXCLUIR", "Bairro - Excluir"),
    //Cad. Cidade:
    CIDADE_ACESSAR("CIDADE_ACESSAR", "Cidade - Acessar"),
    CIDADE_SALVAR("CIDADE_SALVAR", "Cidade - Salvar"),
    CIDADE_EDITAR("CIDADE_EDITAR", "Bairro - Editar"),
    CIDADE_EXCLUIR("CIDADE_EXCLUIR", "Bairro - Excluir"),
    //Cad. País:
    PAIS_ACESSAR("PAIS_ACESSAR", "País - Acessar"),
    PAIS_SALVAR("PAIS_SALVAR", "País - Salvar"),
    PAIS_EDITAR("PAIS_EDITAR", "País - Editar"),
    PAIS_EXCLUIR("PAIS_EXCLUIR", "País - Excluir"),
    //Cad. Usuário:
    USUARIO_ACESSAR("USUARIO_ACESSAR", "Usuário - Acessar"),
    USUARIO_SALVAR("USUARIO_SALVAR", "Usuário - Salvar"),
    USUARIO_EDITAR("USUARIO_EDITAR", "Usuário - Editar"),
    USUARIO_EXCLUIR("USUARIO_EXCLUIR", "Usuário - Excluir"),
    //Mensagens:
    MENSAGEM_ACESSAR("MENSAGEM_ACESSAR", "Mensagem Recebida - Acessar"),
    MENSAGENS_ENVIAR_ACESSAR("MENSAGENS_ENVIAR_ACESSAR", "Enviar mensagem - Acessar"),
    MENSAGENS_ENVIAR_NOVO("MENSAGENS_ENVIAR_NOVO", "Enviar mensagem - Novo"),
    MENSAGENS_ENVIAR_EDITAR("MENSAGENS_ENVIAR_EDITAR", "Enviar mensagem - Editar"),
    MENSAGENS_ENVIAR_EXCLUIR("MENSAGENS_ENVIAR_EXCLUIR", "Enviar mensagem - Excluir");

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
