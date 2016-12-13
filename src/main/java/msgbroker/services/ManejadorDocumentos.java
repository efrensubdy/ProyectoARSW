/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgbroker.services;

import msgbroker.model.Documento;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2091412
 */
@Component
@Service
public class ManejadorDocumentos implements ManejadorDocumentosInterfaz{
    ConcurrentHashMap<String, Documento> documentos;
    
    public ManejadorDocumentos(){
        documentos = new ConcurrentHashMap<>();
    }
    
    @Override
    public Documento newDocumento(String nombreDoc, String autor){
        Documento res = null;
        if(validarNombreDocumento(nombreDoc)){
            Documento newDoc = new Documento(nombreDoc, autor);
            documentos.put(nombreDoc, newDoc);
            res = newDoc;
        }
        return res;
    };
    
    @Override
    public void setTextoDocumento(String nombreDoc, String texto){
        documentos.get(nombreDoc).setTexto(texto);
    }
    
    @Override
    public Documento getDocumento(String nombreDoc) throws NullPointerException{
        return documentos.get(nombreDoc);
    }
    
    @Override
    public boolean validarNombreDocumento(String nombreDoc){
        nombreDoc = nombreDoc.trim();
        return (!documentos.containsKey(nombreDoc) && nombreDoc != null && !nombreDoc.equals(""));
    }
}
