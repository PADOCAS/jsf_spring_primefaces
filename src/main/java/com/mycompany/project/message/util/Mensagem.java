/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.message.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author lucia
 */
public abstract class Mensagem extends FacesContext implements Serializable {

    private static final long serialVersionUID = 1L;

    public Mensagem() {
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    private static boolean facesContextValido() {
        return getFacesContext() != null;
    }

    public static void responseOperation(StatusPersistencia status) {
        if (status != null
                && status.equals(StatusPersistencia.SUCESSO)) {
            msgOperacaoRealizadaComSucesso();
        } else if (status != null
                && status.equals(StatusPersistencia.OBJETO_REFERENCIADO)) {
            msgSeverityFatal(StatusPersistencia.OBJETO_REFERENCIADO.toString());
        } else if (status != null
                && status.equals(StatusPersistencia.ERRO)) {
            msgErroNaOperacao();
        }
    }

    //Mensagens Genéricas já prontas:
    public static void msgOperacaoRealizadaComSucesso() {
        msgSeverityInfo(ConstanteMensagemSistema.OPERACAO_REALIZADA_COM_SUCESSO);
    }

    public static void msgSalvoComSucesso() {
        msgSeverityInfo(ConstanteMensagemSistema.REGISTRO_SALVO_COM_SUCESSO);
    }

    public static void msgExcluidoComSucesso() {
        msgSeverityInfo(ConstanteMensagemSistema.REGISTRO_EXCLUIDO_COM_SUCESSO);
    }

    public static void msgErroNaOperacao() {
        msgSeverityError(ConstanteMensagemSistema.ERRO_OPERACAO);
    }

    //Métodos para chamar mensagens criadas nos programas:
    public static void msgSeverityWarn(String msg) {
        if (facesContextValido()) {
            getFacesContext().addMessage(msg, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
        }
    }

    public static void msgSeverityInfo(String msg) {
        if (facesContextValido()) {
            getFacesContext().addMessage(msg, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        }
    }

    public static void msgSeverityError(String msg) {
        if (facesContextValido()) {
            getFacesContext().addMessage(msg, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    public static void msgSeverityFatal(String msg) {
        if (facesContextValido()) {
            getFacesContext().addMessage(msg, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
        }
    }
}
