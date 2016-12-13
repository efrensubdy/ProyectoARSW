    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.controllers;

/**
 *
 * @author 2092955
 */

import msgbroker.model.Documento;
import msgbroker.services.ManejadorDocumentosInterfaz;
import msgbroker.services.ManejadorUsuariosInterfaz;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import msgbroker.persistence.ManejadorPersistenciaInterfaz;


@Component
@Service
@RestController
@RequestMapping(value="/texto")
public class DocumentsResourceController {
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    ManejadorPersistenciaInterfaz persistencia;
    
    /*
    @Autowired
    ManejadorDocumentosInterfaz persistencia;
    
    @Autowired
    ManejadorUsuariosInterfaz persistencia;
    */
   
    
    @RequestMapping(path = "/{nombreDoc}", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetTextoDocumento(@PathVariable String nombreDoc, String username){
        ResponseEntity a;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Documento docu = persistencia.getDocumento(nombreDoc);
            a = (persistencia.comprobarExisteDocumento(username, docu)
                    ? new ResponseEntity<>(mapper.writeValueAsString(docu),HttpStatus.ACCEPTED) 
                    : null);
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
            a = new ResponseEntity<>(persistencia.newDocumento(nombreDoc, user), HttpStatus.ACCEPTED);
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
            persistencia.setTextoDocumento(nombreDoc, texto);
            a = new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    
    
    @RequestMapping(path = "/addDoc/{nombreDoc}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorNewDocumento(@PathVariable String nombreDoc, String autor){
        ResponseEntity a;
        try {
            //System.out.println("Nuevo documento creado: " + nombreDoc + " - Autor: " + autor);
            a = new ResponseEntity<>(persistencia.addDocumentoUsuario(autor, persistencia.newDocumento(nombreDoc, autor)), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsResourceController.class.getName()).log(Level.SEVERE, null, ex);
            a = new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
        return a;
    }
    
    @RequestMapping(path = "/shareDoc/{nombreDoc}/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorCompartirDocumento(@PathVariable String nombreDoc, @PathVariable String username, String autor){
        ResponseEntity a;
        try {
            //System.out.println("Compartir documento " + nombreDoc + " - de " + autor + " para " + username);
            a = new ResponseEntity<>(persistencia.compartirDocumento(nombreDoc, autor, username), HttpStatus.ACCEPTED);
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
