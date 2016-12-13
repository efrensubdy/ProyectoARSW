/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.controllers;

import msgbroker.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import msgbroker.persistence.ManejadorPersistenciaInterfaz;

/**
 *
 * @author 2091412
 */
@Component
@Service
@RestController
@RequestMapping(value="/user")
public class UsersResourceController {
    
    @Autowired
    ManejadorPersistenciaInterfaz users;
    
    /**
     * Manejador para el recurso de login (/login)
     * @param user Recibe un usuario construido en el body. Esto se hace mediante un json con los atributos de username y password.
     * @return true si el usuario fue logeado correctamente, false si el usuario existe en la base de datos pero la contraseña fue invalida, 
     * failed si el usuario no existe en la base de datos
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorLoginUser(Usuario user) {
        ResponseEntity a;
        try {
            a = new ResponseEntity<>(users.loginUsuario(user), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            //Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    /**
     * Maneja las peticiones para registrar usuarios
     * @param user  El objeto del nuevo usuario
     * @return True si el usuario fue agregado correctamente, exception "HttpStatus.NOT_FOUND" si el usuario ya existe
     */
    @RequestMapping(path = "/registrer", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorRegistrerUser(Usuario user) {
        ResponseEntity a;
        try {
            a = new ResponseEntity<>(users.registrarUsuario(user), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            //Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Usuario ya existe", HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    /**
     * Maneja las peticiones para devolver los documentos que posee un usuario
     * @param username  El usuario con documentos
     * @return Un arreglo Object[] 
     */
    @RequestMapping(path = "/listaDocumentos/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorDocumentsUser(@PathVariable String username) {
        ResponseEntity a;
        try {
            //List<String> l = new ArrayList<String>();
            a = new ResponseEntity<>(users.getDocumentosUsuario(username), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            //Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("No existen documentos", HttpStatus.NOT_FOUND);
        }        
        return a;
    }
}
