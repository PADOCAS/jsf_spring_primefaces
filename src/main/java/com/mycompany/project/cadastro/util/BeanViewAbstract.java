/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.cadastro.util;

import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.message.util.StatusPersistencia;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import org.springframework.stereotype.Component;

/**
 *
 * @author lucia
 */
@Component
public abstract class BeanViewAbstract implements ActionViewPadrao {

    @Override
    public void limparLista() throws Exception {
    }

    @Override
    public String save() throws Exception {
        return null;
    }

    @Override
    public void saveNotReturn() throws Exception {
    }

    @Override
    public void saveEdit() throws Exception {
    }

    @Override
    public void excluir() throws Exception {
    }

    @Override
    public String ativar() throws Exception {
        return null;
    }

    /**
     * PostConstruct - Ao abrir a tela ja inicia os componentes, váriaveis
     * desejadas etc..
     */
    @PostConstruct
    @Override
    public void initComponentes() {
    }

    @Override
    public String novo() {
        return null;
    }

    @Override
    public String editar() throws Exception {
        return null;
    }

    @Override
    public void setarVariaveisNulas() throws Exception {
    }

    @Override
    public void consultarEntidade() throws Exception {
    }

    @Override
    public void statusOperation(StatusPersistencia status) throws Exception {
        if (status != null) {
            Mensagem.responseOperation(status);
        }
    }

    @Override
    public String redirecionarNewEntidade() throws Exception {
        return null;
    }

    @Override
    public String redirecionarFindEntidade() throws Exception {
        return null;
    }

    @Override
    public void addMessage(String msg, String sumario, FacesMessage.Severity severity) throws Exception {
        if (msg != null
                && !msg.isEmpty()
                && sumario != null
                && severity != null) {
            if (severity.equals(FacesMessage.SEVERITY_INFO)) {
                Mensagem.msgSeverityInfo(msg, "Informação");
            } else if (severity.equals(FacesMessage.SEVERITY_ERROR)) {
                Mensagem.msgSeverityError(msg, "Erro");
            } else if (!severity.equals(FacesMessage.SEVERITY_FATAL)) {
                Mensagem.msgSeverityError(msg, "Erro");
            } else if (!severity.equals(FacesMessage.SEVERITY_WARN)) {
                Mensagem.msgSeverityWarn(msg, "Atenção");
            }
        }
    }

    protected void sucesso() throws Exception {
        statusOperation(StatusPersistencia.SUCESSO);
    }

    protected void erro() throws Exception {
        statusOperation(StatusPersistencia.ERRO);
    }
}
