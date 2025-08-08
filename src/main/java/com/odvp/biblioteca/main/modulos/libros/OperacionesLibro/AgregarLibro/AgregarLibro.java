package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.AgregarLibro;

import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;

import com.odvp.biblioteca.database.daos.AutorDAO;
import com.odvp.biblioteca.database.daos.CategoriaDAO;
import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.SubCategoriaDAO;

/*
    crea la ventana para agregar un libro
 */

public class AgregarLibro{
    private LibroDAO libroDAO = new LibroDAO();
    private AutorDAO autorDAO = new AutorDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private SubCategoriaDAO subCategoriaDAO = new SubCategoriaDAO();

    public AgregarLibro(ModeloLibros modelo){
        AgregarLibroVentana agregarLibro = new AgregarLibroVentana(libroDAO, autorDAO, categoriaDAO, subCategoriaDAO);
        if(agregarLibro.isHubieronCambios()) modelo.anunciarCambio();
    }
}
