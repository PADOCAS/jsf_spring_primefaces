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
import javax.faces.context.FacesContext;
import org.primefaces.context.PrimeRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
                return entidade.getUltimoacesso();
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
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", false);

        try {
            if (entidadeAlterarSenha != null
                    && entidadeAlterarSenha.getSenhaAtual() != null
                    && entidadeAlterarSenha.getSenhaNova() != null
                    && entidadeAlterarSenha.getSenhaNovaConfirmacao() != null
                    && contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && repositoryLogin != null) {
                //Senha são criptografadas no banco, validar com criptografia:
                validAlteracaoSenha();

                //Atualizar senha:
                //Gerar um salt aleatório (BCrypt.getsalt()):
                contextoBean.getEntidadeLogada().setSenha(BCrypt.hashpw(entidadeAlterarSenha.getSenhaNova(), BCrypt.gensalt()));
                entidadeController.saveOrUpdate(contextoBean.getEntidadeLogada());
                //Busca o objeto no banco para pegar atualizado:
                Entidade entidadeLogadaCharged = entidadeController.findByPorId(Entidade.class, contextoBean.getEntidadeLogada().getCodigo());

                //Atualizar o usuário logado na sessão:
                if (entidadeLogadaCharged != null
                        && entidadeLogadaCharged.getCodigo() != null
                        && entidadeLogadaCharged.getSenha() != null
                        && entidadeLogadaCharged.getLogin() != null
                        && FacesContext.getCurrentInstance() != null
                        && FacesContext.getCurrentInstance().getExternalContext() != null) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogadoSessao", entidadeLogadaCharged);
                }

                PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", true);
            }
        } catch (Exception ex) {
            Logger.getLogger(EntidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError("Erro ao alterar senha!<br><br>" + ex.getMessage(), "Erro");
        }
    }

    public void msgUpdateSenhaComSucesso() {
        Mensagem.msgSeverityInfo("Senha alterada com sucesso!", "OK");
    }

    private void validAlteracaoSenha() throws Exception {
        if (entidadeAlterarSenha != null
                && entidadeAlterarSenha.getSenhaAtual() != null
                && entidadeAlterarSenha.getSenhaNova() != null
                && entidadeAlterarSenha.getSenhaNovaConfirmacao() != null
                && contextoBean != null
                && contextoBean.getEntidadeLogada() != null
                && contextoBean.getEntidadeLogada().getLogin() != null
                && repositoryLogin != null) {
            //Senha atual válida:
            if (!repositoryLogin.autenticaUsuario(contextoBean.getEntidadeLogada().getLogin(), entidadeAlterarSenha.getSenhaAtual())) {
                throw new Exception("Senha atual não é válida!");
            }

            //Senha atual igual a senha nova:
            if (repositoryLogin.autenticaUsuario(contextoBean.getEntidadeLogada().getLogin(), entidadeAlterarSenha.getSenhaNova())) {
                throw new Exception("Senha nova não deve ser igual a senha atual!");
            }

            //Senha nova e senha confirmação devem ser iguais:
            if (!entidadeAlterarSenha.getSenhaNova().equals(entidadeAlterarSenha.getSenhaNovaConfirmacao())) {
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
