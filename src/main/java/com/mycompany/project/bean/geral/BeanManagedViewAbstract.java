/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.bean.geral;

import com.mycompany.hibernate.interfaces.crud.IInterfaceCrud;
import com.mycompany.project.annotation.IdentificaCampoPesquisa;
import com.mycompany.project.report.util.BeanReportView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.model.SelectItem;
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

    public List<SelectItem> listCampoConsulta;

    public ObjetoCampoConsulta getObjetoCampoConsulta() {
        return objetoCampoConsulta;
    }

    public void setObjetoCampoConsulta(ObjetoCampoConsulta objetoCampoConsulta) {
        this.objetoCampoConsulta = objetoCampoConsulta;
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
        listCampoConsulta = new ArrayList<>();
        List<ObjetoCampoConsulta> listObjCamPesTemp = new ArrayList<>();

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
                objCamCon.setClasse(field.getType());

                listObjCamPesTemp.add(objCamCon);
            }
        }

        //Ordena a Lista respeitando o que foi passado no VO:
        ordenaLista(listObjCamPesTemp);

        for (ObjetoCampoConsulta objCamPes : listObjCamPesTemp) {
            listCampoConsulta.add(new SelectItem(objCamPes));
        }

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

}
