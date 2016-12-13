package msgbroker.persistence;

import msgbroker.model.Documento;
import msgbroker.model.Usuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2091412
 */
public interface ManejadorPersistenciaInterfaz {
    
    //Usuarios
    public boolean loginUsuario(Usuario user);
    
    public boolean registrarUsuario(Usuario user) throws Exception;
   
    public boolean compartirDocumento(String nombreDoc, String autor, String username);
    
    public boolean addDocumentoUsuario(String username, Documento docu);
    
    public Object[] getDocumentosUsuario(String username);
    
    public boolean comprobarExisteDocumento(String username, Documento documento);
    
    
    //Documentos
    public Documento newDocumento(String nombreDoc, String autor);
    
    public void setTextoDocumento(String nombreDoc, String texto);
    
    public Documento getDocumento(String nombreDoc) throws NullPointerException;
    
    public boolean validarNombreDocumento(String nombreDoc);
}
