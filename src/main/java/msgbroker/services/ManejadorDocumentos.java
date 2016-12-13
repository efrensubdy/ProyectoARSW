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
    
    /**
     * Crea un nuevo documento
     * @param nombreDoc El nombre del nuevo documento
     * @param autor El nombre del autor del documento
     * @return  El objecto documento nuevo, null si ya existia
     */
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
    
    /**
     * Modifica el texto de un documento
     * @param nombreDoc El nombre del documento
     * @param texto El nuevo texto
     */
    @Override
    public void setTextoDocumento(String nombreDoc, String texto){
        documentos.get(nombreDoc).setTexto(texto);
    }
    
    /**
     * Devuelve un objeto documento con el nombre como parametro
     * @param nombreDoc El nombre del documento
     * @return El objeto documento
     * @throws NullPointerException si el documento no existe
     */
    @Override
    public Documento getDocumento(String nombreDoc) throws NullPointerException{
        return documentos.get(nombreDoc);
    }
    
    /**
     * Valida si no existe otro documento ya creado con un nombre particular
     * @param nombreDoc El nombre de un documento a validar
     * @return  True si el documento con ese nombre no existe, false de lo contrario
     */
    @Override
    public boolean validarNombreDocumento(String nombreDoc){
        nombreDoc = nombreDoc.trim();
        return (!documentos.containsKey(nombreDoc) && nombreDoc != null && !nombreDoc.equals(""));
    }
}
