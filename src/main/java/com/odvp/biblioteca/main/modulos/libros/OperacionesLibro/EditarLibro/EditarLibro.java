package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.EditarLibro;

import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.objetos.Libro;
import com.odvp.biblioteca.database.daos.AutorDAO;
import com.odvp.biblioteca.database.daos.CategoriaDAO;
import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.SubCategoriaDAO;

/*
    crea la ventana para editar un libro
 */

public class EditarLibro {
    private final LibroDAO libroDAO = new LibroDAO();
    private final AutorDAO autorDAO = new AutorDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final SubCategoriaDAO subCategoriaDAO =  new SubCategoriaDAO();
    public EditarLibro(ModeloLibros modelo){

        Libro libro = libroDAO.obtener(modelo.getLibroSeleccionado().getID());
        EditarLibroVentana editarLibro = new EditarLibroVentana(libro,libroDAO ,autorDAO, categoriaDAO, subCategoriaDAO);
        if(editarLibro.isHubieronCambios()) modelo.anunciarCambio();
    }


}
