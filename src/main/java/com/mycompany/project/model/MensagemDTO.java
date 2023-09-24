/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucia
 */
public class MensagemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMensagem;

    private Entidade usuarioOrigem;

    private String assunto;

    private String mensagem;

    private Boolean exigirResposta;

    public MensagemDTO() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getExigirResposta() {
        return exigirResposta;
    }

    public void setExigirResposta(Boolean exigirResposta) {
        this.exigirResposta = exigirResposta;
    }

    public Entidade getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(Entidade usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MensagemDTO other = (MensagemDTO) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "MensagemDTO{" + "codigo=" + codigo + ", dataMensagem=" + dataMensagem + ", usuarioOrigem=" + usuarioOrigem + ", assunto=" + assunto + ", mensagem=" + mensagem + ", exigirResposta=" + exigirResposta + '}';
    }

}
