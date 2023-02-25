/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.report.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lucia
 */
public class DateUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Formata data atual para colocar no nome do relatorio (ddMMyyyyHHmmss)
     * @return 
     */
    public static String getDateAtualReportName() {
        DateFormat df = new SimpleDateFormat("ddMMyyyy_HHmmss");
        return df.format(Calendar.getInstance().getTime());
    }

    /**
     * Retorna data formatada para salvar no banco de dados (com as aspas) ('yyyy-MM-dd')
     * @param data
     * @return 
     */
    public static String formatDataSql(Date data) {
        StringBuilder str = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        str.append("'").append(df.format(data)).append("'");

        return str.toString();
    }
    
    /**
     * Retorna data formatada para salvar no banco de dados (sem aspas) (yyyy-MM-dd)
     * @param data
     * @return 
     */
    public static String formatDataSqlSimple(Date data) {
        StringBuilder str = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        str.append(df.format(data));

        return str.toString();
    }

}
