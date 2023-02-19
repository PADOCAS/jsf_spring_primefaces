/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.bean.ApplicationScoped;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Responsável por estabelecer conexão com o Hibernate
 *
 * @author lucia
 */
@ApplicationScoped
public class HibernateUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    //Caso for usar por lookup o datasource (exemplo do curso):
    private static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:comp/env/jdbc/datasource";

    private static SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Responsável por ler o arquivo de configuração hibernate.cfg.xml
     *
     * @return
     */
    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }

            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Erro ao criar conexão SessionFactory!\n" + e.getMessage());
        }
    }

    /**
     * Retorna o SessionFactory existente
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Retorna a Sessão do SessionFactory
     *
     * @return Session
     */
    public static Session getCurrentSession() {
        if (sessionFactory != null) {
            return sessionFactory.getCurrentSession();
        }

        return null;
    }

    /**
     * Abre uma nova sessão do SessionFactory e retorna ela
     *
     * @return Session
     */
    public static Session openSession() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }

        if (sessionFactory != null) {
            return sessionFactory.openSession();
        }

        return null;
    }

    /**
     * Obtem a Connection do provedor de conexões configurado
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnectionProvider() throws SQLException {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        if (sessionFactory != null
                && ((SessionImplementor) sessionFactory).getJdbcConnectionAccess() != null) {
            return ((SessionImplementor) sessionFactory).getJdbcConnectionAccess().obtainConnection();
        }

        //Versões anteriores do hibernate era pego assim:
        //((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
        return null;
    }

    /**
     * Obtem a Connection do provedor de conexões configurado direto no
     * dataSource (initialContext - java:comp/env/jdbc/datasource)
     *
     * @return Connection no InitialContext java:comp/env/jdbc/datasource
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InitialContext context = new InitialContext();
        DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);

        return ds.getConnection();
    }

    /**
     * Obtem o DataSource configurado pelo initialContext -
     * java:comp/env/jdbc/datasource
     *
     * @return DataSource
     * @throws NamingException
     */
    public DataSource getDataSourceJndi() throws NamingException {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
    }
}
