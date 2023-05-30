/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 *
 * @author lucia
 */
@Component
public class ReportUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String UNDERLINE = "_";

    private static final String PONTO = ".";

    private static final String FOLDER_RELATORIO = "/relatorios";

    private static final String SUBREPORT_DIR = "SUBREPORT_DIR";

    private static final String EXTENSION_ODS = "ods";

    private static final String EXTENSION_XLS = "xls";

    private static final String EXTENSION_HTML = "html";

    private static final String EXTENSION_PDF = "pdf";

    private String SEPARATOR = File.separator;

    private static final int RELATORIO_PDF = 1;

    private static final int RELATORIO_EXCEL = 2;

    private static final int RELATORIO_HTML = 3;

    private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;

    private StreamedContent arquivoRetorno = null;

    private String arquivoCaminhoRelatorio = null;

    private String arquivoCaminhoSubRelatorioDir = null;

    private JRExporter tipoArquivoExportado = null;

    private String extensaoArquivoExportado = "";

    private File arquivoGerado = null;

    public StreamedContent geraRelatorio(List<?> listDataBean, HashMap<String, Object> paramRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, int tipoRelatorio) throws Exception {
        if (nomeRelatorioJasper != null
                && nomeRelatorioSaida != null
                && paramRelatorio != null) {
            //Cria a lista de CollectionDataSource de beans que carregam os dados para relatório 
            JRBeanCollectionDataSource jrBeanColDs = new JRBeanCollectionDataSource(listDataBean);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();

            //Fornece o caminho físico até a pasta que contem os relatorio compilados (.jasper)
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIO);

            //Exemplo: C:/applicação/relatorios/relbairro.jasper
            File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper");

            if (caminhoRelatorio == null
                    || caminhoRelatorio.isEmpty()
                    || !file.exists()) {
                caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIO).getPath();
                SEPARATOR = "";
            }

            //Caminho para Imagem:
            paramRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);

            //Caminho completo para o relatório:
            String caminhoArquivoJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper";

            //Faz o carregamento do relatório:
            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);
            //SubRelatorio - seta o caminho dos subRelatorios como parametro:
            arquivoCaminhoSubRelatorioDir = caminhoRelatorio + SEPARATOR;
            paramRelatorio.put(SUBREPORT_DIR, arquivoCaminhoSubRelatorioDir);

            //Carrega o arquivo Compilado para memória:
            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioJasper, paramRelatorio, jrBeanColDs);

            switch (tipoRelatorio) {
                case RELATORIO_PDF:
                    tipoArquivoExportado = new JRPdfExporter();
                    extensaoArquivoExportado = EXTENSION_PDF;
                    break;
                case RELATORIO_EXCEL:
                    tipoArquivoExportado = new JRXlsExporter();
                    extensaoArquivoExportado = EXTENSION_XLS;
                    break;
                case RELATORIO_PLANILHA_OPEN_OFFICE:
                    tipoArquivoExportado = new JROdtExporter();
                    extensaoArquivoExportado = EXTENSION_ODS;
                    break;
                case RELATORIO_HTML:
                    tipoArquivoExportado = new HtmlExporter();
                    extensaoArquivoExportado = EXTENSION_HTML;
                    break;
                default:
                    //Default sempre PDf caso venha algo diferente cai aqui:
                    tipoArquivoExportado = new JRPdfExporter();
                    extensaoArquivoExportado = EXTENSION_PDF;
                    break;
            }

            //Relatorio Saída sempre concatena com (underline + DataAtual)
            nomeRelatorioSaida += UNDERLINE + DateUtil.getDateAtualReportName();

            //Caminho Relatório Exportado:
            arquivoCaminhoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO + extensaoArquivoExportado;

            //Tudo feito em memória, agora vamos criar o arquivo físico:
            arquivoGerado = new File(arquivoCaminhoRelatorio);
            tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            //Nome do Arquivo físico a ser exportado;
            tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

            //Exporta o arquivo:
            tipoArquivoExportado.exportReport();

            //Remove o arquivo do servidor após ser feito o download pelo usuário, para não ficar usando recurso do servidor:
            arquivoGerado.deleteOnExit();

            //Cria o InputStream para ser usado pelo PrimeFaces:
            InputStream inputStream = new FileInputStream(arquivoGerado);

            //Faz o retorno para aplicação:
            //Antes era direto no construtor.. mas isso mudou não tem mais assim nos primefaces mais novos!
            //arquivoRetorno = new DefaultStreamedContent(inputStream, "application/" + extensaoArquivoExportado, nomeRelatorioSaida + PONTO + extensaoArquivoExportado);
            arquivoRetorno = DefaultStreamedContent.builder()
                    .name(nomeRelatorioSaida + PONTO + extensaoArquivoExportado)
                    .contentType("application/" + extensaoArquivoExportado)
                    .stream(() -> inputStream).build();

            return arquivoRetorno;
        }

        return null;
    }
}
