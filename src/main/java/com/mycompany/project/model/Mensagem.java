/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author lucia
 */
@Audited
@Entity
@Table(name = "mensagem")
@SequenceGenerator(name = "mensagem_seq", initialValue = 1, allocationSize = 1)
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensagem_seq")
    @IdentificaCampoPesquisa(campoBancoDeDados = "codigo", descricaoCampoEmTela = "Código")
    private Long codigo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @ForeignKey(name = "mensagem_fk1")
    @JoinColumn(name = "usuario_orig", referencedColumnName = "ent_codigo")
    @IdentificaCampoPesquisa(campoBancoDeDados = "usuarioOrigem.ent_login", descricaoCampoEmTela = "Usuário Origem (Login)", ordemCampoEmTela = 2)
    private Entidade usuarioOrigem;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @ForeignKey(name = "mensagem_fk2")
    @JoinColumn(name = "usuario_dest", referencedColumnName = "ent_codigo")
    @IdentificaCampoPesquisa(campoBancoDeDados = "usuarioDestino.ent_login", descricaoCampoEmTela = "Usuário Destino (Login)", ordemCampoEmTela = 3)
    private Entidade usuarioDestino;

    @NotNull
    @Column(name = "lida")
    private Boolean lida;

    @NotNull
    @Column(name = "data_mensagem")
    @IdentificaCampoPesquisa(campoBancoDeDados = "data_mensagem", descricaoCampoEmTela = "Data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMensagem;

    @NotNull
    @Column(name = "assunto")
    @Length(max = 80)
    @IdentificaCampoPesquisa(campoBancoDeDados = "assunto", descricaoCampoEmTela = "Assunto")
    private String assunto;

    @NotNull
    @Column(name = "descricao")
    @Length(max = 1000)
    @IdentificaCampoPesquisa(campoBancoDeDados = "descricao", descricaoCampoEmTela = "Descrição", ordemCampoEmTela = 1)
    private String descricao;

    @NotNull
    @Column(name = "exigir_resposta")
    private Boolean exigirResposta;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    public Mensagem() {
        this.exigirResposta = false;
        this.lida = false;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Entidade getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(Entidade usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public Entidade getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Entidade usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getExigirResposta() {
        return exigirResposta;
    }

    public void setExigirResposta(Boolean exigirResposta) {
        this.exigirResposta = exigirResposta;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    /**
     * Método para montar um JSON da classe mensagem e retornar
     *
     * @return
     */
    public JSONObject getJson() {
        Map<Object, Object> map = new HashMap<>();
        map.put("codigo", getCodigo());
        map.put("lida", getLida());
        map.put("assunto", getAssunto());

        return new JSONObject(map);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final Mensagem other = (Mensagem) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Mensagem{" + "codigo=" + codigo + ", assunto=" + assunto + ", descricao=" + descricao + '}';
    }

}
