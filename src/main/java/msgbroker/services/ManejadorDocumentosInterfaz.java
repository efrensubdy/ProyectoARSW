/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.services;

import msgbroker.model.Documento;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Juan Pablo
 */
public interface ManejadorDocumentosInterfaz {
    
    public Documento newDocumento(String nombreDoc, String autor);
    
    public void setTextoDocumento(String nombreDoc, String texto);
    
    public Documento getDocumento(String nombreDoc);
    
    public boolean validarNombreDocumento(String nombreDoc);
}
