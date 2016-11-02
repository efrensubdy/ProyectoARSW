/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author sebastian
 */
@Service
public class manejadorTexto {

    private ConcurrentHashMap<String, Texto> documentos;

    
    public manejadorTexto() {
        documentos = new ConcurrentHashMap<String, Texto>();
        Texto g=new Texto("hola","hola","dcdddddddddddddddddddddddd");
        this.setTexto(g);
    }
    
    
    public void setTexto(Texto t){
        documentos.put(t.getNombre(), t);
    }
    
    public Texto getTexto(String t){
        return documentos.get(t);
    }
    
    public boolean contains(String t){
        return documentos.containsKey(t);
    }
    
    public ConcurrentHashMap<String,Texto> getDocumentos(){
        return documentos;
    }
    
    
}
