/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hibernate.impl.crud;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.hibernate.session.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lucia
 * @param <T>
 */
@Component
@Transactional
//Component = Injeção de dependencia do spring
//Transactional = depende de transação
public class CrudImpl<T> implements IInterfaceCrud<T> {

    private static final long serialVersionUID = 1L;

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Autowired //Utilizado para injeção de dependência
    private JdbcTemplateImpl jdbcTemplateImpl;

    @Autowired //Utilizado para injeção de dependência
    private SimpleJdbcTemplateImpl simpleJdbcTemplateImpl;

    @Autowired //Utilizado para injeção de dependência
    private SimpleJdbcInsertImpl simpleJdbcInsertImpl;

    @Autowired //Utilizado para injeção de dependência
    private SimpleJdbcClassImpl simpleJdbcClassImpl;

    /**
     * Retornar o sessionFactory caso precisar utilizar
     * @return 
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Valida SessionFactory
     */
    private void validaSessionFactory() {
        if (getSessionFactory() == null) {
            sessionFactory = HibernateUtil.getSessionFactory();
        }

        validaTransaction();
    }

    /**
     * Valida transação, caso não tiver ativa, inicia uma transação
     */
    private void validaTransaction() {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && getSessionFactory().getCurrentSession().getTransaction() != null
                && !getSessionFactory().getCurrentSession().getTransaction().isActive()) {
            //Caso não tiver transação ativa, inicia uma:
            getSessionFactory().getCurrentSession().beginTransaction();
        }
    }

    /**
     * Commit caso necessário em processo Ajax
     */
    private void commitProcessoAjax() {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null) {
            getSessionFactory().getCurrentSession().beginTransaction().commit();
        }
    }

    /**
     * RollBack caso necessário em processo Ajax
     */
    private void rollBackProcessoAjax() {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null) {
            getSessionFactory().getCurrentSession().beginTransaction().rollback();
        }
    }

    @Override
    public void save(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void persist(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveOrUpdate(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T merge(T objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> findList(Class<T> entidade) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object findById(Class<T> entidade, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T findByPorId(Class<T> entidade, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> findListByQueryDinamica(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void executeUpdateQueryDinamica(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void executeUpdateSqlDinamica(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clearSession() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void evict(Object objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Session getSession() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> getListSqlDinamica(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public JdbcTemplate getJdbcTemplate() throws Exception {
        return jdbcTemplateImpl;
    }

    @Override
    public SimpleJdbcTemplate getSimpleJdbcTemplate() throws Exception {
        return simpleJdbcTemplateImpl;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() throws Exception {
        return simpleJdbcInsertImpl;
    }

    @Override
    public SimpleJdbcCall getSimpleJdbcCall() throws Exception {
        return simpleJdbcClassImpl;
    }

    @Override
    public Long qtdeTotalRegistro(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Query<T> obterQuery(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> findListByQueryDinamica(String query, int iniResult, int maxResult) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
