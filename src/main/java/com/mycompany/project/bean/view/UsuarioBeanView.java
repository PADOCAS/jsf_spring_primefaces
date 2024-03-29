/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.acessos.Permissao;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.carregamento.lazy.CarregamentoLazyListForObject;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Entidade;
import com.mycompany.project.util.all.RegexUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.ValidationException;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
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

    private DualListModel<Permissao> listPickAcessos;

    @Autowired
    private EntidadeController entidadeController;

    @Autowired
    private ContextoBean contextoBean;

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

            chargedPickList();
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

    private void chargedPickList() {
        if (getObjetoSelecionado() != null) {
            List<Permissao> acessosSelecionados = new ArrayList<>();
            // Lista formada através de um Arrays.asList fica uma lista imutável, não podendo alterar elementos, para resolver vamos criar uma lista mutavél através da imutável            
            List<Permissao> acessosDisponiveis = new ArrayList<>(Arrays.asList(Permissao.values()));
            acessosDisponiveis.remove(Permissao.ADMIN);
            acessosDisponiveis.remove(Permissao.USER);
            acessosDisponiveis.remove(Permissao.CADASTRO_ACESSAR);
            acessosDisponiveis.remove(Permissao.MOVIMENTO_ACESSAR);
            acessosDisponiveis.remove(Permissao.CIDADE_ACESSAR);
            acessosDisponiveis.remove(Permissao.USUARIO_ACESSAR);
            acessosDisponiveis.remove(Permissao.TITULO_ACESSAR);

            if (getObjetoSelecionado().getEntidadeAcessosPermissao() != null
                    && !getObjetoSelecionado().getEntidadeAcessosPermissao().isEmpty()) {
                getObjetoSelecionado().getEntidadeAcessosPermissao().stream()
                        .forEach(permissao -> {
                            acessosSelecionados.add(permissao);
                            acessosDisponiveis.remove(permissao);
                        });
            }

            acessosSelecionados.remove(Permissao.ADMIN);
            acessosSelecionados.remove(Permissao.USER);
            acessosSelecionados.remove(Permissao.CADASTRO_ACESSAR);
            acessosSelecionados.remove(Permissao.MOVIMENTO_ACESSAR);
            acessosSelecionados.remove(Permissao.CIDADE_ACESSAR);
            acessosSelecionados.remove(Permissao.USUARIO_ACESSAR);
            acessosSelecionados.remove(Permissao.TITULO_ACESSAR);

//               Lembrar de completar com esses acessos depois ao salvar!        
//               CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - Acessar"),
//               MOVIMENTO_ACESSAR("MOVIMENTO_ACESSAR", "Movimento - Acessar"),            
            setListPickAcessos(new DualListModel<>(acessosDisponiveis, acessosSelecionados));
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

    private void chargedAcessosToSave() throws Exception {
        //Tratamento para Acesso:
        if (contextoBean.getEntidadeLogada() != null
                && contextoBean.getEntidadeLogada().getTipo() != null
                && contextoBean.getEntidadeLogada().getTipo().equals("A")) {
            // Se é um usuário administrador que está logado ele pode dar manutenção nos acessos:
            getObjetoSelecionado().setAcessos(new HashSet<>());

            if (getObjetoSelecionado().getTipo() != null) {
                if (getObjetoSelecionado().getTipo().equals("A")) {
                    getObjetoSelecionado().getAcessos().add("ADMIN");
                    getObjetoSelecionado().getAcessos().add("USER");
                } else if (getObjetoSelecionado().getTipo().equals("U")) {
                    getObjetoSelecionado().getAcessos().add("USER");
                }
            }

            if (getListPickAcessos() != null
                    && getListPickAcessos().getTarget() != null
                    && !getListPickAcessos().getTarget().isEmpty()) {
                getListPickAcessos().getTarget().stream().forEach(permissao -> {
                    getObjetoSelecionado().getAcessos().add(permissao.getValor());

                    //Cadastro Acessar / Cidade Acessar / Usuario Acessar:
                    if (Arrays.asList(Permissao.CIDADE_SALVAR,
                            Permissao.CIDADE_EDITAR,
                            Permissao.CIDADE_EXCLUIR,
                            Permissao.USUARIO_SALVAR,
                            Permissao.USUARIO_EDITAR,
                            Permissao.USUARIO_EXCLUIR).contains(permissao)) {
                        if (!getObjetoSelecionado().getAcessos().contains(Permissao.CADASTRO_ACESSAR.getValor())) {
                            getObjetoSelecionado().getAcessos().add(Permissao.CADASTRO_ACESSAR.getValor());
                        }

                        //Cidade Acessar:
                        if (Arrays.asList(Permissao.CIDADE_SALVAR,
                                Permissao.CIDADE_EDITAR,
                                Permissao.CIDADE_EXCLUIR).contains(permissao)) {
                            if (!getObjetoSelecionado().getAcessos().contains(Permissao.CIDADE_ACESSAR.getValor())) {
                                getObjetoSelecionado().getAcessos().add(Permissao.CIDADE_ACESSAR.getValor());
                            }
                        }

                        //Usuario Acessar:
                        if (Arrays.asList(Permissao.USUARIO_SALVAR,
                                Permissao.USUARIO_EDITAR,
                                Permissao.USUARIO_EXCLUIR).contains(permissao)) {
                            if (!getObjetoSelecionado().getAcessos().contains(Permissao.USUARIO_ACESSAR.getValor())) {
                                getObjetoSelecionado().getAcessos().add(Permissao.USUARIO_ACESSAR.getValor());
                            }
                        }
                    }

                    //Título Acessar / Movimento Acessar
                    if (Arrays.asList(Permissao.TITULO_SALVAR,
                            Permissao.TITULO_EDITAR,
                            Permissao.TITULO_EXCLUIR).contains(permissao)) {
                        if (!getObjetoSelecionado().getAcessos().contains(Permissao.MOVIMENTO_ACESSAR.getValor())) {
                            getObjetoSelecionado().getAcessos().add(Permissao.MOVIMENTO_ACESSAR.getValor());
                        }

                        //Titulo Acessar:
                        if (!getObjetoSelecionado().getAcessos().contains(Permissao.TITULO_ACESSAR.getValor())) {
                            getObjetoSelecionado().getAcessos().add(Permissao.TITULO_ACESSAR.getValor());
                        }
                    }
                });
            }
        } else {
            //Adiciona o acesso default para o novo usuário:
            if (getObjetoSelecionado().getAcessos() != null
                    && getObjetoSelecionado().getTipo() != null) {
                if (getObjetoSelecionado().getAcessos() != null) {
                    if (getObjetoSelecionado().getTipo().equals("A")) {
                        if (!getObjetoSelecionado().getAcessos().contains("ADMIN")) {
                            getObjetoSelecionado().getAcessos().add("ADMIN");
                        }

                        //ADMIN também deve ter acesso (USER):
                        if (!getObjetoSelecionado().getAcessos().contains("USER")) {
                            getObjetoSelecionado().getAcessos().add("USER");
                        }
                    } else if (getObjetoSelecionado().getTipo().equals("U")) {
                        if (!getObjetoSelecionado().getAcessos().contains("USER")) {
                            getObjetoSelecionado().getAcessos().add("USER");
                        }
                        //Se virou usuário, vamos apagar o registro ADMIN se tiver:
                        if (getObjetoSelecionado().getAcessos().contains("ADMIN")) {
                            getObjetoSelecionado().getAcessos().remove("ADMIN");
                        }
                    }
                }
            }
        }
    }

    @Override
    public String save() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", false);

        try {
            validaSaveUsuario();
            setEnableButtonsAcao(false);
            //Inclusão de novo usuário, gravar a senha:
            if (acao != null
                    && acao.equals("0")
                    && getObjetoSelecionado() != null
                    && getObjetoSelecionado().getSenhaString() != null) {
                //Gerar um salt aleatório (BCrypt.getsalt()):
                getObjetoSelecionado().setSenha(BCrypt.hashpw(getObjetoSelecionado().getSenhaString(), BCrypt.gensalt()));
            }

            //Tratamento para Acesso:
            chargedAcessosToSave();

            //Retirando mascára do CPF para gravação:
            getObjetoSelecionado().setCpf(RegexUtil.manterApenasDigitosCpf(getObjetoSelecionado().getCpf()));
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
            validaSaveUsuario();
            setEnableButtonsAcao(false);
            //Inclusão de novo usuário, gravar a senha:
            if (acao != null
                    && acao.equals("0")
                    && getObjetoSelecionado() != null
                    && getObjetoSelecionado().getSenhaString() != null) {
                //Gerar um salt aleatório (BCrypt.getsalt()):
                getObjetoSelecionado().setSenha(BCrypt.hashpw(getObjetoSelecionado().getSenhaString(), BCrypt.gensalt()));
            }

            //Tratamento para Acesso:
            chargedAcessosToSave();

            //Retirando mascára do CPF para gravação:
            getObjetoSelecionado().setCpf(RegexUtil.manterApenasDigitosCpf(getObjetoSelecionado().getCpf()));
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

    private void validaSaveUsuario() throws ValidationException {
        try {
            if (getObjetoSelecionado() != null
                    && getObjetoSelecionado().getLogin() != null) {
                //Senha e confirmação devem ser iguais:
                if (getObjetoSelecionado().getSenhaString() != null
                        && getObjetoSelecionado().getConfirmaSenha() != null) {
                    if (!getObjetoSelecionado().getSenhaString().equals(getObjetoSelecionado().getConfirmaSenha())) {
                        throw new ValidationException("A senha e a confirmação não conferem!");
                    }
                }

                //Testar se o login já existe no sistema:
                if (getAcao() != null) {
                    //Caso for inclusão, não pode existir ainda login igual:
                    if (acao.equals("0")) {
                        if (entidadeController.getExistsEntidadeLogin(getObjetoSelecionado().getLogin())) {
                            throw new ValidationException("Login já existente!<br>Defina um login diferente e tente novamente.");
                        }
                    }

                    //Valida CPF:
                    //Caso for inclusão, não pode existir ainda login igual:
                    if (acao.equals("0")) {
                        if (getObjetoSelecionado().getCpf() != null
                                && !getObjetoSelecionado().getCpf().isEmpty()) {
                            if (entidadeController.getExistsCpf(RegexUtil.manterApenasDigitosCpf(getObjetoSelecionado().getCpf()))) {
                                throw new ValidationException("CPF já cadastrado!");
                            }
                        }
                    } else if (acao.equals("1")
                            && getObjetoSelecionado().getCpf() != null
                            && !getObjetoSelecionado().getCpf().isEmpty()
                            && getObjetoSelecionado().getCodigo() != null) {
                        Entidade entidadeCharged = entidadeController.findByPorId(Entidade.class, getObjetoSelecionado().getCodigo());

                        //Alteração não pode ter CPF igual diferente do usuário atual:
                        if (entidadeCharged != null
                                && entidadeCharged.getCodigo() != null
                                && entidadeCharged.getCpf() != null
                                && entidadeController.getExistsCpf(RegexUtil.manterApenasDigitosCpf(getObjetoSelecionado().getCpf()))
                                && !RegexUtil.manterApenasDigitosCpf(entidadeCharged.getCpf()).equals(RegexUtil.manterApenasDigitosCpf(getObjetoSelecionado().getCpf()))) {
                            throw new ValidationException("CPF já cadastrado!");
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new ValidationException(ex.getMessage());
        }
    }

    @Override
    public void validEditar() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", false);

        if (contextoBean.possuiAcesso(Permissao.ADMIN.getValor(),
                Permissao.USUARIO_EDITAR.getValor())) {
            if (getObjetoSelecionado() == null
                    || getObjetoSelecionado().getCodigo() == null) {
                Mensagem.msgSeverityWarn("Selecione um registro para altera-lo.", "Atenção");
            } else if (getObjetoSelecionado().getLogin() != null
                    && !getObjetoSelecionado().getLogin().isEmpty()
                    && getObjetoSelecionado().getLogin().equals("admin")) {
                Mensagem.msgSeverityWarn("Usuário 'admin' não pode ser editado.", "Atenção");
            } else {
                PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", true);
            }
        } else {
            Mensagem.msgSeverityWarn("Você não tem permissão para editar Usuários!", "Atenção");
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
        } else if (getObjetoSelecionado().getLogin() != null
                && !getObjetoSelecionado().getLogin().isEmpty()
                && getObjetoSelecionado().getLogin().equals("admin")) {
            Mensagem.msgSeverityWarn("Usuário 'admin' não pode ser excluído.", "Atenção");
        } else {
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
        }
    }

    @Override
    public void excluir() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", false);
        try {
            if (getObjetoSelecionado() != null
                    && getObjetoSelecionado().getCodigo() != null
                    && getObjetoSelecionado().getLogin() != null) {
                if (!getObjetoSelecionado().getLogin().isEmpty()
                        && getObjetoSelecionado().getLogin().equals("admin")) {
                    Mensagem.msgSeverityWarn("Usuário 'admin' não pode ser excluído.", "Atenção");
                } else {
                    //Find no objeto atual, para evitar que está diferente no banco de dados por alguma alteração que aqui ainda está em cache!
                    Entidade entidadeDel = entidadeController.findByPorId(Entidade.class, getObjetoSelecionado().getCodigo());

                    if (entidadeDel != null) {
                        entidadeController.delete(entidadeDel);
                        setarVariaveisNulas();
                        Mensagem.msgExcluidoComSucesso();

                        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
                    }
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

    @Override
    public StreamedContent getArquivoReport() throws Exception {
        //Parâmetros para o Relatório:
        setNomeRelatorioJasper("usuario_report");
        setNomeRelatorioSaida("usuario_report");
        setListDataBeanCollectionReport(entidadeController.findListByQueryDinamica(" FROM Entidade ORDER BY nome"));

        return super.getArquivoReport();
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

    public DualListModel<Permissao> getListPickAcessos() {
        return listPickAcessos;
    }

    public void setListPickAcessos(DualListModel<Permissao> listPickAcessos) {
        this.listPickAcessos = listPickAcessos;
    }

}
