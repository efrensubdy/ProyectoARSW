/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.ArrayList;

/**
 *
 * @author sebastian
 */
public class Texto {
    private int id;
    private String nombre;
    private String texto;
    private ArrayList<Integer> colaboradores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<Integer> getColaboradores() {
        return colaboradores;
    }
    
    public void addColaborador(int i){
        colaboradores.add(i);
    }
    public void delColaborador(int i){
        colaboradores.remove(i);
    }
    
    
}
