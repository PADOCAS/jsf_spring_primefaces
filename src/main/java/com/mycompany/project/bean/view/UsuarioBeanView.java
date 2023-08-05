/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.carregamento.lazy.CarregamentoLazyListForObject;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Entidade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.ValidationException;
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
@Scope(value = "view")
@ManagedBean(name = "usuarioBeanView")
public class UsuarioBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    private final String url = "/cadastro/cad_usuario.jsf?faces-redirect=true";

    private final String urlFind = "/cadastro/consulta/find_usuario.jsf?faces-redirect=true";

    private Entidade objetoSelecionado;

    //acao: 0(default-insert), 1(update)
    private String acao;

    private Entidade objetoAlteracao;

    private Boolean enableButtonsAcao = true;

    private CarregamentoLazyListForObject<Entidade> list = new CarregamentoLazyListForObject<>();

    @Autowired
    private EntidadeController entidadeController;

    @Override
    protected Class<?> getClassImplement() {
        return Entidade.class;
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return entidadeController;
    }

    @Override
    public String getUrfFindEntidade() {
        return "/cadastro/consulta/find_usuario";
    }

    @Override
    public String getUrfEntidade() {
        return "/cadastro/cad_usuario";
    }

    @Override
    public void initComponentes() {
        super.initComponentes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody

        try {
            if (FacesContext.getCurrentInstance() != null
                    && FacesContext.getCurrentInstance().getExternalContext() != null
                    && FacesContext.getCurrentInstance().getExternalContext().getFlash() != null
                    && FacesContext.getCurrentInstance().getExternalContext().getFlash().get("acao") != null
                    && FacesContext.getCurrentInstance().getExternalContext().getFlash().get("objetoAlteracao") != null) {
                setAcao((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("acao"));
                setObjetoAlteracao((Entidade) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("objetoAlteracao"));
                //Após utilizar da um clear nos parâmetros para não deixar em memória
                FacesContext.getCurrentInstance().getExternalContext().getFlash().remove("acao");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().remove("objetoAlteracao");
            }

            if (getAcao() == null
                    || getAcao().equals("0")) {
                //Insert:
                setarVariaveisNulas();
            } else if (getAcao().equals("1")
                    && getObjetoAlteracao() != null) {
                setObjetoSelecionado(getObjetoAlteracao());
            } else {
                setarVariaveisNulas();
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setarVariaveisNulas() throws Exception {
        setObjetoSelecionado(new Entidade());
        getObjetoSelecionado().setTipo("F");
        getObjetoSelecionado().setInativo(false);

        setAcao("0");
        setObjetoAlteracao(null);
        consultarEntidade();

        if (FacesContext.getCurrentInstance() != null
                && FacesContext.getCurrentInstance().getExternalContext() != null
                && FacesContext.getCurrentInstance().getExternalContext().getFlash() != null
                && FacesContext.getCurrentInstance().getExternalContext().getFlash().get("acao") != null
                && FacesContext.getCurrentInstance().getExternalContext().getFlash().get("objetoAlteracao") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().remove("acao");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().remove("objetoAlteracao");
        }
    }

    @Override
    public String novo() {
        try {
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url;
    }

    @Override
    public String redirecionarFindEntidade() throws Exception {
        setarVariaveisNulas();
        return urlFind;
    }

    @Override
    public void consultarEntidade() throws Exception {
        try {
            objetoSelecionado = new Entidade();
            list.clean();
            list.setTotRegConsulta(getTotalRegistroConsulta(), getSqlLazyQueryTotRegistro());
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError(ex.getMessage(), "Erro");
        }
    }

    @Override
    public String getMessageExclusao() throws Exception {
        if (getObjetoSelecionado() != null
                && getObjetoSelecionado().getCodigo() != null
                && getObjetoSelecionado().getLogin() != null) {
            StringBuilder str = new StringBuilder();
            str.append("Confirma a exclusão do Usuário: (").append(getObjetoSelecionado().getCodigo()).append(") ");
            str.append(getObjetoSelecionado().getLogin()).append(" ?");

            return str.toString();
        }

        return "";
    }

    @Override
    public String save() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", false);

        try {
            validaSenha();
            setEnableButtonsAcao(false);
            //Inclusão de novo usuário, gravar a senha:
            if (acao != null
                    && acao.equals("0")
                    && getObjetoSelecionado() != null
                    && getObjetoSelecionado().getSenhaString() != null) {
                //Gerar um salt aleatório (BCrypt.getsalt()):
                getObjetoSelecionado().setSenha(BCrypt.hashpw(getObjetoSelecionado().getSenhaString(), BCrypt.gensalt()));
            }
            setObjetoSelecionado(entidadeController.merge(getObjetoSelecionado()));
            Mensagem.msgSalvoComSucesso();
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", true);
        } catch (ValidationException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>" + ex.getMessage(), "Atenção");
            setEnableButtonsAcao(true);
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>Registro já foi alterado por outro usuário.<br>Retorne a consulta de usuários e selecione novamente para alteração.", "Atenção");
            setEnableButtonsAcao(true);
            //Caso der erro, mantém na mesma página:
            return "";
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
            setEnableButtonsAcao(true);
            throw new Exception(ex);
        }

        //Tudo ok, retorna para página de pesquisa:
        return urlFind;
    }

    @Override
    public String saveNew() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", false);

        try {
            validaSenha();
            setEnableButtonsAcao(false);
            //Inclusão de novo usuário, gravar a senha:
            if (acao != null
                    && acao.equals("0")
                    && getObjetoSelecionado() != null
                    && getObjetoSelecionado().getSenhaString() != null) {
                //Gerar um salt aleatório (BCrypt.getsalt()):
                getObjetoSelecionado().setSenha(BCrypt.hashpw(getObjetoSelecionado().getSenhaString(), BCrypt.gensalt()));
            }
            entidadeController.merge(getObjetoSelecionado());
            Mensagem.msgSalvoComSucesso();
            setarVariaveisNulas();
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", true);
        } catch (ValidationException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>" + ex.getMessage(), "Atenção");
            setEnableButtonsAcao(true);
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>Registro já foi alterado por outro usuário.<br>Retorne a consulta de usuários e selecione novamente para alteração.", "Atenção");
            setEnableButtonsAcao(true);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
            setEnableButtonsAcao(true);
            throw new Exception(ex);
        }

        return "";
    }

    private void validaSenha() throws ValidationException {
        if (getObjetoSelecionado() != null
                && getObjetoSelecionado().getSenhaString() != null
                && getObjetoSelecionado().getConfirmaSenha() != null) {
            //Senha e confirmação devem ser iguais:
            if (!getObjetoSelecionado().getSenhaString().equals(getObjetoSelecionado().getConfirmaSenha())) {
                throw new ValidationException("A senha e a confirmação não conferem!");
            }
        }
    }

    @Override
    public void validEditar() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", false);

        if (getObjetoSelecionado() == null
                || getObjetoSelecionado().getCodigo() == null) {
            Mensagem.msgSeverityWarn("Selecione um registro para altera-lo.", "Atenção");
        } else {
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", true);
        }
    }

    @Override
    public String editar() throws Exception {
        if (getObjetoSelecionado() != null
                && getObjetoSelecionado().getCodigo() != null
                && FacesContext.getCurrentInstance() != null
                && FacesContext.getCurrentInstance().getExternalContext() != null
                && FacesContext.getCurrentInstance().getExternalContext().getFlash() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("acao", "1");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("objetoAlteracao", getObjetoSelecionado());
            //Redireciona diratemente com os parâmetros setadas no flash (depois apenas retorna null para o método para não ser redirecionado 2 vezes):
//            FacesContext.getCurrentInstance().getExternalContext().redirect(PrimeFacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + url);
        }

        return url;
    }

    @Override
    public void validExclusao() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", false);

        if (getObjetoSelecionado() == null
                || getObjetoSelecionado().getCodigo() == null) {
            Mensagem.msgSeverityWarn("Selecione um registro para excluí-lo.", "Atenção");
        } else {
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
        }
    }

    @Override
    public void excluir() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", false);
        try {
            if (getObjetoSelecionado() != null
                    && getObjetoSelecionado().getCodigo() != null) {
                //Find no objeto atual, para evitar que está diferente no banco de dados por alguma alteração que aqui ainda está em cache!
                Entidade entidadeDel = entidadeController.findByPorId(Entidade.class, getObjetoSelecionado().getCodigo());

                if (entidadeDel != null) {
                    entidadeController.delete(entidadeDel);
                    setarVariaveisNulas();
                    Mensagem.msgExcluidoComSucesso();

                    PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
                }
            } else {
                Mensagem.msgSeverityWarn("Edite um registro para poder excluí-lo.", "Erro");
            }
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao Excluir!<br><br>Registro foi alterado por outro usuário,<br>retorne a consulta de usuários e tente excluí-lo novamente.", "Atenção");
        } catch (Exception ex) {
            if (ex.getMessage().contains("No row with the given identifier exists")) {
                Mensagem.msgSeverityWarn("Erro ao Excluir!<br><br>Esse registro já foi excluído por outro usuário.", "Atenção");
            } else {
                //Caso der outro tipo de exception, deixar para o tratamento de exception padrão nosso tratar (CustomExceptionHandler)
                Logger.getLogger(UsuarioBeanView.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
        }
    }

    @Override
    public String condicaoAndParaPesquisa() throws Exception {
        return null;
    }

    public Entidade getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Entidade objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Entidade getObjetoAlteracao() {
        return objetoAlteracao;
    }

    public void setObjetoAlteracao(Entidade objetoAlteracao) {
        this.objetoAlteracao = objetoAlteracao;
    }

    public Boolean getEnableButtonsAcao() {
        return enableButtonsAcao;
    }

    public void setEnableButtonsAcao(Boolean enableButtonsAcao) {
        this.enableButtonsAcao = enableButtonsAcao;
    }

    public CarregamentoLazyListForObject<Entidade> getList() {
        return list;
    }

    public void setList(CarregamentoLazyListForObject<Entidade> list) {
        this.list = list;
    }

}
