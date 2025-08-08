package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.main.modulos.defaulltComponents.TableDefault;

import java.beans.PropertyChangeEvent;
import java.util.List;


public class TablePrestamo extends TableDefault {

    ModeloPrestamos modelo;

    public TablePrestamo(ModeloPrestamos modelo) {
        super(
                List.of("Estado", "Codigo", "Usuario", "Libro", "Fecha"),
                List.of(50,70,150,150,70),
                List.of(false,true,true,true, true),
                List.of(false,false,false,false, false)
        );
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }
    @Override
    public void setCardsAction(){
        for(Card card : getCards()){
            card.getVista().setOnMouseClicked(e -> modelo.setPrestamoSeleccionado(card.getDatoVisual()));
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloPrestamos.OBS_PRESTAMOS_MOSTRADOS)){
            addCards(modelo.getPrestamosMostrados());
        }
        if(evt.getPropertyName().equals(ModeloPrestamos.OBS_PRESTAMOS_SELECCIONADO)){
            for(Card card : getCards()){
                card.setSelected(card.getDatoVisual().equals(evt.getNewValue()));
            }
        }
    }
}
