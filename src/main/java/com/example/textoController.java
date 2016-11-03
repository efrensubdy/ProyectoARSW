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
import javax.inject.Inject;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value="/texto")
public class textoController {
    
    
    @Autowired
    manejadorTexto mat;
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    ManejadorDocumentos docus = new ManejadorDocumentos();
    
    
    @RequestMapping(path = "/{nombreDoc}", method = RequestMethod.GET)
    public ResponseEntity<?>manejadorDeRecurso(@PathVariable String nombreDoc){
       ResponseEntity a;
        try {
            a = new ResponseEntity<>(docus.getTextoDocumento(nombreDoc),HttpStatus.ACCEPTED);
            
        } catch (Exception ex) {
            Logger.getLogger(textoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
       return a;
    }
    
    /*
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> manejadorOrden(@PathVariable String id) {
        try {
            //System.out.println(orde + " " + mo.getOrdenes().get(orde));
            Texto a=mat.getTexto(id);
            System.out.println(a.getTexto());
            
            return new ResponseEntity<>("aqui", HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            org.apache.log4j.Logger.getLogger(textoController.class.getName()).log(Priority.ERROR, null, ex);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    */
    
    @RequestMapping(path = "/{docName}/{user}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostOrdenes(@RequestBody String docName, String user, String texto) {
        //Registrar
        //mat.setTexto(texto);
        //System.out.println(orde + " " + mo.getOrdenes().get(orde));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(path = "/{nombreDoc}", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostOrdenes(@PathVariable String nombreDoc, String texto){
        try {    
            docus.setTextoDocumento(nombreDoc, texto);
            System.out.println("el nombre es : " + nombreDoc + " el texto recibido es: " + docus.getTextoDocumento(nombreDoc));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.FORBIDDEN);            
        }  
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostOrdenes(String nombreDoc) {
        //Crear documento
        //cambiar Pepito por usuario cuando la funcionalidad este lista
        docus.newDocumento(nombreDoc, "Pepito");
        System.out.println("Nuevo documento creado: " + nombreDoc);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @MessageMapping("/textupdate/{docName}")
    public void manejadorMensajesDocumentos(@DestinationVariable("docName") String docName, String word){
        msgt.convertAndSend("/docu/textupdate/" + docName, word);
    };
}
