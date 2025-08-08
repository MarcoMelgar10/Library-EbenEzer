package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.main.modulos.defaulltComponents.TableDefault;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class TableAutores extends TableDefault {

    private ModeloAutores modelo;
    public TableAutores(ModeloAutores modelo) {
        super(
              List.of("","Nombre","Reseña"),
              List.of(45,200, 300),
              List.of(false,false,true),
              List.of(false,false,false)
        );
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }

    @Override
    public void setCardsAction() {
        for(Card card: getCards()){
            card.getVista().setOnMouseClicked(e -> modelo.setAutorSeleccionado(card.getDatoVisual()));
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if(evt.getPropertyName().equals(ModeloAutores.OBS_AUTORES_MOSTRADOS)){
            addCards(modelo.getAutoresMostrados());
        }
        if(evt.getPropertyName().equals(ModeloAutores.OBS_AUTOR_SELECCIONADO)){
            for(Card card : getCards()){
                card.setSelected(card.getDatoVisual().equals(evt.getNewValue()));
            }
        }
    }

}
