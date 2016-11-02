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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping(value="/Texto")
public class textoController {
    
    
    @Autowired
    manejadorTexto mat;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?>manejadorDeRecurso(){
       
       ResponseEntity a;
        try {
            //obtener datos que se enviarán a través del API
            a = new ResponseEntity<>(mat.getDocumentos(),HttpStatus.ACCEPTED);
            
        } catch (Exception ex) {
            Logger.getLogger(textoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
       return a;
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Texto> manejadorOrden(@PathVariable String id) {
        try {
            //System.out.println(orde + " " + mo.getOrdenes().get(orde));
            Texto a=mat.getTexto(id);
            System.out.println(a.getTexto());
            return new ResponseEntity<>(a, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            org.apache.log4j.Logger.getLogger(textoController.class.getName()).log(Priority.ERROR, null, ex);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Texto> manejadorPostOrdenes(@RequestBody Texto orde) {
        //Registrar
        mat.setTexto(orde);
        //System.out.println(orde + " " + mo.getOrdenes().get(orde));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
