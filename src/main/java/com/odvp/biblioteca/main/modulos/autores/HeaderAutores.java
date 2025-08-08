package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.AgregarAutor.AgregarAutor;
import com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.EditarAutor.EditarAutor;
import com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.EliminarAutor.EliminarAutor;
import com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.VisualizarAutor.VisualizarAutor;
import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.main.modulos.prestamos.SearcherPrestamo;
import com.odvp.biblioteca.servicios.ServicioBotones;

import java.beans.PropertyChangeEvent;

public class HeaderAutores extends HeaderDefault {

    private final ButtonDefault agregarButton = ServicioBotones.createBotonAgregar();
    private final ButtonDefault editarButton = ServicioBotones.createButtonEditar();
    private final ButtonDefault visualizarButton = ServicioBotones.createButtonVisualizar();
    private final ButtonDefault eliminarButton = ServicioBotones.createButtonEliminar();
    private SearcherAutores searcherAutores;


    private ModeloAutores modelo;

    public HeaderAutores(ModeloAutores modelo) {
        super("Autores");
        this.modelo = modelo;
        this.modelo.addObserver(this);
        searcherAutores = new SearcherAutores(this.modelo);
        agregarButton.setOnMouseClicked(e -> new AgregarAutor(this.modelo));
        editarButton.setOnMouseClicked(e -> new EditarAutor(this.modelo));
        visualizarButton.setOnMouseClicked(e -> new VisualizarAutor(this.modelo));
        eliminarButton.setOnMouseClicked(e -> new EliminarAutor(this.modelo));
        addButtons(agregarButton, visualizarButton, editarButton, eliminarButton);
        setSearcherContainer(searcherAutores);

    }

    @Override
    public void deshabilitarBotones(boolean deshabilitar) {
        editarButton.desactivar(deshabilitar);
        visualizarButton.desactivar(deshabilitar);
        eliminarButton.desactivar(deshabilitar);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloAutores.OBS_AUTOR_SELECCIONADO)){
            if(evt.getOldValue() == null){
                deshabilitarBotones(false);
            }
            if(evt.getNewValue() == null){
                deshabilitarBotones(true);
            }
        }
    }
}
