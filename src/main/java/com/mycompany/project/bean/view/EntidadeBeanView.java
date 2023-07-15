/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.bean.geral.EntidadeAlterarSenha;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Entidade;
import com.mycompany.repository.interfaces.RepositoryLogin;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    //Injeção de dependência do DAOLogin:
    @Autowired
    private RepositoryLogin repositoryLogin;

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
        try {
            if (entidadeAlterarSenha != null
                    && entidadeAlterarSenha.getSenhaAtual() != null
                    && entidadeAlterarSenha.getSenhaNova() != null
                    && entidadeAlterarSenha.getSenhaNovaConfirmacao() != null
                    && contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && repositoryLogin != null) {
                //Fazer método para validar senha atual criptografando e autenticando para ver se é valida
                validAlteracaoSenha();

                //Método para valida autenticacao do usuario temos no DAOLogin! ver da onde vai ser pego na aula antes de fazer aqui.. caso precise injetar e tal..!!
//                return repositoryLogin.autenticaUsuario(login, senha);
            }
        } catch (Exception ex) {
            Logger.getLogger(EntidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError("Erro ao alterar senha!<br><br>" + ex.getMessage(), "Erro");
        }
    }

    private void validAlteracaoSenha() throws Exception {
        if (entidadeAlterarSenha != null
                && entidadeAlterarSenha.getSenhaAtual() != null
                && entidadeAlterarSenha.getSenhaNova() != null
                && entidadeAlterarSenha.getSenhaNovaConfirmacao() != null
                && contextoBean != null
                && contextoBean.getEntidadeLogada() != null
                && contextoBean.getEntidadeLogada().getEnt_login() != null
                && repositoryLogin != null) {
            //Senha atual válida:
            if (!repositoryLogin.autenticaUsuario(contextoBean.getEntidadeLogada().getEnt_login(), entidadeAlterarSenha.getSenhaAtual())) {
                throw new Exception("Senha atual não é válida!");
            }

            //Senha atual igual a senha nova:
            if (repositoryLogin.autenticaUsuario(contextoBean.getEntidadeLogada().getEnt_login(), entidadeAlterarSenha.getSenhaNova())) {
                throw new Exception("Senha atual não deve ser igual a senha nova!");
            }
            
            // TODOOOOOOOOOO: PAREI AQUI
            //Senha nova e senha confirmação devem ser iguais:
            if(!entidadeAlterarSenha.getSenhaNova().equals(entidadeAlterarSenha.getSenhaNovaConfirmacao())) {
                throw new Exception("A nova senha e a confirmação não conferem!");
            }
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
