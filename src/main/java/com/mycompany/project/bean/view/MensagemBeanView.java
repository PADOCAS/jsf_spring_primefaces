/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.EntidadeController;
import com.mycompany.project.geral.controller.MensagemController;
import com.mycompany.project.model.Entidade;
import com.mycompany.project.model.Mensagem;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private MensagemController mensagemController;

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
        mensagem.setDataMensagem(new Date());
        mensagem.setLida(false);

        if (contextoBean != null) {
            mensagem.setUsuarioOrigem(contextoBean.getEntidadeLogada());
        }

        setObjetoSelecionado(mensagem);
    }

    @Override
    public String novo() {
        try {
            setarVariaveisNulas();
        } catch (Exception ex) {
            Logger.getLogger(MensagemBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    @Override
    public void saveNotReturn() throws Exception {
        try {
            mensagemController.merge(getObjetoSelecionado());
            novo();
            msgEnvioMensagemFeitoComSucesso();
        } catch (Exception ex) {
            Logger.getLogger(MensagemBeanView.class.getName()).log(Level.SEVERE, null, ex);
            com.mycompany.project.message.util.Mensagem.msgSeverityError("Erro ao enviar Mensagem!<br><br>" + ex.getMessage(), "Erro");
        }
    }

    public List<Entidade> chargedListEntidade(String query) {
        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getCodigo() != null) {
                return entidadeController.getListEntidadeEnvioMensagem(query, contextoBean.getEntidadeLogada().getCodigo());
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Método para acessar direto via JAVASCRIPT, responsável para trazer os
     * usuários de destino disponíveis (diferente do usuário logado) No nosso
     * caso fizemos um método direto, não fizemos via javascript
     *
     * @param httpServletResponse
     * @param codEntidade
     * @throws java.lang.Exception
     */
    @RequestMapping("**/buscarUsuarioDestinoMsg")
    public void buscarUsuarioDestinoMsg(HttpServletResponse httpServletResponse, @RequestParam(value = "codEntidade") Long codEntidade) throws Exception {
        Entidade entidade = entidadeController.findByPorId(Entidade.class, codEntidade);
        if (entidade != null) {
            getObjetoSelecionado().setUsuarioDestino(entidade);
            //Escreve a resposta em JSON para o JavaScript obter o objeto
            httpServletResponse.getWriter().write(entidade.getJson().toString());
        }
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
