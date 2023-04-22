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
import org.springframework.jdbc.support.rowset.SqlRowSet;
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

//    Não existe mais o SimpleJdbcTemplate para as versões recentes do Spring-jdbc retiramos ele
//    @Autowired //Utilizado para injeção de dependência
//    private SimpleJdbcTemplateImpl simpleJdbcTemplateImpl;
    @Autowired //Utilizado para injeção de dependência
    private SimpleJdbcInsertImpl simpleJdbcInsertImpl;

    @Autowired //Utilizado para injeção de dependência
    private SimpleJdbcClassImpl simpleJdbcClassImpl;

    /**
     * Retornar o sessionFactory caso precisar utilizar
     *
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

    /**
     * Roda instantaneamente a instrução no banco de dados Ele roda as
     * instruções mas não comita ainda.. seria tipo um caso de gravar Pessoa e
     * depois Telefones da Pessoa Ele salva a pessoa executa o flush depois
     * insere os telefones..
     */
    private void executeFlushSession() {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null) {
            getSessionFactory().getCurrentSession().flush();
        }
    }

    @Override
    public void save(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().save(objeto);
            executeFlushSession();
        }
    }

    @Override
    public void persist(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().persist(objeto);
            executeFlushSession();
        }
    }

    @Override
    public void saveOrUpdate(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().saveOrUpdate(objeto);
            executeFlushSession();
        }
    }

    @Override
    public void update(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().update(objeto);
            executeFlushSession();
        }
    }

    @Override
    public void delete(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().delete(objeto);
            executeFlushSession();
        }
    }

    @Override
    public T merge(T objeto) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            validaSessionFactory();
            objeto = (T) getSessionFactory().getCurrentSession().merge(objeto);
            executeFlushSession();

            return objeto;
        }

        return null;
    }

    @Override
    public List<T> findList(Class<T> entidade) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && entidade != null) {
            validaSessionFactory();

            StringBuilder sql = new StringBuilder();
            sql.append(" select distinct(entity) from ");
            sql.append(entidade.getSimpleName()).append(" entity ");

            List<T> list = getSessionFactory().getCurrentSession().createQuery(sql.toString()).list();

            return list;
        }

        return null;
    }

    @Override
    public Object findById(Class<T> entidade, Long id) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && entidade != null
                && id != null) {
            validaSessionFactory();

            Object obj = getSessionFactory().getCurrentSession().load(entidade, id);

            return obj;
        }

        return null;
    }

    @Override
    public T findByPorId(Class<T> entidade, Long id) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && entidade != null
                && id != null) {
            validaSessionFactory();

            T obj = (T) getSessionFactory().getCurrentSession().load(entidade, id);

            return obj;
        }

        return null;
    }

    @Override
    public List<T> findListByQueryDinamica(String query) throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();

            List<T> list = getSessionFactory().getCurrentSession().createQuery(query).list();

            return list;
        }

        return null;
    }

    @Override
    public void executeUpdateQueryDinamica(String query) throws Exception {
        //Essa é por HQL do hibernate ou JPA (usa o createQuery)
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
            executeFlushSession();
        }
    }

    @Override
    public void executeUpdateSqlDinamica(String query) throws Exception {
        //Essa é por SQL puro (usa o createSQLQuery)
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();
            getSessionFactory().getCurrentSession().createSQLQuery(query).executeUpdate();
            executeFlushSession();
        }
    }

    @Override
    public void clearSession() throws Exception {
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null) {
            getSessionFactory().getCurrentSession().clear();
        }
    }

    @Override
    public void evict(Object objeto) throws Exception {
        //Limpa o objeto da sessão
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && objeto != null) {
            getSessionFactory().getCurrentSession().evict(objeto);
        }
    }

    @Override
    public Session getSession() throws Exception {
        if (getSessionFactory() != null) {
            validaSessionFactory();
            return getSessionFactory().getCurrentSession();
        }

        return null;
    }

    @Override
    public List<T> getListSqlDinamica(String query) throws Exception {
        //Retorna uma lista através de um SQL puro
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();

            List<T> list = getSessionFactory().getCurrentSession().createSQLQuery(query).list();

            return list;
        }

        return null;
    }

    @Override
    public List<Object[]> getListSqlDinamicaArray(String query) throws Exception {
        //Retorna uma lista de Objeto Array através de um SQL puro
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();

            List<Object[]> list = (List<Object[]>) getSessionFactory().getCurrentSession().createSQLQuery(query).list();

            return list;
        }

        return null;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() throws Exception {
        return jdbcTemplateImpl;
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
    public Long qtdeTotalRegistro(String table) throws Exception {
        //Retorna a qtde de registros de uma tabela usando jdbc
        if (getJdbcTemplate() != null
                && table != null) {
            StringBuilder sql = new StringBuilder();
            sql.append(" select count(1) as qtde from ").append(table);

            SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql.toString());

            return sqlRowSet.next() ? sqlRowSet.getLong("qtde") : 0L;
            //Versão mais atual do Spring, retirou esse tipo de query:
//            return getJdbcTemplate().queryForLong(sql.toString());
        }

        return 0L;
    }

    @Override
    public Query<T> obterQuery(String query) throws Exception {
        //Retorna uma Query preparada para uso posterior se necessário
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();

            Query queryReturn = getSessionFactory().getCurrentSession().createQuery(query.toString());

            return queryReturn;
        }

        return null;
    }

    /**
     * Usado para selecionar por demanda, paginado do initResult até o maxResult
     *
     * @param query
     * @param initResult
     * @param maxResult
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findListByQueryDinamica(String query, int initResult, int maxResult) throws Exception {
        //Retorna uma lista de Objeto Array através de um SQL puro
        if (getSessionFactory() != null
                && getSessionFactory().getCurrentSession() != null
                && query != null) {
            validaSessionFactory();

            List<T> list = (List<T>) getSessionFactory().getCurrentSession().createQuery(query)
                    .setFirstResult(initResult)
                    .setMaxResults(maxResult)
                    .list();

            return list;
        }

        return null;
    }

}
