package com.odvp.biblioteca.main.modulos.multas;
import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultSimpleSearcher;


public class SearcherMulta extends DefaultSimpleSearcher {
    private ModeloMulta modelo;
    public SearcherMulta(){
    }
    public SearcherMulta(ModeloMulta modelo){
        super();
        this.modelo = modelo;
        modelo.addObserver(this);
    }


    @Override
    public void setBuscadorAction(){
        buscador.textProperty().addListener((observable, oldValuem, newValue) -> {
            modelo.setTextoBusqueda(newValue);
        });
    }


}
