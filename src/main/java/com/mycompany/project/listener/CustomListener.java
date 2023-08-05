/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.listener;

import com.mycompany.framework.util.FrameworkUtil;
import com.mycompany.project.model.Entidade;
import com.mycompany.project.model.RevInfo;
import java.io.Serializable;
import javax.servlet.ServletContextListener;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucia
 */
@Service
public class CustomListener implements RevisionListener, ServletContextListener, Serializable {
    
    //Para funcionar o CustomListener declarado no web.xml, teve que ser implementado um desses, caso contrário não fazia deploy, testar o funcionamento depois:
    //(deve implementar um dos seguintes: ServletContextListener, ServletContextAttributeListener, ServletRequestListener, ServletRequestAttributeListener, HttpSessionListener ou HttpSessionAttributeListener)

    private static final long serialVersionUID = 1L;

    @Override
    public void newRevision(Object o) {
        if (o != null
                && FrameworkUtil.getThreadLocal() != null) {
            RevInfo revInfo = (RevInfo) o;
            //Pega o código do Usuário que vai ser sempre setado na ThreadLocal pelo FilterOpenSessionInView:
            Long codUser = FrameworkUtil.getThreadLocal().get();

            Entidade entidade = new Entidade();

            if (codUser != null
                    && codUser != 0L) {
                entidade.setCodigo(codUser);
                revInfo.setEntidade(entidade);
            }
        }
    }

}
