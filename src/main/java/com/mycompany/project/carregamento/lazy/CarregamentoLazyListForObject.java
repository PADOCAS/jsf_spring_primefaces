/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.carregamento.lazy;

import com.mycompany.framework.controller.crud.CrudGeralController;
import com.mycompany.project.listener.ContextLoaderListenerUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 * Classe para implementar o carregamento por demanda (lazy -> preguiçoso) para
 * nossas telas de pesquisas (dataTable primefaces) Com isso, mesmo tendo muitos
 * registros no cadastro, ficará paginado não sobrecarregando o sistema, só
 * carregará para as páginas necessárias
 *
 * @param <T>
 * @author lucia
 */
public class CarregamentoLazyListForObject<T> extends LazyDataModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> list = new ArrayList<>();

    private int totalRegistrosConsulta = 0;

    private String query = null;

    private final CrudGeralController crudGeralController = (CrudGeralController) ContextLoaderListenerUtil.getBean(CrudGeralController.class);

    @Override
    public int count(Map<String, FilterMeta> map) {
        return totalRegistrosConsulta;
    }

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        try {
            if (query != null
                    && !query.isEmpty()) {
                list = (List<T>) crudGeralController.findListByQueryDinamica(query, first, pageSize);

                //Total Registros Consulta deve ser pego por outro select para saber número total de registros:
                setRowCount(totalRegistrosConsulta == 0 ? 0 : totalRegistrosConsulta);
                setPageSize(pageSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Método para retornar o objeto selecionado em uma row da dataTable (atráves do seu rowKey)
     * 
     * @param rowKey
     * @return 
     */
    @Override
    public T getRowData(String rowKey) {
        if (rowKey != null) {
            for (T object : list) {
                String key = getRowKey(object);
                if (key != null
                        && key.equals(rowKey)) {
                    return object;
                }
            }
        }

        return null;
    }

    /**
     * Método para tratar o rowKey dos nossos tableDetail (Cadastros), onde sempre será tratado pela PK das tabelas (campo codigo)
     * 
     * @param object
     * @return 
     */
    @Override
    public String getRowKey(T object) {
        try {
            if (object != null
                    && object.getClass() != null) {
                //Tratar sempre pela PK da tabela (Cadastros sempre vão ser por padrão pk com nome codigo, caso modifique algum tratar aqui genericamente depois):
                Field pkField = object.getClass().getDeclaredField("codigo");
                // Permite acesso ao atributo mesmo se for privado
                pkField.setAccessible(true);
                // Obtém o valor do atributo
                Object pkValue = pkField.get(object);
                return String.valueOf(pkValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void clean() {
        this.query = null;
        this.totalRegistrosConsulta = 0;
        this.list.clear();
    }

    public void remove(T objetoSelecionado) {
        if (list != null
                && !list.isEmpty()) {
            this.list.remove(objetoSelecionado);
        }
    }

    public void add(T objetoSelecionado) {
        if (list != null) {
            this.list.add(objetoSelecionado);
        }
    }

    public void addAll(List<T> collections) {
        if (list != null) {
            this.list.addAll(collections);
        }
    }

    public void setTotRegConsulta(int totalRegistrosConsulta, String query) {
        this.query = query;
        this.totalRegistrosConsulta = totalRegistrosConsulta;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
