/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    ManejadorUsuarios users;
    
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
    
    //incompleta
    @RequestMapping(path = "/documents", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorDocumentsUser(Usuario user) {
        ResponseEntity a;
        try {
            a = new ResponseEntity<>(users.getDocumentosUsuario(user), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            //Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("No existen documentos", HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
}
