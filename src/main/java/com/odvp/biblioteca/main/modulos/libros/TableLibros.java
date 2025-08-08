package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.TableDefault;

import java.beans.PropertyChangeEvent;
import java.util.List;


public class TableLibros extends TableDefault {

    ModeloLibros modelo;

    public TableLibros(ModeloLibros modelo) {
        super(
                List.of("Estado", "Titulo", "Autor", "Stock", "Disponible"),
                List.of(50,200,100,70,70),
                List.of(false,true,true,false,false),
                List.of(false,false,false,true,true)
        );
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }
    @Override
    public void setCardsAction(){
        for(Card card : getCards()){
            card.getVista().setOnMouseClicked(e -> modelo.setLibroSeleccionado(card.getDatoVisual()));
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloLibros.OBS_LIBROS_MOSTRADOS)){
            addCards(modelo.getLibrosMostrados());
        }
        if(evt.getPropertyName().equals(ModeloLibros.OBS_LIBRO_SELECCIONADO)){
            for(Card card : getCards()){
                card.setSelected(card.getDatoVisual().equals(evt.getNewValue()));
            }
        }
    }
}
