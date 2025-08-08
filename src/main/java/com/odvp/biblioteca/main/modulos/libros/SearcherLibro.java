package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultDynamicSearcher;
import com.odvp.biblioteca.servicios.ServicioIconos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class SearcherLibro extends DefaultDynamicSearcher {
    private final TipoBusqueda porTitulo = new TipoBusqueda("Por Titulo", ServicioIconos.LIBRO_POR_TITULO, "text-field-search-orange","button-orange");
    private final TipoBusqueda porAutor = new TipoBusqueda("Por Autor", ServicioIconos.LIBRO_POR_AUTOR,  "text-field-search-purple","button-purple");
    private static Logger log = LogManager.getRootLogger();
    private final ModeloLibros modelo;
    public SearcherLibro(ModeloLibros modelo){
        super();
        this.modelo = modelo;
        modelo.addObserver(this);
        addTiposBusqueda(List.of(porTitulo, porAutor));
        modelo.setTipo_de_busqueda(ModeloLibros.BUSQUEDA_POR_TITULO);
    }

    @Override
    public void setButtonAction(){
        iconoContenedor.setOnMouseClicked(e-> {
            if(modelo.getTipo_de_busqueda().equals(ModeloLibros.BUSQUEDA_POR_TITULO)){
                modelo.setTipo_de_busqueda(ModeloLibros.BUSQUEDA_POR_AUTOR);
                return;
            }
            modelo.setTipo_de_busqueda(ModeloLibros.BUSQUEDA_POR_TITULO);
        });
    }

    @Override
    public void setBuscadorAction(){
        buscador.textProperty().addListener((observable, oldValuem, newValue) -> {
            modelo.setTextoBusqueda(newValue);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(ModeloLibros.OBS_TIPO_DE_BUSQUEDA.equals(evt.getPropertyName())){
            log.info("Se cambio el tipo de busqueda");
            if(modelo.getTipo_de_busqueda().equals(ModeloLibros.BUSQUEDA_POR_TITULO)){
                setTipoSeleccionado(porTitulo);
                return;
            }
            setTipoSeleccionado(porAutor);
        }
    }
}
