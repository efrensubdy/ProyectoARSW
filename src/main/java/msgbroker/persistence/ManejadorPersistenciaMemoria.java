package msgbroker.persistence;

import msgbroker.model.Documento;
import msgbroker.model.Usuario;
import msgbroker.services.ManejadorDocumentosInterfaz;
import msgbroker.services.ManejadorUsuariosInterfaz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2091412
 */
@Component
@Service
public class ManejadorPersistenciaMemoria implements ManejadorPersistenciaInterfaz{
    
    @Autowired
    ManejadorUsuariosInterfaz usuarios;
    
    @Autowired
    ManejadorDocumentosInterfaz documentos;
    
    //Usuarios
    @Override
    public boolean loginUsuario(Usuario user){
        return usuarios.loginUsuario(user);
    }
    
    @Override
    public boolean registrarUsuario(Usuario user) throws Exception{
        return usuarios.registrarUsuario(user);
    }
   
    @Override
    public boolean compartirDocumento(String nombreDoc, String autor, String username){
        return usuarios.compartirDocumento(nombreDoc, autor, username);
    }
    
    @Override
    public boolean addDocumentoUsuario(String username, Documento docu){
        return usuarios.addDocumentoUsuario(username, docu);
    }
   
    @Override
    public Object[] getDocumentosUsuario(String username){
        return usuarios.getDocumentosUsuario(username);
    }
    
    @Override
    public boolean comprobarExisteDocumento(String username, Documento documento){
        return usuarios.comprobarExisteDocumento(username, documento);
    }
    
    
    //Documentos
    @Override
    public Documento newDocumento(String nombreDoc, String autor){
        return documentos.newDocumento(nombreDoc, autor);
    }
    
    @Override
    public void setTextoDocumento(String nombreDoc, String texto){
        documentos.setTextoDocumento(nombreDoc, texto);
    }
    
    @Override
    public Documento getDocumento(String nombreDoc) throws NullPointerException{
        return documentos.getDocumento(nombreDoc);
    }
    
    @Override
    public boolean validarNombreDocumento(String nombreDoc){
        return documentos.validarNombreDocumento(nombreDoc);
    }
    
    
}
