package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.main.modulos.defaulltComponents.TableDefault;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Objects;

public class TableUsuarios extends TableDefault {

    private ModeloUsuarios modelo;

    public TableUsuarios(ModeloUsuarios modelo) {
        super(
                List.of("Estado", "Carnet", "Nombre"),
                List.of(45, 70,300),
                List.of(false,false,true),
                List.of(false,false,false)
        );
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }

    @Override
    public void setCardsAction() {
        for(Card card : getCards()){
            card.getVista().setOnMouseClicked(e-> modelo.setUsuarioSeleccionado(card.getDatoVisual()));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if(Objects.equals(evt.getPropertyName(), ModeloUsuarios.OBS_USUARIOS_MOSTRADOS)){
            addCards(modelo.getUsuariosMostrados());
        }
        if(evt.getPropertyName().equals(ModeloUsuarios.OBS_USUARIO_SELECCIONADO)){
            for(Card card : getCards()){
                card.setSelected(card.getDatoVisual().equals(evt.getNewValue()));
            }
        }
    }

}
