package com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.AgregarAutor;

import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.database.daos.AutorDAO;

/*
    crea la ventana para agregar un libro
 */

public class AgregarAutor {
    private AutorDAO autorDAO = new AutorDAO();
    public AgregarAutor(ModeloAutores modelo){
        AgregarAutorVentana agregarLibro = new AgregarAutorVentana(autorDAO);
        if(agregarLibro.isHubieronCambios()) modelo.anunciarCambio();
    }
}
