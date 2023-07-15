/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.bean.geral.EntidadeAlterarSenha;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.model.Entidade;
import com.mycompany.repository.interfaces.RepositoryLogin;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "session")
@ManagedBean(name = "entidadeBeanView")
public class EntidadeBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ContextoBean contextoBean;

    @Autowired
    private EntidadeController entidadeController;

    private EntidadeAlterarSenha entidadeAlterarSenha;

    @Override
    public void initComponentes() {
        super.initComponentes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        entidadeAlterarSenha = new EntidadeAlterarSenha();
    }

    public String getUsuarioLogadoSecurity() {
        if (contextoBean != null
                && contextoBean.getAuthentication() != null
                && contextoBean.getAuthentication().getName() != null) {
            return contextoBean.getAuthentication().getName();
        }

        return "";
    }

    public Date getUltimoAcesso() throws Exception {
        if (contextoBean != null) {
            Entidade entidade = contextoBean.getEntidadeLogada();

            if (entidade != null) {
                return entidade.getEnt_ultimoacesso();
            }
        }

        return null;
    }

    public void iniciarAlteraracaoSenha() {
        entidadeAlterarSenha = new EntidadeAlterarSenha();
    }

    /**
     * Método para alteração de senha do usuário
     */
    public void updateSenha() {
        if (entidadeAlterarSenha != null
                && entidadeAlterarSenha.getSenhaAtual() != null
                && entidadeAlterarSenha.getSenhaNova() != null
                && entidadeAlterarSenha.getSenhaNovaConfirmacao() != null
                && contextoBean != null
                && contextoBean.getAuthentication() != null
                && contextoBean.getAuthentication().getName() != null) {
            //Usuario logado: contextoBean.getAuthentication().getName()
            
            //Método para valida autenticacao do usuario temos no DAOLogin! ver da onde vai ser pego na aula antes de fazer aqui.. caso precise injetar e tal..!!
//                return repositoryLogin.autenticaUsuario(login, senha);

//TODOOOOOOOOO: VERIFICAR SE SENHA NOVA E CONFIRMACAO DE SENHA NOVA BATEM            
//TODOOOOOOOOO: VERIFICAR SE SENHA QUE O USUÁRIO DIGITOU ATUAL ESTÁ CORRETA:
//TODOOOOOOOOO: SE TUDO OK, GRAVAR NO BANCO A NOVA SENHA CRIPTOGRAFADA!
        }
    }

    @Override
    protected Class<?> getClassImplement() {
        return Entidade.class;
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return entidadeController;
    }

    @Override
    public String condicaoAndParaPesquisa() throws Exception {
        return null;
    }

    public EntidadeAlterarSenha getEntidadeAlterarSenha() {
        return entidadeAlterarSenha;
    }

    public void setEntidadeAlterarSenha(EntidadeAlterarSenha entidadeAlterarSenha) {
        this.entidadeAlterarSenha = entidadeAlterarSenha;
    }

}
