package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.database.daos.AutorDAO;
import javafx.scene.layout.BorderPane;

public class ModuloAutores extends BorderPane implements IModulo{

    private HeaderAutores headerAutores;
    private TableAutores tableAutores;
    private ServicioBusquedaAutores servicioBusquedaAutores;
    private ModeloAutores modelo;

    public ModuloAutores(ModeloAutores modelo){
        this.modelo = modelo;
        headerAutores = new HeaderAutores(this.modelo);
        tableAutores = new TableAutores(this.modelo);
        servicioBusquedaAutores = new ServicioBusquedaAutores(modelo);
        setTop(headerAutores);
        setCenter(tableAutores);
        cargarDatosIniciales();
    }

    @Override
    public void cargarDatosIniciales(){
        new Thread(() -> {
            AutorDAO autorDAO = new AutorDAO();
            modelo.setAutoresMostrados(autorDAO.obtenerAutoresAlfabeticamente());
        }).start();
    }
}
