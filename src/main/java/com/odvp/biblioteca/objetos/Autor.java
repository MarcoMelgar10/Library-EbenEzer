package com.odvp.biblioteca.objetos;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.scene.image.Image;

import java.util.List;

/*
Clase objeto del autor.
 */
public class Autor implements IDatoVisual {
    private Integer ID;
    private String nombre;
    private String descripcion;
    public Autor(int ID, String nombre, String descripcion){
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public List<Object> getDatos() {
        return List.of(new Image(ServicioIconos.AUTOR_ICONO), nombre, descripcion);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
