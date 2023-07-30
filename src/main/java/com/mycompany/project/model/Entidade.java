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
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author lucia
 */
@Audited
@Entity
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "ent_codigo")
    private Long ent_codigo;

    @NotNull
    @Size(max = 20)
    @IdentificaCampoPesquisa(campoBancoDeDados = "ent_login", descricaoCampoEmTela = "Login", ordemCampoEmTela = 1)
    @Column(name = "ent_login")
    private String ent_login;

    @NotNull
    @Column(name = "ent_senha")
    private String ent_senha;

    @Column(name = "ent_inativo")
    private Boolean ent_inativo;

    @NotNull
    @Size(max = 50)
    @IdentificaCampoPesquisa(campoBancoDeDados = "ent_nome", descricaoCampoEmTela = "Nome", ordemCampoEmTela = 2)
    @Column(name = "ent_nome")
    private String ent_nome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ent_ultimoacesso")
    private Date ent_ultimoacesso;

    @NotNull
    @Size(max = 1)
    @Column(name = "ent_tipo")
    private String ent_tipo;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    public Entidade() {
    }

    public Long getEnt_codigo() {
        return ent_codigo;
    }

    public void setEnt_codigo(Long ent_codigo) {
        this.ent_codigo = ent_codigo;
    }

    public String getEnt_login() {
        return ent_login;
    }

    public void setEnt_login(String ent_login) {
        this.ent_login = ent_login;
    }

    public String getEnt_senha() {
        return ent_senha;
    }

    public void setEnt_senha(String ent_senha) {
        this.ent_senha = ent_senha;
    }

    public Boolean getEnt_inativo() {
        return ent_inativo;
    }

    public void setEnt_inativo(Boolean ent_inativo) {
        this.ent_inativo = ent_inativo;
    }

    public String getEnt_nome() {
        return ent_nome;
    }

    public void setEnt_nome(String ent_nome) {
        this.ent_nome = ent_nome;
    }

    public Date getEnt_ultimoacesso() {
        return ent_ultimoacesso;
    }

    public void setEnt_ultimoacesso(Date ent_ultimoacesso) {
        this.ent_ultimoacesso = ent_ultimoacesso;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    public String getEnt_tipo() {
        return ent_tipo;
    }

    public void setEnt_tipo(String ent_tipo) {
        this.ent_tipo = ent_tipo;
    }

    /**
     * Método para montar um JSON da classe Entidade e retornar
     *
     * @return
     */
    public JSONObject getJson() {
        Map<Object, Object> map = new HashMap<>();
        map.put("ent_codigo", getEnt_codigo());
        map.put("ent_login", getEnt_login());
        map.put("ent_nome", getEnt_nome());

        return new JSONObject(map);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.ent_codigo);
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
        final Entidade other = (Entidade) obj;
        return Objects.equals(this.ent_codigo, other.ent_codigo);
    }

}
