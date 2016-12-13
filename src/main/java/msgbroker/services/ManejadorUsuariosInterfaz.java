/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2091412
 */

package msgbroker.services;

import msgbroker.model.Documento;
import msgbroker.model.Usuario;


public interface ManejadorUsuariosInterfaz {
    
    public boolean loginUsuario(Usuario user);
     
    public boolean registrarUsuario(Usuario user) throws Exception;
    
    public boolean compartirDocumento(String nombreDoc, String autor, String username);
    
    public boolean addDocumentoUsuario(String username, Documento docu);
    
    public Object[] getDocumentosUsuario(String username);
    
    public boolean comprobarExisteDocumento(String username, Documento documento);
}
