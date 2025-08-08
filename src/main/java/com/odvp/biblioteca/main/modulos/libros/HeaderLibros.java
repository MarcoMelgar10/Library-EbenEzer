package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.AgregarLibro.AgregarLibro;
import com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.EditarLibro.EditarLibro;
import com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.EliminarLibro.EliminarLibro;
import com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.VisualizarLibro.VisualizarLibro;
import com.odvp.biblioteca.servicios.ServicioBotones;

import java.beans.PropertyChangeEvent;

public class HeaderLibros extends HeaderDefault {

    private final ButtonDefault buttonNew = ServicioBotones.createBotonAgregar();
    private final ButtonDefault buttonEdit = ServicioBotones.createButtonEditar();
    private final ButtonDefault buttonView = ServicioBotones.createButtonVisualizar();
    private final ButtonDefault buttonDelete = ServicioBotones.createButtonEliminar();

    private SearcherLibro searcher;
    private ModeloLibros modelo;

    public HeaderLibros(ModeloLibros modelo) {
        super("Libros");
        this.modelo = modelo;
        this.modelo.addObserver(this);
        searcher = new SearcherLibro(this.modelo);
        buttonNew.setOnMouseClicked(e -> new AgregarLibro(modelo));
        buttonView.setOnMouseClicked(e -> new VisualizarLibro(modelo));
        buttonEdit.setOnMouseClicked(e -> new EditarLibro(modelo));
        buttonDelete.setOnMouseClicked(e -> new EliminarLibro(modelo));

        deshabilitarBotones(true);
        addButtons(buttonNew,buttonView,buttonEdit,buttonDelete);
        setSearcherContainer(searcher);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloLibros.OBS_LIBRO_SELECCIONADO)){
            if(evt.getOldValue() == null){
                deshabilitarBotones(false);
            }
            if(evt.getNewValue() == null){
                deshabilitarBotones(true);
            }
        }
    }

    @Override
    public void deshabilitarBotones(boolean deshabilitar){
        buttonEdit.desactivar(deshabilitar);
        buttonView.desactivar(deshabilitar);
        buttonDelete.desactivar(deshabilitar);
    }

}
