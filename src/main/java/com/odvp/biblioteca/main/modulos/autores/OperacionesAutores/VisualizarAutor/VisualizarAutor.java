package com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.VisualizarAutor;

import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.objetos.Autor;
import com.odvp.biblioteca.database.daos.AutorDAO;

/*
    crea la vista para visualizar libro
 */

public class VisualizarAutor {
    private final AutorDAO autorDAO = new AutorDAO();
    public VisualizarAutor(ModeloAutores modelo){
        Autor autor = autorDAO.obtener(modelo.getAutorSeleccionado().getID());
        VisualizarAutorVentana visualizarAutorVentana = new VisualizarAutorVentana(autor);
    }
}
