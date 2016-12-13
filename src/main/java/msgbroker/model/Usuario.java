/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.model;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Juan Pablo
 */
public class Usuario {

    private String username;
    private String password;
    
    private final ConcurrentHashMap<String, Documento> documentos = new ConcurrentHashMap<>(); 
    
    /**
     * Constructor por defecto de Usuario
     */
    public Usuario(){
    }
    
    /**
     * Constructor que recibe un nombre de usuario y una contraseña
     * @param username  El nombre del nuevo usuario
     * @param password  La contraseña del nuevo usuario
     */
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
    
    /**
     * Devuelve un documento unicamente
     * @param nombreDoc El nombre del documento
     * @return  El documento
     */
    public Documento getSingleDocumento(String nombreDoc){
        return documentos.get(nombreDoc);
    }
    
    /**
     * Agrega un documento para el usuario
     * @param documento El documento a agregar
     * @return True si se pudo agregar, false si ya lo posee el usuario
     */
    public boolean addDocumento(Documento documento){
        boolean res = false;
        if(!documentos.containsKey(documento.getNombreDoc())){
            documentos.put(documento.getNombreDoc(), documento);
            res = true;
        }
        return res;
    }
    
    /**
     * Devuelve un arreglo con los nombres de los documentos que posee el usuario y en la primera posicion el numero de documentos
     * @return El arreglo con los nombres de los documentos y la cantidad de estos
     */
    public ArrayList<String> getDocumentosNames(){
        ArrayList<String> array = new ArrayList<>(documentos.keySet());
        array.add(0, ""+documentos.keySet().size());
        return array;
    }
    
    /**
     * Comprueba si el usuario posee el documento
     * @param documento El documento
     * @return True si lo posee, false de lo contrario
     */
    public boolean comprobarExisteDocumento(Documento documento){
        return documentos.containsKey(documento.getNombreDoc());
    }
   
}
