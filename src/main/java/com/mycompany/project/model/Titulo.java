/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "titulo")
@SequenceGenerator(name = "titulo_seq", initialValue = 1, allocationSize = 1)
public class Titulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titulo_seq")
    @IdentificaCampoPesquisa(campoBancoDeDados = "codigo", descricaoCampoEmTela = "Número", ordemCampoEmTela = 1)
    @NotNull
    @Column(name = "codigo")
    private Long codigo;

    @NotNull
    @Size(max = 100)
    @Column(name = "pessoa")
    @IdentificaCampoPesquisa(campoBancoDeDados = "pessoa", descricaoCampoEmTela = "Pessoa", ordemCampoEmTela = 2)
    private String pessoa;

    @NotNull
    @Column(name = "valor", scale = 2, precision = 15)
    @IdentificaCampoPesquisa(campoBancoDeDados = "valor", descricaoCampoEmTela = "Valor R$", ordemCampoEmTela = 4)
    private BigDecimal valor;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data", updatable = false) //uppdatable = false -> Não vai atualizar esse campo em updates, apenas na inclusão!
    @IdentificaCampoPesquisa(campoBancoDeDados = "data", descricaoCampoEmTela = "Data Emissão", ordemCampoEmTela = 3)
    private Date data;

    @NotNull
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_abertura", updatable = false)
    @ForeignKey(name = "titulo_fk1")
    //Nome campo banco de dados deve ser o mesmo do atributo para tabelas relacionadas (entidadeUsuarioAbertura)
    @IdentificaCampoPesquisa(campoBancoDeDados = "entidadeUsuarioAbertura.nome", descricaoCampoEmTela = "Usuário Abertura", ordemCampoEmTela = 5)
    private Entidade entidadeUsuarioAbertura;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    public Titulo() {
        this.valor = BigDecimal.ZERO;
        this.data = new Date();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Entidade getEntidadeUsuarioAbertura() {
        return entidadeUsuarioAbertura;
    }

    public void setEntidadeUsuarioAbertura(Entidade entidadeUsuarioAbertura) {
        this.entidadeUsuarioAbertura = entidadeUsuarioAbertura;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    public String getTipoTituloStr() {
        if (getTipo() != null) {
            if (getTipo().equals("R")) {
                return "Contas a Receber";
            } else {
                return "Contas a Pagar";
            }
        }

        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Titulo other = (Titulo) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Titulo{" + "codigo=" + codigo + '}';
    }

}
