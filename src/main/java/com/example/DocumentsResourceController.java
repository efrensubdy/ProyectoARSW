/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

/**
 *
 * @author 2092955
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value="/texto")
public class DocumentsResourceController {
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    ManejadorDocumentosInterfaz docus;
    
    /* Temporal
    TODO Aspecto de usuarios
    @Autowired
    ManejadorUsuarios users;
    */
    
    @RequestMapping(path = "/{nombreDoc}", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetTextoDocumento(@PathVariable String nombreDoc){
        ResponseEntity a;
        try {
            a = new ResponseEntity<>(docus.getTextoDocumento(nombreDoc),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Seguramente un NullPointerException (No existe el documento)",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    @RequestMapping(path = "/{nombreDoc}/{user}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorNewDocumentoUser(@DestinationVariable("nombreDoc") String nombreDoc, @DestinationVariable("user") String user) {
        //Crear documento con el usuario que lo creo
        ResponseEntity a;
        try {
            //System.out.println("Nuevo documento creado: " + nombreDoc);
            a = new ResponseEntity<>(docus.newDocumento(nombreDoc, user), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    @RequestMapping(path = "/{nombreDoc}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorSetTextoDocumento(@PathVariable String nombreDoc, String texto){
        ResponseEntity a;
        try {
            docus.setTextoDocumento(nombreDoc, texto);
            a = new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorNewDocumento(String nombreDoc) {
        //Crear documento
        //cambiar Pepito por usuario cuando la funcionalidad correcta de usuario cuando este lista
        ResponseEntity a;
        try {
            //System.out.println("Nuevo documento creado: " + nombreDoc);
            a = new ResponseEntity<>(docus.newDocumento(nombreDoc, "Pepito"), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    @MessageMapping("/textupdate/{docName}")
    public void manejadorMensajesDocumentos(@DestinationVariable("docName") String docName, String word){
        //System.out.println("doc: " + docName + " word: "+ word);
        msgt.convertAndSend("/topic/textupdate/" + docName, word);
    };
}
