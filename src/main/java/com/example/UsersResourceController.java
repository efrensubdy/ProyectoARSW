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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2091412
 */
@RestController
@RequestMapping(value="/user")
public class UsersResourceController {
    
    @Autowired
    ManejadorUsuarios users;
    
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorLoginUser(Usuario user) {
        //Crear documento con el usuario que lo creo
        ResponseEntity a;
        try {
            System.out.println("User login 2");
            a = new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
}
