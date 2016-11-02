/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 2091412
 */
public class ManejadorDocumentos {
    ConcurrentHashMap<String, Documento> documentos;
    
    public ManejadorDocumentos(){
        documentos = new ConcurrentHashMap<>();
    }
    
    public void newDocumento(String nombreDoc, String autor){
       Documento newDoc = new Documento(nombreDoc, autor);
       documentos.put(nombreDoc, newDoc);
    };
    
    public void setTextoDocumento(String nombreDoc, String texto){
        System.out.println("el nombre " + nombreDoc);
        documentos.get(nombreDoc).setTexto(texto);
    }
    
    public String getTextoDocumento(String nombreDoc){
        return documentos.get(nombreDoc).getTexto();
    }
    
}
