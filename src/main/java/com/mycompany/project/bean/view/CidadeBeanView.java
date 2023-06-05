/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.view;

import com.mycompany.project.bean.geral.BeanManagedViewAbstract;
import com.mycompany.project.geral.controller.CidadeController;
import com.mycompany.project.message.util.Mensagem;
import com.mycompany.project.model.Cidade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
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

    private Cidade objetoSelecionado;

    @Autowired
    private CidadeController cidadeController;

    @Override
    public void initComponentes() {
        super.initComponentes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        setObjetoSelecionado(new Cidade());
    }

    @Override
    public String novo() {
        setObjetoSelecionado(new Cidade());
        return "";
    }

    @Override
    public String save() throws Exception {
        setObjetoSelecionado(cidadeController.merge(getObjetoSelecionado()));
        Mensagem.msgSalvoComSucesso();
        //Se quiser podemos redirecionar para alguma tela nesse retorno, vamos manter na mesma tela ao salvar!
        return "";
    }

    @Override
    public String editar() throws Exception {
        //Redireciona para mesma página:
        return "";
    }

    @Override
    public void excluir() throws Exception {
        cidadeController.delete(getObjetoSelecionado());
        setObjetoSelecionado(new Cidade());        
        Mensagem.msgExcluidoComSucesso();
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