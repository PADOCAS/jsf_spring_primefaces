/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.listener;

import com.mycompany.framework.util.FrameworkUtil;
import com.mycompany.project.model.Entidade;
import com.mycompany.project.model.RevInfo;
import java.io.Serializable;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucia
 */
@Service
public class CustomListener implements RevisionListener, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void newRevision(Object o) {
        if (o != null
                && FrameworkUtil.getThreadLocal() != null) {
            RevInfo revInfo = (RevInfo) o;
            Long codUser = FrameworkUtil.getThreadLocal().get();

            Entidade entidade = new Entidade();

            if (codUser != null
                    && codUser != 0L) {
                entidade.setEnt_codigo(codUser);
                revInfo.setEntidade(entidade);
            }
        }
    }

}
