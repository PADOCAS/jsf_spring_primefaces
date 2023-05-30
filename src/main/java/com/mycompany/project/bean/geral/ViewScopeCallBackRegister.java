/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

/**
 *
 * @author lucia
 */
public class ViewScopeCallBackRegister implements ViewMapListener {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event != null) {
            if (event instanceof PostConstructViewMapEvent) {
                PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent) event;
                //Arvore de componentes (uiViewRoot)
                UIViewRoot uiViewRoot = (UIViewRoot) viewMapEvent.getComponent();
                uiViewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<String, Runnable>());
            } else if (event instanceof PreDestroyViewMapEvent) {
                PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent) event;

                //Arvore de componentes (uiViewRoot)
                UIViewRoot uiViewRoot = (UIViewRoot) viewMapEvent.getComponent();

                if (uiViewRoot != null) {
                    Map<String, Runnable> callBacks = (Map<String, Runnable>) uiViewRoot.getViewMap().get(ViewScope.VIEW_SCOPE_CALLBACKS);

                    if (callBacks != null) {
                        for (Runnable run : callBacks.values()) {
                            run.run();
                        }

                        callBacks.clear();
                    }
                }

            }
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source != null && source instanceof UIViewRoot;
    }

}
