/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.filter;

import com.mycompany.framework.util.FrameworkUtil;
import com.mycompany.hibernate.session.HibernateUtil;
import com.mycompany.project.listener.ContextLoaderListenerUtil;
import com.mycompany.project.model.Entidade;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Vai interceptar qualquer ação do usuário em qualquer parte do sistema!
 *
 * @author lucia
 */
@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    private static SessionFactory sessionFactory;

    /**
     * Executado quando a aplicação está sendo iniciada Executa apenas 1 vez!
     *
     * @throws ServletException
     */
    @Override
    protected void initFilterBean() throws ServletException {
        //Sempre já passa aqui iniciar a aplicação e já alimenta o sessionFactory (apenas 1 vez):
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //JDBC do Spring: (Vai pegar das configurações dos arquivos)
        //Vai ser resposável por comitar ou dar roolback:
//        BasicDataSource springBasicDataSource = (BasicDataSource) ContextLoaderListenerUtil.getBean("springDataSource");  //Caso For TomCat
        DataSource springBasicDataSource = (DataSource) ContextLoaderListenerUtil.getBean("springDataSource");  //Usando DataSource direto, glassfish/payara
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springBasicDataSource);
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            request.setCharacterEncoding("UTF-8"); //Define codificação para acentuação não ser atrapalhada nada..
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            //Captura o usuário que faz a operação:
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession httpSession = httpServletRequest.getSession();
            Entidade userLogadoSessao = (Entidade) httpSession.getAttribute("userLogadoSessao");

            if (userLogadoSessao != null
                    && userLogadoSessao.getEnt_codigo() != null) {
                //Importante setar aqui o EntCodigo para o hibernate envers salvar o código nas tabelas de log de auditoria (CustomListener.class):
                FrameworkUtil.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
            }

            if (sessionFactory != null
                    && sessionFactory.getCurrentSession() != null) {
                sessionFactory.getCurrentSession().beginTransaction();
            }

            //Antes de executar a ação (request):
            filterChain.doFilter(request, response); //Executa nossa ação no servidor
            //Após executar a ação (resposta):

            //Insert do Banco apenas para teste, verificando se está se conectando no banco certinho!! Muito bom tudo ok com a conexão com o banco direto do dataSource do payara!!
            //sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO PUBLIC.PAIS VALUES (3, 'ARROZ', 'BOUS1', 2);").executeUpdate();  
            //
            //Se tudo ok depois do doFilter, faz o commit, caso deu erro vai dar Rollback:
            transactionManager.commit(transactionStatus);

            if (sessionFactory != null
                    && sessionFactory.getCurrentSession() != null) {
                if (sessionFactory.getCurrentSession().getTransaction() != null
                        && sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().flush();
                    sessionFactory.getCurrentSession().getTransaction().commit();
                }

                if (sessionFactory.getCurrentSession().isOpen()) {
                    sessionFactory.getCurrentSession().close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionManager.rollback(transactionStatus);

            if (sessionFactory != null
                    && sessionFactory.getCurrentSession() != null) {
                if (sessionFactory.getCurrentSession().getTransaction() != null
                        && sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }

                if (sessionFactory.getCurrentSession().isOpen()) {
                    sessionFactory.getCurrentSession().close();
                }
            }
        } finally {
            if (sessionFactory != null
                    && sessionFactory.getCurrentSession() != null
                    && sessionFactory.getCurrentSession().isOpen()) {
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().flush();
                    sessionFactory.getCurrentSession().clear();
                }

                sessionFactory.getCurrentSession().close();
            }
        }
    }

}
