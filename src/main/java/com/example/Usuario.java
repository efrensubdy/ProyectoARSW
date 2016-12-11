/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Juan Pablo
 */
public class Usuario {

    private String nombre;
    private String username;
    private String password;
    
    private ConcurrentHashMap<String, Documento> documentos; 
    
    public Usuario(){
        documentos = new ConcurrentHashMap<>();
    }
    
    public Usuario(String nombre, String username, String password){
        super();
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void addDocumento(Documento documento){
        if(!documentos.containsKey(documento.getNombreDoc()))
            documentos.put(documento.getNombreDoc(), documento);
    }
    
    public ArrayList<String> getDocumentosNames(){
        return new ArrayList<>(documentos.keySet());
    }
   
}
