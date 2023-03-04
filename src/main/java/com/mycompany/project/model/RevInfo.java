/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.listener.CustomListener;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 *
 * @author lucia
 */
@Table(name = "revinfo", schema = "public")
@Entity
@RevisionEntity(CustomListener.class)
public class RevInfo extends DefaultRevisionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "entidade_fk")
    @JoinColumn(nullable = false, name = "entidade")
    private Entidade entidade;

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

}
