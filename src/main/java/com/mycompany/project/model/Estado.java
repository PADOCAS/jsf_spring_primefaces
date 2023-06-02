/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 *
 * @author lucia
 */
@Audited
@Entity
@Table(name = "estado")
@SequenceGenerator(name = "estado_seq", initialValue = 1, allocationSize = 1)
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_seq")
    @IdentificaCampoPesquisa(campoBancoDeDados = "codigo", descricaoCampoEmTela = "Código")
    @Column(name = "codigo")
    private Long codigo;

    @NotNull
    @IdentificaCampoPesquisa(campoBancoDeDados = "nome", descricaoCampoEmTela = "Nome", ordemCampoEmTela = 1)
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    //FK Pais:
    @NotNull
    //Identificação campo pesquisa no cadatro pega do banco atravé do relacionamento.nome (estado.nome)
    @IdentificaCampoPesquisa(campoBancoDeDados = "pais.nome", descricaoCampoEmTela = "País")
    //Carregamento sempre do objeto Pais (EAGER) - LAZY preguiçoso, apenas quando for usado
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id", referencedColumnName = "codigo")
    @ForeignKey(name = "estado_fk1")
    private Pais pais;

    //Relacionamento de volta, não auditado:
    @NotAudited
    @OneToMany(mappedBy = "estado", orphanRemoval = false)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Cidade> listCidade;

    public Estado() {
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Cidade> getListCidade() {
        return listCidade;
    }

    public void setListCidade(List<Cidade> listCidade) {
        this.listCidade = listCidade;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.codigo);
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
        final Estado other = (Estado) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Estado{" + "codigo=" + codigo + ", nome=" + nome + '}';
    }

}
