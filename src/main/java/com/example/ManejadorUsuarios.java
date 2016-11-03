/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan Pablo
 */
@Service
public class ManejadorUsuarios {
    ConcurrentHashMap<String, Usuario> usuarios;
    
    public ManejadorUsuarios(){
        usuarios = new ConcurrentHashMap<>();
    }
    
}
