package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultSimpleSearcher;

public class SearcherUsuario extends DefaultSimpleSearcher {

    private final ModeloUsuarios modelo;

    public SearcherUsuario(ModeloUsuarios modelo){
        super();
        this.modelo = modelo;
    }
    @Override
    public void setBuscadorAction(){
        buscador.textProperty().addListener((observable, oldValuem, newValue) -> {
            modelo.setTextoBusqueda(newValue);
        });
    }
}
