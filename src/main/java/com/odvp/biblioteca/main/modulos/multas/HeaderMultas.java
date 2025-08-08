package com.odvp.biblioteca.main.modulos.multas;

import com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Agregar.AgregarMulta;
import com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.CancelarMulta.CancelarMulta;
import com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Editar.EditarMulta;
import com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Eliminar.EliminarMulta;
import com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Visualizar.VisualizarMulta;
import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.servicios.ServicioBotones;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class HeaderMultas extends HeaderDefault {
    private ButtonDefault buttonNew = ServicioBotones.createBotonAgregar();
    private ButtonDefault buttonEdit = ServicioBotones.createButtonEditar();
    private ButtonDefault buttonDelete = ServicioBotones.createButtonEliminar();
    private ButtonDefault buttonView = ServicioBotones.createButtonVisualizar();
    private ButtonDefault buttonPay = ServicioBotones.createBotonPersonalizado(ServicioIconos.CANCELAR_MULTA, "button-orange");
    private SearcherMulta searcher;
    private ModeloMulta modelo;

    public HeaderMultas(ModeloMulta modelo) {
        super("Multas");
        this.modelo = modelo;
        searcher = new SearcherMulta(this.modelo);
        searcher.getBuscador().setPromptText("Usuario");
        this.modelo.addObserver(this);
        addButtons(buttonPay,buttonNew, buttonView, buttonEdit, buttonDelete);
        deshabilitarBotones(true);
        setSearcherContainer(searcher);
        buttonNew.setOnMouseClicked(e -> new AgregarMulta(modelo));
        buttonDelete.setOnMouseClicked(e -> new EliminarMulta(modelo));
        buttonView.setOnMouseClicked(e -> new VisualizarMulta(modelo));
        buttonEdit.setOnMouseClicked(e -> new EditarMulta(modelo));
        buttonPay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(modelo.multaSeleccionada().isEstado())
                {new CancelarMulta(modelo);}else{
                    JOptionPane.showMessageDialog(null, "Multa ya cancelada");
                }
            }
        } );
    }

        @Override
        public void deshabilitarBotones(boolean deshabilitar){
            buttonEdit.desactivar(deshabilitar);
            buttonView.desactivar(deshabilitar);
            buttonDelete.desactivar(deshabilitar);
            buttonPay.desactivar(deshabilitar);
        }
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ModeloMulta.OBS_MULTA_SELECCIONADA())){
                if(evt.getOldValue() != null && evt.getNewValue() != null) return;
                deshabilitarBotones(evt.getNewValue() == null);
            }
        }
    }
