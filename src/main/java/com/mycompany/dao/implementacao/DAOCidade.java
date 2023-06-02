/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao.implementacao;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import com.mycompany.project.model.Cidade;
import com.mycompany.repository.interfaces.RepositoryCidade;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lucia
 */
@Transactional
@Repository
public class DAOCidade extends CrudImpl<Cidade> implements RepositoryCidade {

    private static final long serialVersionUID = 1L;
}
