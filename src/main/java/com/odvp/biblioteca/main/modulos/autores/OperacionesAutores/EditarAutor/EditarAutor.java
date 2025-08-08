package com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.EditarAutor;

import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.objetos.Autor;
import com.odvp.biblioteca.database.daos.AutorDAO;

/*
    crea la ventana para editar un libro
 */

public class EditarAutor {
    private final AutorDAO autorDAO = new AutorDAO();
    public EditarAutor(ModeloAutores modelo){
        Autor autor = autorDAO.obtener(modelo.getAutorSeleccionado().getID());

        EditarAutorVentana editarAutorVentana = new EditarAutorVentana(autorDAO,autor);
        if(editarAutorVentana.isHubieronCambios()) modelo.anunciarCambio();
    }


}
