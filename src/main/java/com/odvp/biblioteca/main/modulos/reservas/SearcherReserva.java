package com.odvp.biblioteca.main.modulos.reservas;

import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultSimpleSearcher;

public class SearcherReserva extends DefaultSimpleSearcher {
    ModeloReserva modelo;
    public SearcherReserva(ModeloReserva modelo) {
        this.modelo = modelo;
    }
    @Override
    public void setBuscadorAction(){
        buscador.textProperty().addListener((observable, oldValuem, newValue) -> {
            modelo.setTextoBusqueda(newValue);
        });
    }
}
