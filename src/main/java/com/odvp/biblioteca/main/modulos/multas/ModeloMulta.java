package com.odvp.biblioteca.main.modulos.multas;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.MultaCardData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static com.odvp.biblioteca.main.modulos.libros.ModeloLibros.OBS_CAMBIO_GENERICO;

public class ModeloMulta {
    public static final String OBS_CAMBIO_TEXTO = "CAMBIO_TEXTO";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private IDatoVisual multaSeleccionada;
    private List<IDatoVisual> multaMostrada;
    public static final String OBS_MULTA_MOSTRADAS = "OBS_MULTA_MOSTRADAS";
    public static final String OBS_MULTA_SELECCIONADA = "OBS_MULTA_SELECCIONADA";
    private String textoBusqueda = "";

    public ModeloMulta(){
        multaMostrada = new ArrayList<>();
    }

    public static String OBS_MULTA_SELECCIONADA() {
        return OBS_MULTA_SELECCIONADA;
    }

    public void setMultaMostrada(List<IDatoVisual> multaMostrada){
        List<IDatoVisual> oldDeudaMostrada = List.copyOf(this.multaMostrada);
        this.multaMostrada = multaMostrada;
        support.firePropertyChange(OBS_MULTA_MOSTRADAS, oldDeudaMostrada, this.multaMostrada);
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public void setMultaSeleccionada(IDatoVisual multaSeleccionada){
        if(this.multaSeleccionada == multaSeleccionada) multaSeleccionada = null;
            IDatoVisual oldDeudaSeleccionada = this.multaSeleccionada;
            this.multaSeleccionada = multaSeleccionada;
            support.firePropertyChange(OBS_MULTA_SELECCIONADA, oldDeudaSeleccionada,this.multaSeleccionada);
    }
    public List<IDatoVisual> getMultaMostrada(){
        return multaMostrada;
    }

    public MultaCardData multaSeleccionada(){
        return (MultaCardData) multaSeleccionada;
    }

    public void anunciarCambio(){
        support.firePropertyChange(OBS_CAMBIO_GENERICO, false, true);
    }

    public void setTextoBusqueda(String textoBusqueda){
        String oldTexto = this.textoBusqueda;
        this.textoBusqueda = textoBusqueda;
        support.firePropertyChange(OBS_CAMBIO_TEXTO, oldTexto, this.textoBusqueda);
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    }
