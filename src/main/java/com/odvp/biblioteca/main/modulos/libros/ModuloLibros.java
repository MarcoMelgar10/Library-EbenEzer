package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.database.daos.CategoriaDAO;
import com.odvp.biblioteca.database.daos.LibroDAO;
import javafx.concurrent.Task;
import javafx.scene.layout.BorderPane;

public class ModuloLibros extends BorderPane implements IModulo {

    private ModeloLibros modelo;

    private HeaderLibros header;
    private ParametersLibros paramsRight;
    private TableLibros table;

    private ServicioBusquedaLibros busquedaLibros;

    public ModuloLibros(ModeloLibros modelo){
        this.modelo = modelo;
        header = new HeaderLibros(this.modelo);
        paramsRight = new ParametersLibros(this.modelo);
        table = new TableLibros(this.modelo);
        busquedaLibros = new ServicioBusquedaLibros(this.modelo);

        setTop(header);
        setRight(paramsRight);
        setCenter(table);
        cargarDatosIniciales();
    }


    public void cargarDatosIniciales(){
        new Thread(new Task<>() {

            @Override
            protected Object call() throws Exception {
                LibroDAO libroDAO = new LibroDAO();
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                modelo.setLibrosMostrados(libroDAO.listaLibrosVisual());
                modelo.setCategoriasMostradas(categoriaDAO.listaCategorias());
                modelo.setFiltrosMostrados(ServicioFiltros.obtenerFiltrosPredeterminados());
                return null;
            }
        }).start();
    }
}