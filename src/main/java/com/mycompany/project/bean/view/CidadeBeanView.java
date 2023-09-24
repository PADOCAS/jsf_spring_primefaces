/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.acessos.Permissao;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.carregamento.lazy.CarregamentoLazyListForObject;
import com.mycompany.project.geral.controller.CidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Cidade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "view")
@ManagedBean(name = "cidadeBeanView")
public class CidadeBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    private final String url = "/cadastro/cad_cidade.jsf?faces-redirect=true";

    private final String urlFind = "/cadastro/consulta/find_cidade.jsf?faces-redirect=true";

    private Cidade objetoSelecionado;

    //acao: 0(default-insert), 1(update)
    private String acao;

    private Cidade objetoAlteracao;

    private Boolean enableButtonsAcao = true;

    private CarregamentoLazyListForObject<Cidade> list = new CarregamentoLazyListForObject<>();

    @Autowired
    private CidadeController cidadeController;

    @Autowired
    private ContextoBean contextoBean;

    @Override
    protected Class<Cidade> getClassImplement() {
        return Cidade.class;
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
                setObjetoAlteracao((Cidade) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("objetoAlteracao"));
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
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setarVariaveisNulas() throws Exception {
        setObjetoSelecionado(new Cidade());
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
    public String redirecionarFindEntidade() throws Exception {
        setarVariaveisNulas();
        return urlFind;
    }

    @Override
    public String getUrfFindEntidade() {
        return "/cadastro/consulta/find_cidade";
    }

    @Override
    public String getUrfEntidade() {
        return "/cadastro/cad_cidade";
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return cidadeController;
    }

    @Override
    public String novo() {
        try {
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url;
    }

    @Override
    public String getMessageExclusao() throws Exception {
        //Confirma a Exclusão da Cidade: (#{cidadeBeanView.objetoSelecionado.codigo}) #{cidadeBeanView.objetoSelecionado.nome} ?
        if (getObjetoSelecionado() != null
                && getObjetoSelecionado().getCodigo() != null
                && getObjetoSelecionado().getNome() != null) {
            StringBuilder str = new StringBuilder();
            str.append("Confirma a exclusão da Cidade: (").append(getObjetoSelecionado().getCodigo()).append(") ");
            str.append(getObjetoSelecionado().getNome()).append(" ?");

            return str.toString();
        }

        return "";
    }

    @Override
    public String save() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", false);

        try {
            setEnableButtonsAcao(false);
            setObjetoSelecionado(cidadeController.merge(getObjetoSelecionado()));
            Mensagem.msgSalvoComSucesso();
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", true);
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>Registro já foi alterado por outro usuário.<br>Retorne a consulta de cidades e selecione novamente para alteração.", "Atenção");
            setEnableButtonsAcao(true);
            //Caso der erro, mantém na mesma página:
            return "";
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
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
            setEnableButtonsAcao(false);
            cidadeController.merge(getObjetoSelecionado());
            Mensagem.msgSalvoComSucesso();
            setarVariaveisNulas();
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("saveOk", true);
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao salvar!<br><br>Registro já foi alterado por outro usuário.<br>Retorne a consulta de cidades e selecione novamente para alteração.", "Atenção");
            setEnableButtonsAcao(true);
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            setEnableButtonsAcao(true);
            throw new Exception(ex);
        }

        return "";
    }

    @Override
    public void validEditar() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", false);

        //Verifica se o usuário tem permissão para alteração:
        if (contextoBean.possuiAcesso(Permissao.ADMIN.getValor(),
                Permissao.CIDADE_EDITAR.getValor())) {
            if (getObjetoSelecionado() == null
                    || getObjetoSelecionado().getCodigo() == null) {
                Mensagem.msgSeverityWarn("Selecione um registro para altera-lo.", "Atenção");
            } else {
                PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validEditar", true);
            }
        } else {
            Mensagem.msgSeverityWarn("Você não tem permissão para editar Cidades!", "Atenção");
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
                Cidade cidadeDel = cidadeController.findByPorId(Cidade.class, getObjetoSelecionado().getCodigo());

                if (cidadeDel != null) {
                    cidadeController.delete(cidadeDel);
                    setarVariaveisNulas();
                    Mensagem.msgExcluidoComSucesso();

                    PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
                }
            } else {
                Mensagem.msgSeverityWarn("Edite um registro para poder excluí-lo.", "Erro");
            }
        } catch (javax.persistence.OptimisticLockException ex) {
            Mensagem.msgSeverityWarn("Erro ao Excluir!<br><br>Registro foi alterado por outro usuário,<br>retorne a consulta de cidade e tente excluí-lo novamente.", "Atenção");
        } catch (Exception ex) {
            if (ex.getMessage().contains("No row with the given identifier exists")) {
                Mensagem.msgSeverityWarn("Erro ao Excluir!<br><br>Esse registro já foi excluído por outro usuário.", "Atenção");
            } else {
                Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
        }
    }

    @Override
    public void consultarEntidade() throws Exception {
        try {
            objetoSelecionado = new Cidade();
            list.clean();
            list.setTotRegConsulta(getTotalRegistroConsulta(), getSqlLazyQueryTotRegistro());
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError(ex.getMessage(), "Erro");
        }
    }

    @Override
    public StreamedContent getArquivoReport() throws Exception {
        //Parâmetros para o Relatório:
        setNomeRelatorioJasper("cidade_report");
        setNomeRelatorioSaida("cidade_report");
        setListDataBeanCollectionReport(cidadeController.findListByQueryDinamica(" FROM Cidade ORDER BY nome"));

        return super.getArquivoReport();
    }

    public List<SelectItem> getListSelectItemEstado() {
        return cidadeController.getListSelectItemEstado();
    }

    public Cidade getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Cidade objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Cidade getObjetoAlteracao() {
        return objetoAlteracao;
    }

    public void setObjetoAlteracao(Cidade objetoAlteracao) {
        this.objetoAlteracao = objetoAlteracao;
    }

    @Override
    public String condicaoAndParaPesquisa() throws Exception {
        return null;
    }

    public Boolean getEnableButtonsAcao() {
        return enableButtonsAcao;
    }

    public void setEnableButtonsAcao(Boolean enableButtonsAcao) {
        this.enableButtonsAcao = enableButtonsAcao;
    }

    public CarregamentoLazyListForObject<Cidade> getList() {
        return list;
    }

    public void setList(CarregamentoLazyListForObject<Cidade> list) {
        this.list = list;
    }

}
