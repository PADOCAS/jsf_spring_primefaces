/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.Audited;

/**
 *
 * @author lucia
 */
@Audited
@Entity
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long ent_codigo;

    private String ent_login;

    private String ent_senha;

    private Boolean ent_inativo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ent_ult_acesso;

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

    public Date getEnt_ult_acesso() {
        return ent_ult_acesso;
    }

    public void setEnt_ult_acesso(Date ent_ult_acesso) {
        this.ent_ult_acesso = ent_ult_acesso;
    }

}
