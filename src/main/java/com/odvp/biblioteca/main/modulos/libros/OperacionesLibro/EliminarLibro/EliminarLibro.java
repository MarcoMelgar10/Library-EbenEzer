package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.EliminarLibro;

import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.database.daos.LibroDAO;

/*
    crea la ventana de eliminar libro
 */

public class EliminarLibro {
    private final LibroDAO libroDAO = new LibroDAO();
    public EliminarLibro(ModeloLibros modeloLibros){
        EliminarLibroVentana eliminarLibroVentana = new EliminarLibroVentana(modeloLibros.getLibroSeleccionado().getID(), libroDAO);
        if(eliminarLibroVentana.isEliminar()) modeloLibros.anunciarCambio();
    }
}
