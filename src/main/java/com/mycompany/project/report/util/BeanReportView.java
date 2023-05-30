/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.report.util;

import com.mycompany.project.cadastro.util.BeanViewAbstract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 * Classe a receber os dados e passar para o ReportUtil gerar o relatório
 *
 * @author lucia
 */
@Component
public abstract class BeanReportView extends BeanViewAbstract {

    private static final long serialVersionUID = 1L;

    //Não vou utilizar, caso precisar implemento:
//    protected StreamedContent arquivoReport;

    protected int tipoRelatorio;

    protected List<?> listDataBeanCollectionReport;

    protected HashMap<String, Object> parameters;

    protected String nomeRelatorioJasper = "default";

    protected String nomeRelatorioSaida = "default";

    @Resource
    private ReportUtil reportUtil;

    public BeanReportView() {
        parameters = new HashMap<>();
        listDataBeanCollectionReport = new ArrayList<>();
    }

    public ReportUtil getReportUtil() {
        return reportUtil;
    }

    public void setReportUtil(ReportUtil reportUtil) {
        this.reportUtil = reportUtil;
    }

    public StreamedContent getArquivoReport() throws Exception {
        return getReportUtil().geraRelatorio(
                getListDataBeanCollectionReport(),
                getParameters(),
                getNomeRelatorioJasper(),
                getNomeRelatorioSaida(),
                getTipoRelatorio());
    }

    public int getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(int tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public List<?> getListDataBeanCollectionReport() {
        return listDataBeanCollectionReport;
    }

    public void setListDataBeanCollectionReport(List<?> listDataBeanCollectionReport) {
        this.listDataBeanCollectionReport = listDataBeanCollectionReport;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getNomeRelatorioJasper() {
        return nomeRelatorioJasper;
    }

    public void setNomeRelatorioJasper(String nomeRelatorioJasper) {
        //Nome default vai servir para soltar um relatório padrão indicando que o usuário errou o nome do relatório
        if (nomeRelatorioJasper == null
                || nomeRelatorioJasper.isEmpty()) {
            nomeRelatorioJasper = "default";
        }

        this.nomeRelatorioJasper = nomeRelatorioJasper;
    }

    public String getNomeRelatorioSaida() {
        return nomeRelatorioSaida;
    }

    public void setNomeRelatorioSaida(String nomeRelatorioSaida) {
        //Nome default vai servir para soltar um relatório padrão indicando que o usuário errou o nome do relatório
        if (nomeRelatorioSaida == null
                || nomeRelatorioSaida.isEmpty()) {
            nomeRelatorioSaida = "default";
        }

        this.nomeRelatorioSaida = nomeRelatorioSaida;
    }

}
