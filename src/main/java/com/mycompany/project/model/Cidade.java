/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

/**
 *
 * @author lucia
 */
@Audited
@Entity
@Table(name = "cidade")
@SequenceGenerator(name = "cidade_seq", initialValue = 1, allocationSize = 1)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_seq")
    @IdentificaCampoPesquisa(campoBancoDeDados = "codigo", descricaoCampoEmTela = "Código")
    @Column(name = "codigo")
    private Long codigo;

    @NotNull
    @IdentificaCampoPesquisa(campoBancoDeDados = "nome", descricaoCampoEmTela = "Nome", ordemCampoEmTela = 1)
    @Size(max = 50)
    @Column(name = "nome")
    private String nome;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    //FK ESTADO:
    @NotNull
    //Identificação campo pesquisa no cadatro pega do banco atravé do relacionamento.nome (estado.nome)
    @IdentificaCampoPesquisa(campoBancoDeDados = "estado.nome", descricaoCampoEmTela = "Estado")
    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "codigo")
    @ForeignKey(name = "cidade_fk1")
    private Estado estado;

    public Cidade() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.codigo);
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
        final Cidade other = (Cidade) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Cidade{" + "codigo=" + codigo + ", nome=" + nome + '}';
    }

}
