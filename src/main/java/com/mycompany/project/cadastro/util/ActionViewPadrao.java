/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.cadastro.util;

import com.mycompany.project.message.util.StatusPersistencia;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.apache.poi.ss.formula.functions.T;

/**
 *
 * @author lucia
 */
public interface ActionViewPadrao extends Serializable {

    public abstract void limparLista() throws Exception;

    public abstract String save() throws Exception;
    
    public abstract String saveNew() throws Exception;

    public abstract void saveNotReturn() throws Exception;

    public abstract void saveEdit() throws Exception;
    
    public abstract void validExclusao() throws Exception;
    
    public abstract void validEditar() throws Exception;

    public abstract void excluir() throws Exception;

    public abstract String ativar() throws Exception;
    
    public abstract String getMessageExclusao() throws Exception;

    public abstract void initComponentes();
    
    public abstract String novo();

    public abstract String editar() throws Exception;

    public abstract void setarVariaveisNulas() throws Exception;

    public abstract void consultarEntidade() throws Exception;

    public abstract void statusOperation(StatusPersistencia status) throws Exception;

    public abstract String redirecionarNewEntidade() throws Exception;

    public abstract String redirecionarFindEntidade() throws Exception;
    
    public abstract String getUrfFindEntidade();
    
    public abstract List<?> getListAll() throws Exception;
    
    public abstract String getUrfEntidade();
    
    public abstract void geraMensagemSaveDefault();

    public abstract void addMessage(String msg, String sumario, FacesMessage.Severity severity) throws Exception;
}
