/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.CidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Cidade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
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

    private String url = "/cadastro/cad_cidade.jsf?faces-redirect=true";
    
    private String urlFind = "/cadastro/consulta/find_cidade.jsf?faces-redirect=true";

    private Cidade objetoSelecionado;

    @Autowired
    private CidadeController cidadeController;

    @Override
    protected Class<Cidade> getClassImplement() {
        return Cidade.class;
    }

    @Override
    public void initComponentes() {
        super.initComponentes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setarVariaveisNulas() throws Exception {
        setObjetoSelecionado(new Cidade());
    }

    @Override
    public String redirecionarFindEntidade() throws Exception {
        setarVariaveisNulas();
        return urlFind;
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
        try {
            setObjetoSelecionado(cidadeController.merge(getObjetoSelecionado()));
            Mensagem.msgSalvoComSucesso();
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError("Erro ao salvar!\n" + ex.getMessage(), "Erro");
        }

        return "";
    }

    @Override
    public String saveNew() throws Exception {
        try {
            cidadeController.merge(getObjetoSelecionado());
            Mensagem.msgSalvoComSucesso();
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError("Erro ao salvar!\n" + ex.getMessage(), "Erro");
        }

        return "";
    }

    @Override
    public String editar() throws Exception {
        //Redireciona para mesma página:
        return "";
    }

    @Override
    public void validExclusao() throws Exception {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", false);

        if (getObjetoSelecionado() == null
                || getObjetoSelecionado().getCodigo() == null) {
            Mensagem.msgSeverityWarn("Edite um registro para poder excluí-lo.", "Erro");
        } else {
            PrimeRequestContext.getCurrentInstance().getCallbackParams().put("validExclusao", true);
        }
    }

    @Override
    public void excluir() throws Exception {
        try {
            if (getObjetoSelecionado() != null
                    && getObjetoSelecionado().getCodigo() != null) {
                cidadeController.delete(getObjetoSelecionado());
                setarVariaveisNulas();
                Mensagem.msgExcluidoComSucesso();
            } else {
                Mensagem.msgSeverityWarn("Edite um registro para poder excluí-lo.", "Erro");
            }
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError("Erro ao Excluir!\n" + ex.getMessage(), "Erro");
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

    public List<Cidade> getListAllCidade() {
        List<Cidade> listCidade = null;

        try {
            listCidade = cidadeController.findListByQueryDinamica(" FROM Cidade ORDER BY nome");
        } catch (Exception ex) {
            Logger.getLogger(CidadeBeanView.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.msgSeverityError(ex.getMessage(), "Erro");
        }

        return listCidade;
    }
}
