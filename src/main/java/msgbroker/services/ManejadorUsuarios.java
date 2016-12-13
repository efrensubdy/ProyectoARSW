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
    
    /**
     * El constructor por defecto. Crea un usuario pepito con contraseña "asd" para pruebas
     */
    public ManejadorUsuarios(){
        usuarios = new ConcurrentHashMap<>();
        Usuario newUser = new Usuario("pepito", "asd");
        usuarios.put("pepito", newUser);
    }
    
    /**
     * Intenta logear el usuario
     * @param user  El usuario a ingresar
     * @return True si la informacion del usuario es correcta, false si la contraseña es incorrecta. Una Excepcion "NullPointerException" si el usuario no existe
     */
    @Override
    public boolean loginUsuario(Usuario user){
        return usuarios.get(user.getUsername()).getPassword().equals(user.getPassword());
    }
     
    /**
     * Intenta agregar un nuevo usuario
     * @param user  El nuevo usuario
     * @return  True si fue exitoso
     * @throws Exception si ya existe ese usuario
     */
    @Override
    public boolean registrarUsuario(Usuario user) throws Exception{
        if(!usuarios.containsKey(user.getUsername())){
            usuarios.put(user.getUsername(), user);
            return true;
        }else{
            throw new Exception();
        }
    }
    
    /**
     * Comparte un documento con otro usuario, solo si el usuario que lo quiere compartir lo posee
     * @param nombreDoc El nombre del documento
     * @param autor El autor o propietario que desee compartir
     * @param username  El usuario a compartirle el documento
     * @return True si se pudo agregar, false si el usuario ya tenia el documento
     */
    @Override
    public boolean compartirDocumento(String nombreDoc, String autor, String username){
        return usuarios.get(username).addDocumento(usuarios.get(autor).getSingleDocumento(nombreDoc));
    }
    
    /**
     * Agrega un documento a un usuario
     * @param username  El usuario
     * @param docu  El documento
     * @return True si se pudo agregar, false si ya lo tenia
     */
    @Override
    public boolean addDocumentoUsuario(String username, Documento docu){
        return usuarios.get(username).addDocumento(docu);
    }
    
    /**
     * Devuelve un arreglo tipo Object[] de cadenas con la primera posicion el numero de documentos
     * que posee el usuario, y los nombres de los documentos
     * @param username  El usuario
     * @return 
     */
    @Override
    public Object[] getDocumentosUsuario(String username){
        return usuarios.get(username).getDocumentosNames().toArray();
    }
    
    /**
     * Comprueba si un usuario posee un documento
     * @param username  El nombre del usuario
     * @param documento El documento
     * @return True si lo posee, false de lo contrario
     */
    @Override
    public boolean comprobarExisteDocumento(String username, Documento documento){
        return usuarios.get(username).comprobarExisteDocumento(documento);
    }
    
}
