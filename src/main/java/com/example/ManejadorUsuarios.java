/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan Pablo
 */
@Component
@Aspect
public class ManejadorUsuarios {
    ConcurrentHashMap<String, Usuario> usuarios;
    
    public ManejadorUsuarios(){
        usuarios = new ConcurrentHashMap<>();
        Usuario newUser = new Usuario("pepito", "asd");
        usuarios.put("pepito", newUser);
    }
    
    public boolean loginUsuario(Usuario user){
        return usuarios.get(user.getUsername()).getPassword().equals(user.getPassword());
    }
    
    public boolean registrarUsuario(Usuario user) throws Exception{
        if(!usuarios.containsKey(user.getUsername())){
            usuarios.put(user.getUsername(), user);
            return true;
        }else{
            throw new Exception();
        }
    }
    
    public boolean compartirDocumento(String nombreDoc, String autor, String username){
        return usuarios.get(username).addDocumento(usuarios.get(autor).getSingleDocumento(nombreDoc));
    }
    
    public boolean addDocumentoUsuario(String username, Documento docu){
        return usuarios.get(username).addDocumento(docu);
    }
    
    public Object[] getDocumentosUsuario(Usuario user){
        return usuarios.get(user.getUsername()).getDocumentosNames().toArray();
    }
    
    public boolean comprobarExisteDocumento(String username, Documento documento){
        return usuarios.get(username).comprobarExisteDocumento(documento);
    }
    
}
