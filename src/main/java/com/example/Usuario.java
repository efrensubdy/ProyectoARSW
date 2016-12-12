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

    private String username;
    private String password;
    
    private ConcurrentHashMap<String, Documento> documentos = new ConcurrentHashMap<>(); 
    
    public Usuario(){
        
    }
    
    public Usuario(String username, String password){
        super();
        this.username = username;
        this.password = password;
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
    
    public boolean addDocumento(Documento documento){
        boolean res = false;
        System.out.println("nomDoc " + documento.getNombreDoc());
        if(!documentos.containsKey(documento.getNombreDoc())){
            documentos.put(documento.getNombreDoc(), documento);
            System.out.println("-> " + documento.getAutor() + " - " + this.username);
            res = true;
        }
        return res;
    }
    
    public ArrayList<String> getDocumentosNames(){
        return new ArrayList<>(documentos.keySet());
    }
   
}
