/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.services;

import msgbroker.model.Documento;
import msgbroker.model.Usuario;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan Pablo
 */
@Component
@Service
public class ManejadorUsuarios implements ManejadorUsuariosInterfaz{
    ConcurrentHashMap<String, Usuario> usuarios;
    
    public ManejadorUsuarios(){
        usuarios = new ConcurrentHashMap<>();
        Usuario newUser = new Usuario("pepito", "asd");
        usuarios.put("pepito", newUser);
    }
    
    @Override
    public boolean loginUsuario(Usuario user){
        return usuarios.get(user.getUsername()).getPassword().equals(user.getPassword());
    }
     
    @Override
    public boolean registrarUsuario(Usuario user) throws Exception{
        if(!usuarios.containsKey(user.getUsername())){
            usuarios.put(user.getUsername(), user);
            return true;
        }else{
            throw new Exception();
        }
    }
    
    @Override
    public boolean compartirDocumento(String nombreDoc, String autor, String username){
        return usuarios.get(username).addDocumento(usuarios.get(autor).getSingleDocumento(nombreDoc));
    }
    
    @Override
    public boolean addDocumentoUsuario(String username, Documento docu){
        return usuarios.get(username).addDocumento(docu);
    }
    
    @Override
    public Object[] getDocumentosUsuario(String username){
        return usuarios.get(username).getDocumentosNames().toArray();
    }
    
    @Override
    public boolean comprobarExisteDocumento(String username, Documento documento){
        return usuarios.get(username).comprobarExisteDocumento(documento);
    }
    
}
