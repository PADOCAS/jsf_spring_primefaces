/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

/**
 *
 * @author lucia
 */
@Audited
@Entity
@Table(name = "mensagem_resposta")
@SequenceGenerator(name = "mensagem_resposta_seq", initialValue = 1, allocationSize = 1)
public class MensagemResposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensagem_resposta_seq")
    private Long codigo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @ForeignKey(name = "mensagem_resposta_fk1")
    @JoinColumn(name = "cod_mensagem_pai", referencedColumnName = "codigo")
    private Mensagem mensagemPai;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @ForeignKey(name = "mensagem_resposta_fk2")
    @JoinColumn(name = "cod_mensagem_resposta", referencedColumnName = "codigo")
    private Mensagem mensagemResposta;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    public MensagemResposta() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Mensagem getMensagemPai() {
        return mensagemPai;
    }

    public void setMensagemPai(Mensagem mensagemPai) {
        this.mensagemPai = mensagemPai;
    }

    public Mensagem getMensagemResposta() {
        return mensagemResposta;
    }

    public void setMensagemResposta(Mensagem mensagemResposta) {
        this.mensagemResposta = mensagemResposta;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.codigo);
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
        final MensagemResposta other = (MensagemResposta) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "MensagemResposta{" + "codigo=" + codigo + ", mensagemPai=" + mensagemPai + ", mensagemResposta=" + mensagemResposta + '}';
    }
}
