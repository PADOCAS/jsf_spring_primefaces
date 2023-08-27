/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.geral.controller;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Titulo;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lucia
 */
@Controller
public class TituloController extends CrudImpl<Titulo> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Método Spring para retornar um Array para o Gráfico inicial do sistema
     * (Requisição Ajax)
     *
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "**/gerarGraficoTitulo", method = RequestMethod.POST) //Pelo nome do mapeamento que ele vai reconhecer
    public @ResponseBody
    String gerarGraficoTitulo() throws Exception {
        List<Map<String, Object>> listTitResumo = getResumoTitulos();

        //Array com total de Linhas:
        String[] dados = new String[3];
        int count = 0;
        
        if (listTitResumo == null
                || listTitResumo.isEmpty()) {
            dados[count] = "[\"" + "Tipo" + "\"," + "\"" + "Quantidade" + "\"," + "\"" + "Valor Total" + "\"]";
            count++;
            dados[count] = "[\"" + "Contas a Receber" + "\"," + 0 + "," + 0 + "]";
            count++;
            dados[count] = "[\"" + "Contas a Pagar" + "\"," + 0 + "," + 0 + "]";
        } else {
            dados[count] = "[\"" + "Tipo" + "\"," + "\"" + "Quantidade" + "\"," + "\"" + "Valor Total" + "\"]";
            count++;

            //Monta os dados do Gráfico:
            Boolean existsCp = false;
            Boolean existsCr = false;
            for (Map<String, Object> map : listTitResumo) {
                if (map.get("tipo") != null) {
                    if (map.get("tipo").equals("CONTAS A PAGAR")) {
                        existsCp = true;
                    } else if (map.get("tipo").equals("CONTAS A RECEBER")) {
                        existsCr = true;
                    }
                }

                dados[count] = "[\"" + map.get("tipo") + "\"," + map.get("qtde") + "," + map.get("tot_valor") + "]";
                count++;
            }

            if (!existsCp) {
                dados[count] = "[\"" + "Contas a Pagar" + "\"," + 0 + "," + 0 + "]";
            } else if (!existsCr) {
                dados[count] = "[\"" + "Contas a Receber" + "\"," + 0 + "," + 0 + "]";
            }
        }

        return Arrays.toString(dados);
    }

    private List<Map<String, Object>> getResumoTitulos() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("    select * from ( ");
        sql.append("    select count(1) as qtde,  ");
        sql.append("           CASE WHEN tit.tipo = 'P'   ");
        sql.append("		       THEN 'CONTAS A PAGAR'   ");
        sql.append("		       ELSE 'CONTAS A RECEBER'   ");
        sql.append("           END as tipo, ");
        sql.append("	          COALESCE(sum(tit.valor),0) as tot_valor ");
        sql.append("      from public.titulo tit ");
        sql.append("  group by tit.tipo ");
        sql.append("  ) res   ");
        sql.append("  order by tot_valor; ");

        return getJdbcTemplate().queryForList(sql.toString());
    }
}
