/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.model.Entidade;
import com.mycompany.project.model.Mensagem;
import java.util.List;
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
@ManagedBean(name = "mensagemBeanView")
public class MensagemBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    private Mensagem objetoSelecionado;

    @Autowired
    private ContextoBean contextoBean;

    @Autowired
    private EntidadeController entidadeController;

    @Override
    public void initComponentes() {
        try {
            super.initComponentes();
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(MensagemBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setarVariaveisNulas() throws Exception {
        Mensagem mensagem = new Mensagem();

        if (contextoBean != null) {
            mensagem.setUsuarioOrigem(contextoBean.getEntidadeLogada());
        }

        setObjetoSelecionado(mensagem);
    }

    public List<Entidade> chargedListEntidade(String query) {
        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getEnt_codigo() != null) {
                return entidadeController.getListEntidadeEnvioMensagem(query, contextoBean.getEntidadeLogada().getEnt_codigo());
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void msgEnvioMensagemFeitoComSucesso() {
        com.mycompany.project.message.util.Mensagem.msgSeverityInfo("Mensagem enviada com sucesso!", "OK");
    }

    @Override
    protected Class<?> getClassImplement() {
        return Mensagem.class;
    }

    @Override
    protected IInterfaceCrud<?> getController() {
        return null;
    }

    @Override
    public String condicaoAndParaPesquisa() throws Exception {
        return null;
    }

    public Mensagem getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Mensagem objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

}
