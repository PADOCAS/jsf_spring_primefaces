/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package com.mycompany.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para identificar campos de pesquisa em filtros de cadastros, serão anotados os campos nos VOs de cadastros que terão opções de filtro!
 * Targed - indica que só pode ser anotados os fields das classes
 * Retention - RunTime para máquina virtual
 * 
 * @author lucia
 */

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {
    
    /**
     * Descrição do Campo na Tela
     * 
     * @return 
     */
    public String descricaoCampoEmTela();
    
    /**
     * Campo do banco que será pesquisado
     * 
     * @return 
     */
    public String campoBancoDeDados();
    
    /**
     * Ordem que aparecerá como prioridade nos campos de busca (combobox), tem um valor default de 10000, caso não informar nada sempre fica depois de algum já declarado a ordem.
     * 
     * @return 
     */
    public int ordemCampoEmTela() default 10000;

}
