/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Fábrica de Exceções que vai ser declarado nos arquivos de parametros do nosso projeto para indicar que ela trabalhará com as exceções
 * 
 * @author lucia
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory exceptionHandlerFactory;

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        if (exceptionHandlerFactory != null) {
            ExceptionHandler exceptionHandler = new CustomExceptionHandler(exceptionHandlerFactory.getExceptionHandler());
            return exceptionHandler;
        }

        return null;
    }

}
