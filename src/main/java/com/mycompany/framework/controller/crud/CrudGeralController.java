/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.framework.controller.crud;

import com.mycompany.hibernate.impl.crud.CrudImpl;
import java.io.Serializable;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lucia
 */
@Transactional
public class CrudGeralController extends CrudImpl<Object> implements Serializable { 
    
    private static final long serialVersionUID = 1L;
    
}
