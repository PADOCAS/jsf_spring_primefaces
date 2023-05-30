/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.listener;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Instancia unica do projeto (applicationScoped) para controle de coisas do
 * Spring
 *
 * @author lucia
 */
@ApplicationScoped
public class ContextLoaderListenerUtil extends ContextLoaderListener implements Serializable {

    //Contem métodos para retornar as instancias do Bean direto por IDs ou Classes do SPRING que está em memória.. isso ajuda em casos de estar em páginas JSP por exemplo
    //Os parametros do projeto vai estar declarado os menagedBean por ID e classe onde faz com que essa classe aqui consiga encontrar o objeto em questão!!!
    private static final long serialVersionUID = 1L;

    private static WebApplicationContext getWebAppContext() {
        if (getCurrentWebApplicationContext() != null) {
            return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
        }
        
        return null;
    }

    public static Object getBean(String idNomeBean) {
        if (getWebAppContext() != null) {
            return getWebAppContext().getBean(idNomeBean);
        }

        return null;
    }

    public static Object getBean(Class<?> classe) {
        if (getWebAppContext() != null) {
            return getWebAppContext().getBean(classe);
        }

        return null;
    }

}
