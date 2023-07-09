/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import com.mycompany.project.enums.CondicaoPesquisa;
import com.mycompany.project.report.util.BeanReportView;
import com.mycompany.project.util.all.RegexUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author lucia
 */
//@Component para o Spring trabalhar com ela:
@Component
public abstract class BeanManagedViewAbstract extends BeanReportView {

    private static final long serialVersionUID = 1L;

    protected abstract Class<?> getClassImplement();

    protected abstract IInterfaceCrud<?> getController();

    //Objeto Campo Consulta selecionado na tela de pesquisa!
    public ObjetoCampoConsulta objetoCampoConsulta;

    //Objeto CondicaoPesquisa selecionado na tela de pesquisa!
    public CondicaoPesquisa objetoCondicaoConsulta;

    //Valor da Pesquisa que informou em tela!
    public String valorPesquisa;

    public List<SelectItem> listCampoConsulta = null;

    private String objetoCampoConsultaBancoDefault = null;

    public List<SelectItem> listCondicaoPesquisa;

    public abstract String condicaoAndParaPesquisa() throws Exception;

    @Override
    public void initComponentes() {
        super.initComponentes();
        chargedListCamposPesquisa();
    }

    private void chargedListCamposPesquisa() {
        if (listCampoConsulta == null) {
            listCampoConsulta = new ArrayList<>();
            List<ObjetoCampoConsulta> listObjCamPesTemp = new ArrayList<>();

            if (getClassImplement() != null
                    && getClassImplement().getDeclaredFields() != null) {
                for (Field field : getClassImplement().getDeclaredFields()) {
                    //Verifica se o campo tem a anotação de IdentificaCampoPesquisa:
                    if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
                        String campoBancoDeDados = field.getAnnotation(IdentificaCampoPesquisa.class).campoBancoDeDados();
                        String descricaoCampoEmTela = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampoEmTela();
                        Integer ordemCampoEmTela = field.getAnnotation(IdentificaCampoPesquisa.class).ordemCampoEmTela();

                        ObjetoCampoConsulta objCamCon = new ObjetoCampoConsulta();
                        objCamCon.setCampoNoBanco(campoBancoDeDados);
                        objCamCon.setDescricaoEmTela(descricaoCampoEmTela);
                        //Default de ordenação é 1000 -> ou seja, nunca será nulo
                        objCamCon.setOrdemEmTela(ordemCampoEmTela);
                        objCamCon.setClasse(field.getType().getCanonicalName());

                        listObjCamPesTemp.add(objCamCon);
                    }
                }
            }

            //Ordena a Lista respeitando o que foi passado no VO:
            ordenaLista(listObjCamPesTemp);

            if (!listObjCamPesTemp.isEmpty()) {
                for (ObjetoCampoConsulta objCamPes : listObjCamPesTemp) {
                    listCampoConsulta.add(new SelectItem(objCamPes));
                }

                //Seta Default:
                objetoCampoConsultaBancoDefault = listObjCamPesTemp.get(0).getCampoNoBanco();
            }
        }
    }

    public ObjetoCampoConsulta getObjetoCampoConsulta() {
        return objetoCampoConsulta;
    }

    public void setObjetoCampoConsulta(ObjetoCampoConsulta objetoCampoConsulta) {
        this.objetoCampoConsulta = objetoCampoConsulta;
    }

    public CondicaoPesquisa getObjetoCondicaoConsulta() {
        return objetoCondicaoConsulta;
    }

    public void setObjetoCondicaoConsulta(CondicaoPesquisa objetoCondicaoConsulta) {
        this.objetoCondicaoConsulta = objetoCondicaoConsulta;
    }

    public String getValorPesquisa() {
        return valorPesquisa;
    }

    public void setValorPesquisa(String valorPesquisa) {
        this.valorPesquisa = valorPesquisa;
    }

    /**
     * Método que vai retornar uma lista de SelectItem com as condições de
     * Pesquisa definida pelo enum 'CondicaoPesquisa'
     *
     * @return
     */
    public List<SelectItem> getListCondicaoPesquisa() {
        listCondicaoPesquisa = new ArrayList<>();

        for (CondicaoPesquisa conPes : CondicaoPesquisa.values()) {
            listCondicaoPesquisa.add(new SelectItem(conPes, conPes.getCondicao()));
        }

        return listCondicaoPesquisa;
    }

    /**
     * Método que vai retornar uma List de SelectItem ordenada para telas
     * padrões de consulta (pesquisa.xhtml), de acordo com as opções de campos
     * informadas no VO com a anotação 'IdentificaCampoPesquisa'. Utilizado
     * reflect para apurar as informações dos campos do VO
     *
     * @return List<SelectItem>
     */
    public List<SelectItem> getListCampoConsulta() {
        chargedListCamposPesquisa();
        return listCampoConsulta;
    }

    private void ordenaLista(List<ObjetoCampoConsulta> listObjCamPesTemp) {
        if (listObjCamPesTemp != null
                && !listObjCamPesTemp.isEmpty()) {
            Collections.sort(listObjCamPesTemp, (ObjetoCampoConsulta o1, ObjetoCampoConsulta o2) -> {
                return o1.getOrdemEmTela().compareTo(o2.getOrdemEmTela());
            });
        }
    }

    private String getQueryConsulta() throws Exception {
        valorPesquisa = RegexUtil.getRetiraAcentos(valorPesquisa);
        StringBuilder sql = new StringBuilder();
        sql.append(getClassImplement().getSimpleName()).append(" entity ");

        //Caso tiver carregando a página pela primeira vez, trata o campo padrão de consulta:
        String noCampoBancoCheck;
        if (getObjetoCampoConsulta() != null
                && getObjetoCampoConsulta().getCampoNoBanco() != null) {
            noCampoBancoCheck = getObjetoCampoConsulta().getCampoNoBanco();
        } else {
            noCampoBancoCheck = objetoCampoConsultaBancoDefault;
        }

        if (noCampoBancoCheck != null) {
            sql.append(" WHERE ");
            sql.append(" retira_acentos(upper(cast(entity.").append(noCampoBancoCheck).append(" as text))) ");

            switch (getObjetoCondicaoConsulta() == null ? CondicaoPesquisa.CONTEM : getObjetoCondicaoConsulta()) {
                case CONTEM:
                    sql.append(" LIKE retira_acentos(upper('%").append(valorPesquisa).append("%')) ");
                    break;
                case IGUAL:
                    sql.append(" = retira_acentos(upper('").append(valorPesquisa).append("')) ");
                    break;
                case INICIA:
                    sql.append(" LIKE retira_acentos(upper('").append(valorPesquisa).append("%')) ");
                    break;
                case TERMINA:
                    sql.append(" LIKE retira_acentos(upper('%").append(valorPesquisa).append("')) ");
                    break;
                default:
                    break;
            }

            sql.append("  ");
            if (condicaoAndParaPesquisa() != null
                    && !condicaoAndParaPesquisa().isEmpty()) {
                sql.append(condicaoAndParaPesquisa());
            }
        }

        return sql.toString();
    }

    public String getSqlLazyQueryTotRegistro() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT entity ");
        sql.append("      FROM ").append(getQueryConsulta());

        //Caso tiver carregando a página pela primeira vez, trata o campo padrão de consulta:
        String noCampoBancoCheck;
        if (getObjetoCampoConsulta() != null
                && getObjetoCampoConsulta().getCampoNoBanco() != null) {
            noCampoBancoCheck = getObjetoCampoConsulta().getCampoNoBanco();
        } else {
            noCampoBancoCheck = objetoCampoConsultaBancoDefault;
        }

        if (noCampoBancoCheck != null) {
            sql.append("  ORDER BY entity.").append(noCampoBancoCheck);
        }

        return sql.toString();
    }

    public int getTotalRegistroConsulta() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(1) FROM ").append(getQueryConsulta());
        Query query = getController().obterQuery(sql.toString());
        Number result = (Number) query.uniqueResult();

        return result.intValue();
    }

}
