/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.framework.util;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * Classe responsável para registrar os Logs de auditoria depois, qual usuário que fez as alteração (insert, update, delete) nos registros!
 * 
 * @author lucia
 */
@Component
public class FrameworkUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * Método Syncronizado, só usa 1 por vez, se tiver várias requisições para utilizar esse método, eles vão ficando em fila esperando um acabar para o outro pegar.
     * 
     * @return 
     */
    public synchronized static ThreadLocal<Long> getThreadLocal() {
        return threadLocal;
    }

}
