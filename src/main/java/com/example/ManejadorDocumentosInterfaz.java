/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Juan Pablo
 */
public interface ManejadorDocumentosInterfaz {
    
    public boolean newDocumento(String nombreDoc, String autor);
    
    public void setTextoDocumento(String nombreDoc, String texto);
    
    public String getTextoDocumento(String nombreDoc);
    
    public boolean validarNombreDocumento(String nombreDoc);
}