package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.agregarPrestamo.AgregarPrestamo;
import com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.devolverLibro.DevolverLibro;
import com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.editarPrestamo.EditarPrestamo;
import com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.eliminarPrestamo.EliminarPrestamo;
import com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.visualizarPrestamo.VisualizarPrestamo;
import com.odvp.biblioteca.servicios.ServicioBotones;
import com.odvp.biblioteca.servicios.ServicioIconos;

import java.beans.PropertyChangeEvent;

public class HeaderPrestamos extends HeaderDefault {

    private final ButtonDefault buttonNew = ServicioBotones.createBotonAgregar();
    private final ButtonDefault buttonEdit = ServicioBotones.createButtonEditar();
    private final ButtonDefault buttonView = ServicioBotones.createButtonVisualizar();
    private final ButtonDefault buttonDelete = ServicioBotones.createButtonEliminar();
    private final ButtonDefault buttonReturnBook = ServicioBotones.createBotonPersonalizado(ServicioIconos.LIBRO_DEVUELTO,"button-orange");

    private SearcherPrestamo searcher;
    private ModeloPrestamos modelo;

    public HeaderPrestamos(ModeloPrestamos modelo) {
        super("Prestamos");
        this.modelo = modelo;
        this.modelo.addObserver(this);
        searcher = new SearcherPrestamo(this.modelo);
        buttonNew.setOnMouseClicked(e -> new AgregarPrestamo(modelo));
        buttonView.setOnMouseClicked(e -> new VisualizarPrestamo(modelo));
        buttonEdit.setOnMouseClicked(e -> new EditarPrestamo(modelo));
        buttonDelete.setOnMouseClicked(e -> new EliminarPrestamo(modelo));
        buttonReturnBook.setOnMouseClicked(e -> new DevolverLibro(modelo));

        deshabilitarBotones(true);
        addButtons(buttonReturnBook, buttonNew,buttonView,buttonEdit,buttonDelete);
        setSearcherContainer(searcher);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloPrestamos.OBS_PRESTAMOS_SELECCIONADO)){
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
        buttonReturnBook.desactivar(deshabilitar);
        buttonEdit.desactivar(deshabilitar);
        buttonView.desactivar(deshabilitar);
        buttonDelete.desactivar(deshabilitar);
    }

}
