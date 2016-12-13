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
import com.fasterxml.jackson.databind.ObjectMapper;
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
   
    /**
     * Devuelve el texto unicamente de un documento especifico
     * @param nombreDoc El nombre del documento
     * @param username El nombre del usuario que posee el documento
     * @return  El texto del documento
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
   
    /**
     * Modifica el texto de un documento actual
     * @param nombreDoc El nombre del documento
     * @param texto El texto nuevo
     * @return ACCEPTED si fue exitosa, NOT_FOUND si no lo fue
     */
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
    
    /**
     * Crea un nuevo documento y lo asigna al autor
     * @param nombreDoc El nombre del nuevo documento
     * @param autor El nombre del autor
     * @return  True si el documento fue creado exitosamente, false si el autor ya tenia un documento con ese nombre
     */
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
    
    /**
     * Comparte un documento a otro usuario para que este pueda accederlo
     * @param nombreDoc El nombre del documento
     * @param username  El nombre del usuario al que se le va a compartir el documento
     * @param autor El nombre de quien posee el documento y lo desea compartir
     * @return True si el documento fue compartido exitosamente, false si ya tiene el documento el usuario
     */
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
    
    /**
     * Maneja los mensajes de un documento que tiene el texto para su reenvio a los suscriptores y lo envia a "/topic/textupdate/"
     * @param docName El nombre del documento
     * @param word El texto del del documento
     */
    @MessageMapping("/textupdate/{docName}")
    public void manejadorMensajesDocumentos(@DestinationVariable("docName") String docName, String word){
        //System.out.println("doc: " + docName + " word: "+ word);
        msgt.convertAndSend("/topic/textupdate/" + docName, word);
    };
}
