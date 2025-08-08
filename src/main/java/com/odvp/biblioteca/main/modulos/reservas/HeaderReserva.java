package com.odvp.biblioteca.main.modulos.reservas;

import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.agregarReserva.AgregarReserva;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.editarReserva.EditarReserva;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.eliminarReserva.EliminarReserva;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.visualizarReserva.VIsualizarReserva;
import com.odvp.biblioteca.servicios.ServicioBotones;

import java.beans.PropertyChangeEvent;

public class HeaderReserva extends HeaderDefault {
    private ButtonDefault buttonNew = ServicioBotones.createBotonAgregar();
    private ButtonDefault buttonEdit = ServicioBotones.createButtonEditar();
    private ButtonDefault buttonDelete = ServicioBotones.createButtonEliminar();
    private ButtonDefault buttonView = ServicioBotones.createButtonVisualizar();
    private SearcherReserva searcher;
    private ModeloReserva modelo;
    public HeaderReserva(ModeloReserva modelo) {
        super("Reserva");
        this.modelo = modelo;
        searcher = new SearcherReserva(this.modelo);
        searcher.getBuscador().setPromptText("Usuario");
        this.modelo.addObserver(this);
        addButtons(buttonNew, buttonView, buttonEdit, buttonDelete);
        deshabilitarBotones(true);
        setSearcherContainer(searcher);
        buttonNew.setOnMouseClicked(e -> new AgregarReserva(modelo));
        buttonDelete.setOnMouseClicked(e -> new EliminarReserva(modelo));
        buttonView.setOnMouseClicked(e -> new VIsualizarReserva(modelo));
        buttonEdit.setOnMouseClicked(e -> new EditarReserva(modelo));
    }
        @Override
        public void deshabilitarBotones(boolean deshabilitar){
            buttonEdit.desactivar(deshabilitar);
            buttonView.desactivar(deshabilitar);
            buttonDelete.desactivar(deshabilitar);
        }
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ModeloReserva.OBS_RESERVA_SELECCIONADA())){
                if(evt.getOldValue() != null && evt.getNewValue() != null) return;
                deshabilitarBotones(evt.getNewValue() == null);
            }
        }

    }
