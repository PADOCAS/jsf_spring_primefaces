/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hibernate.impl.crud;

import java.io.Serializable;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lucia
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//Component = Injeção de dependencia do spring
//Transactional = depende de transação, Propagation.REQUIRED ==Caso não existir a transação ele vai criar pra gente (required), roolbackFor vai dar rollback sempre que tiver uma exception
public class SimpleJdbcInsertImpl extends SimpleJdbcInsert implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleJdbcInsertImpl(DataSource dataSource) {
        super(dataSource);
    }

}
