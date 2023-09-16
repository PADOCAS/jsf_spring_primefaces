/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.MensagemController;
import com.mycompany.project.model.Mensagem;
import com.mycompany.project.model.MensagemDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.PrimeRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author lucia
 */
@Controller
@Scope(value = "session")
@ManagedBean(name = "mensagemRespostaBeanView")
public class MensagemRespostaBeanView extends BeanManagedViewAbstract {

    private static final long serialVersionUID = 1L;

    private Mensagem objetoSelecionado;

    @Autowired
    private ContextoBean contextoBean;

    @Autowired
    private MensagemController mensagemController;

    @Override
    public void initComponentes() {
        try {
            super.initComponentes();
            setarVariaveisNulas();
            chargedMensagemPendente();
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
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
            chargedMensagemPendente();
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    private void chargedMensagemPendente() {
        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getCodigo() != null) {
                Mensagem mensOrigem = mensagemController.getMensagemPendenteForUser(contextoBean.getEntidadeLogada().getCodigo());

                if (mensOrigem != null) {
                    getObjetoSelecionado().setDataMensagemASerRespondida(mensOrigem.getDataMensagem());
                    getObjetoSelecionado().setUsuarioDestino(mensOrigem.getUsuarioOrigem());
                    getObjetoSelecionado().setExigirRespostaASerRepondida(mensOrigem.getExigirResposta());
                    getObjetoSelecionado().setAssunto(mensOrigem.getAssunto());
                    getObjetoSelecionado().setAssuntoASerRespondido(mensOrigem.getAssunto());
                    getObjetoSelecionado().setMensagemASerRespondida(mensOrigem.getDescricao());
                    
                    //teste de mensagens histórico:
                    List<MensagemDTO> listMensagemDto = new ArrayList<>();
                    MensagemDTO mens1 = new MensagemDTO();
                    mens1.setAssunto("Gol do Corinthians");
                    mens1.setCodigo(2L);
                    mens1.setDataMensagem(new Date());
                    mens1.setMensagem("Golaço do Gil!");
                    mens1.setResposta("Foi bom mesmo! Belo gol!");
                    listMensagemDto.add(mens1);
                    
                    MensagemDTO mens2 = new MensagemDTO();
                    mens2.setAssunto("Gol do Palmeiras");
                    mens2.setCodigo(3L);
                    mens2.setDataMensagem(new Date());
                    mens2.setMensagem("Gol robado!");
                    mens2.setResposta("VAR não viu a mão, pego 3 vez na mão antes de entra.");
                    listMensagemDto.add(mens2);
                    
                    getObjetoSelecionado().setListMensagemDto(listMensagemDto);
                } else {
                    com.mycompany.project.message.util.Mensagem.msgSeverityWarn("Você não tem nenhuma mensagem pendente!", "Atenção");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
            com.mycompany.project.message.util.Mensagem.msgSeverityError("Erro ao verificar mensagens!<br><br>" + ex.getMessage(), "Erro");
        }
    }

    @Override
    public void saveNotReturn() throws Exception {
        try {
            mensagemController.merge(getObjetoSelecionado());
            novo();
            msgEnvioMensagemFeitoComSucesso();
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    public void verifyMensagensPendentes() {
        PrimeRequestContext.getCurrentInstance().getCallbackParams().put("existsMensagemPendente", false);

        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getCodigo() != null) {
                Long totalNotificacoes = mensagemController.getTotalNotificacoesUser(contextoBean.getEntidadeLogada().getCodigo());

                if (totalNotificacoes != null
                        && totalNotificacoes > 0) {
                    PrimeRequestContext.getCurrentInstance().getCallbackParams().put("existsMensagemPendente", true);
                } else {
                    com.mycompany.project.message.util.Mensagem.msgSeverityWarn("Você não tem nenhuma mensagem pendente!", "Atenção");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
            com.mycompany.project.message.util.Mensagem.msgSeverityError("Erro ao verificar mensagens!<br><br>" + ex.getMessage(), "Erro");
        }
    }

    /**
     * Retorna o tipo de layaout de mensagens em tela (icone) para o usuário de
     * acordo com as mensagens pendentes de serem lidas
     *
     * @return
     */
    public String getLayoutNotificacoesUser() {
        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getCodigo() != null) {
                Long totalNotificacoes = mensagemController.getTotalNotificacoesUser(contextoBean.getEntidadeLogada().getCodigo());

                if (totalNotificacoes != null
                        && totalNotificacoes > 0L) {
                    return "danger";
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "info";
    }

    //Métodos para tratamento mensagens recebidas (notificações aos usuários):
    /**
     * Retorna o número de mensagens pendentes de serem lidas para o usuário
     * logado
     *
     * @return
     */
    public String getTotalNotificacoesUser() {
        try {
            if (contextoBean != null
                    && contextoBean.getEntidadeLogada() != null
                    && contextoBean.getEntidadeLogada().getCodigo() != null) {
                Long totalNotificacoes = mensagemController.getTotalNotificacoesUser(contextoBean.getEntidadeLogada().getCodigo());

                if (totalNotificacoes != null) {
                    return totalNotificacoes.toString();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MensagemRespostaBeanView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "0";
    }

    public void msgEnvioMensagemFeitoComSucesso() {
        com.mycompany.project.message.util.Mensagem.msgSeverityInfo("Mensagem Confirmada/Respondida com sucesso!", "OK");
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
