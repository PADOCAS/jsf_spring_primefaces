/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hibernate.interfaces.crud;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface padrão para trabalhar com cadastros no hibernate
 * <T> para trabalhar com qualquer objeto (genérico)
 *
 * @author lucia
 * @param <T> Qualquer objeto (genérico)
 */
@Component
@Transactional
//Component = Injeção de dependencia do spring
//Transactional = depende de transação
public interface IInterfaceCrud<T> extends Serializable {

    /**
     * Salva o objeto recebido
     *
     * @param objeto
     * @throws Exception
     */
    public void save(T objeto) throws Exception;

    public void persist(T objeto) throws Exception;

    /**
     * Salvo ou atualiza o objeto recebido
     *
     * @param objeto
     * @throws Exception
     */
    public void saveOrUpdate(T objeto) throws Exception;

    /**
     * Realiza o update/atualização dos dados do objeto recebido
     *
     * @param objeto
     * @throws Exception
     */
    public void update(T objeto) throws Exception;

    /**
     * Realiza o delete do objeto recebido
     *
     * @param objeto
     * @throws Exception
     */
    public void delete(T objeto) throws Exception;

    /**
     * Salva ou atualiza e retorna o objeto salvo/atualizado
     *
     * @param objeto
     * @return
     * @throws Exception
     */
    public T merge(T objeto) throws Exception;

    /**
     * Carrega a Lista de determinada classe
     *
     * @param entidade
     * @return
     * @throws Exception
     */
    public List<T> findList(Class<T> entidade) throws Exception;

    public Object findById(Class<T> entidade, Long id) throws Exception;

    public T findByPorId(Class<T> entidade, Long id) throws Exception;

    public List<T> findListByQueryDinamica(String query) throws Exception;

    /**
     * Executar update com HQL do hibernate
     *
     * @param query
     * @throws Exception
     */
    public void executeUpdateQueryDinamica(String query) throws Exception;

    /**
     * Executar update com SQL puro
     *
     * @param query
     * @throws Exception
     */
    public void executeUpdateSqlDinamica(String query) throws Exception;

    /**
     * Limpa a sessão do Hibernate (Fica guardando muita coisa em memória é bom
     * limpar em alguns momentos)
     *
     * @throws Exception
     */
    public void clearSession() throws Exception;

    /**
     * Retira um objeto da sessão do hibernate para evitar sujeiras em alguns
     * momentos
     *
     * @param objeto
     * @throws Exception
     */
    public void evict(Object objeto) throws Exception;

    /**
     * Retorno Session do hibernate
     *
     * @return
     * @throws Exception
     */
    public Session getSession() throws Exception;

    public List<T> getListSqlDinamica(String query) throws Exception;

    public JdbcTemplate getJdbcTemplate() throws Exception;

    public SimpleJdbcTemplate getSimpleJdbcTemplate() throws Exception;

    public SimpleJdbcInsert getSimpleJdbcInsert() throws Exception;
    
    public SimpleJdbcCall getSimpleJdbcCall() throws Exception;

    public Long qtdeTotalRegistro(String query) throws Exception;

    public Query<T> obterQuery(String query) throws Exception;

    /**
     * Usado para selecionar por demanda, paginado
     *
     * @param query
     * @param iniResult
     * @param maxResult
     * @return
     * @throws Exception
     */
    public List<T> findListByQueryDinamica(String query, int iniResult, int maxResult) throws Exception;

}
