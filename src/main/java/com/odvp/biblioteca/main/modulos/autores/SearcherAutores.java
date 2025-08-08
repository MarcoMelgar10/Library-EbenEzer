package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultSimpleSearcher;
public class SearcherAutores extends DefaultSimpleSearcher {
    private ModeloAutores modelo;
    public SearcherAutores(ModeloAutores modelo) {
        this.modelo = modelo;
    }
    @Override
    public void setBuscadorAction(){
        buscador.textProperty().addListener((observable, oldValuem, newValue) -> {
            modelo.setTextoBusqueda(newValue);
        });
    }
}
