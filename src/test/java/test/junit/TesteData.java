/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.junit;

import com.mycompany.project.report.util.DateUtil;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author lucia
 */
public class TesteData {
    
    @Test
    public void testData() {
        System.out.println(DateUtil.getDateAtualReportName());
        //Compara valores para ver se são iguais:
//        Assert.assertEquals("25022023", DateUtil.getDateAtualReportName());
        Assert.assertEquals("'2023-02-25'", DateUtil.formatDataSql(Calendar.getInstance().getTime()));
        Assert.assertEquals("2023-02-25", DateUtil.formatDataSqlSimple(Calendar.getInstance().getTime()));
    }
    
}
