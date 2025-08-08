package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ModeloAutores {
    private List<IDatoVisual> autoresMostrados;
    private IDatoVisual autorSeleccionado;
    private String textoBusqueda;

    public static final String OBS_AUTORES_MOSTRADOS = "AUTORES MOSTRADOS";
    public static final String OBS_AUTOR_SELECCIONADO = "AUTOR SELECCIONADO";
    public static final String OBS_TEXTO_BUSQUEDA = "TEXTO BUSQUEDA";
    public static final String OBS_CAMBIO_GENERICO = "CAMBIO GENERICO";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ModeloAutores(){
        autoresMostrados = new ArrayList<>();
        autorSeleccionado = null;
        textoBusqueda = "";
    }

    public IDatoVisual getAutorSeleccionado() {
        return autorSeleccionado;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public List<IDatoVisual> getAutoresMostrados() {
        return autoresMostrados;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        String oldTextoBusqueda = this.textoBusqueda;
        this.textoBusqueda = textoBusqueda;
        support.firePropertyChange(OBS_TEXTO_BUSQUEDA, oldTextoBusqueda, this.textoBusqueda);
    }

    public void setAutoresMostrados(List<IDatoVisual> autoresMostrados) {
        List<IDatoVisual> oldAutores = List.copyOf(this.autoresMostrados);
        this.autoresMostrados = autoresMostrados;
        support.firePropertyChange(OBS_AUTORES_MOSTRADOS, oldAutores, this.autoresMostrados);
        setAutorSeleccionado(null);
    }

    public void setAutorSeleccionado(IDatoVisual autorSeleccionado) {
        if(autorSeleccionado == this.autorSeleccionado) autorSeleccionado = null;
        IDatoVisual oldAutor = this.autorSeleccionado;
        this.autorSeleccionado = autorSeleccionado;
        support.firePropertyChange(OBS_AUTOR_SELECCIONADO, oldAutor, this.autorSeleccionado);
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public void anunciarCambio() {
        support.firePropertyChange(OBS_CAMBIO_GENERICO, false, true);
    }
}
