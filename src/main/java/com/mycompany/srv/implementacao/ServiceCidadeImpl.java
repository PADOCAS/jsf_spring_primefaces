/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.srv.implementacao;

import com.mycompany.repository.interfaces.RepositoryCidade;
import com.mycompany.srv.interfaces.ServiceCidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucia
 */
@Service
public class ServiceCidadeImpl implements ServiceCidade {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private RepositoryCidade repositoryCidade;
    
    
    
}
