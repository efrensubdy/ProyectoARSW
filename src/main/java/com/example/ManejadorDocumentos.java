/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2091412
 */
@Component
@Service
public class ManejadorDocumentos implements ManejadorDocumentosInterfaz{
    ConcurrentHashMap<String, Documento> documentos;
    
    public ManejadorDocumentos(){
        documentos = new ConcurrentHashMap<>();
    }
    
    @Override
    public boolean newDocumento(String nombreDoc, String autor){
        boolean valid = false;
        if(validarNombreDocumento(nombreDoc)){
            Documento newDoc = new Documento(nombreDoc, autor);
            documentos.put(nombreDoc, newDoc);
            valid = true;
        }
        return valid;
    };
    
    @Override
    public void setTextoDocumento(String nombreDoc, String texto){
        documentos.get(nombreDoc).setTexto(texto);
    }
    
    @Override
    public String getTextoDocumento(String nombreDoc) throws NullPointerException{
        return documentos.get(nombreDoc).getTexto();
    }
    
    @Override
    public boolean validarNombreDocumento(String nombreDoc){
        nombreDoc = nombreDoc.trim();
        return (!documentos.containsKey(nombreDoc) && nombreDoc != null && !nombreDoc.equals(""));
    }
}
