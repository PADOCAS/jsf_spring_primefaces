/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.model;

import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
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
@Table(name = "entidade")
@SequenceGenerator(name = "entidade_seq", initialValue = 1, allocationSize = 1)
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidade_seq")
    @NotNull
    @Column(name = "codigo")
    private Long codigo;

    @NotNull
    @Size(max = 20)
    @IdentificaCampoPesquisa(campoBancoDeDados = "login", descricaoCampoEmTela = "Login", ordemCampoEmTela = 1)
    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "senha")
    private String senha;

    @Transient
    @Column(name = "senha_string")
    private String senhaString;

    @Transient
    @Column(name = "confirma_senha")
    private String confirmaSenha;

    @Column(name = "inativo")
    private Boolean inativo;

    @NotNull
    @Size(max = 50)
    @IdentificaCampoPesquisa(campoBancoDeDados = "nome", descricaoCampoEmTela = "Nome", ordemCampoEmTela = 2)
    @Column(name = "nome")
    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimoacesso")
    private Date ultimoacesso;

    @NotNull
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;

    //Tratamento para registrar a sequencia de interações que foram feita (inclusão, alteração, etc.)
    @Version
    @Column(name = "versionnum")
    private Integer versionnum;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    //Fazendo carregar o relacionamento com ACESSOS! -> Como não temos VO de entidadeacesso, criamos um mapeamento com STRING mesmo!
    //Ele vai carregar sempre os acessos do usuário, caso for deletar, vai deletar todos os acessos dele antes.
    //Chave unique criada com os 2 campos (codigo, esa_codigo) para não deixar trazer registros repetidos, é uma chave com os campos.
    //O relacionamento com esse joinTable é com a coluna codigo da tabela entidade x entidadeacessos
    //Set de String retornando a coluna esa_codigo
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @JoinTable(name = "entidadeacesso", uniqueConstraints = {
        @UniqueConstraint(name = "unique_acesso_entidade_uk1", columnNames = {
            "codigo", "esa_codigo"
        })}, joinColumns = {
        @JoinColumn(name = "codigo")
    })
    @Column(name = "esa_codigo", length = 20)
    private Set<String> acessos = new HashSet<>();

    public Entidade() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getUltimoacesso() {
        return ultimoacesso;
    }

    public void setUltimoacesso(Date ultimoacesso) {
        this.ultimoacesso = ultimoacesso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(Integer versionnum) {
        this.versionnum = versionnum;
    }

    public Set<String> getAcessos() {
        return acessos;
    }

    public void setAcessos(Set<String> acessos) {
        this.acessos = acessos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getSenhaString() {
        return senhaString;
    }

    public void setSenhaString(String senhaString) {
        this.senhaString = senhaString;
    }
    
    public String getTipoDescricao() {
        if(getTipo() != null) {
            if(getTipo().equals("A")) {
                return "ADMINISTRADOR";
            } else if(getTipo().equals("U")) {
                return "USUÁRIO";
            }
        }
        
        return null;
    }

    /**
     * Método para montar um JSON da classe Entidade e retornar
     *
     * @return
     */
    public JSONObject getJson() {
        Map<Object, Object> map = new HashMap<>();
        map.put("codigo", getCodigo());
        map.put("login", getLogin());
        map.put("nome", getNome());

        return new JSONObject(map);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.getCodigo());
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
        return Objects.equals(this.codigo, other.codigo);
    }

}
