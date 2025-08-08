package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.main.modulos.defaulltComponents.DefaultDynamicSearcher;
import com.odvp.biblioteca.servicios.ServicioIconos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class SearcherPrestamo extends DefaultDynamicSearcher {
    private final TipoBusqueda porTitulo = new TipoBusqueda("Por Titulo", ServicioIconos.LIBRO_POR_TITULO, "text-field-search-orange","button-orange");
    private final TipoBusqueda porUsuario = new TipoBusqueda("Por Usuario", ServicioIconos.OPCION_MODULO_USUARIOS,  "text-field-search-purple","button-purple");
    private final ModeloPrestamos modelo;
    private static Logger log = LogManager.getRootLogger();


    public SearcherPrestamo(ModeloPrestamos modelo){
        super();
        this.modelo = modelo;
        modelo.addObserver(this);
        addTiposBusqueda(List.of(porTitulo, porUsuario));
        modelo.setTipo_de_busqueda(ModeloPrestamos.BUSQUEDA_POR_TITULO);
    }

    @Override
    public void setButtonAction(){
        iconoContenedor.setOnMouseClicked(e-> {
            if(modelo.getTipo_de_busqueda().equals(ModeloPrestamos.BUSQUEDA_POR_TITULO)){
                modelo.setTipo_de_busqueda(ModeloPrestamos.BUSQUEDA_POR_USUARIO);
                return;
            }
            modelo.setTipo_de_busqueda(ModeloPrestamos.BUSQUEDA_POR_TITULO);
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
        if(ModeloPrestamos.OBS_TIPO_DE_BUSQUEDA.equals(evt.getPropertyName())){
            log.info("Cambio en tipo de busqueda");
            if(modelo.getTipo_de_busqueda().equals(ModeloPrestamos.BUSQUEDA_POR_TITULO)){
                setTipoSeleccionado(porTitulo);
                return;
            }
            setTipoSeleccionado(porUsuario);
        }
    }
}
