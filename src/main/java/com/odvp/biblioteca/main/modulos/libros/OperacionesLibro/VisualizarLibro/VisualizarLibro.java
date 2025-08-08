package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.VisualizarLibro;

import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.objetos.Libro;
import com.odvp.biblioteca.database.daos.LibroDAO;

/*
    crea la vista para visualizar libro
 */

public class VisualizarLibro {
    private final LibroDAO libroDAO = new LibroDAO();
    public VisualizarLibro(ModeloLibros modelo){
        Libro libro = libroDAO.obtener(modelo.getLibroSeleccionado().getID());
        VisualizarLibroVentana visualizarLibro = new VisualizarLibroVentana(libro);
    }
}
