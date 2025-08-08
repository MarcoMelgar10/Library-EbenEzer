package com.odvp.biblioteca.main.modulos.reservas;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.MultaCardData;
import com.odvp.biblioteca.objetosVisuales.ReservaCardData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static com.odvp.biblioteca.main.modulos.libros.ModeloLibros.OBS_CAMBIO_GENERICO;

public class ModeloReserva {
    public static final String OBS_CAMBIO_TEXTO = "CAMBIO_TEXTO";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private IDatoVisual reservaSeleccionada;
    private List<IDatoVisual> reservaMostrada;
    public static final String OBS_RESERVA_MOSTRADAS = "OBS_RESERVA_MOSTRADAS";
    public static final String OBS_RESERVA_SELECCIONADA = "OBS_RESERVA_SELECCIONADA";
    private String textoBusqueda = "";

    public ModeloReserva(){
        reservaMostrada = new ArrayList<>();
    }

    public static String OBS_RESERVA_SELECCIONADA() {
        return OBS_RESERVA_SELECCIONADA;
    }

    public void setReservaMostrada(List<IDatoVisual> reservaMostrada){
        List<IDatoVisual> oldDeudaMostrada = List.copyOf(this.reservaMostrada);
        this.reservaMostrada = reservaMostrada;
        support.firePropertyChange(OBS_RESERVA_MOSTRADAS, oldDeudaMostrada, this.reservaMostrada);
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public void setReservaSeleccionada(IDatoVisual reservaSeleccionada){
        if(this.reservaSeleccionada == reservaSeleccionada) reservaSeleccionada = null;
        IDatoVisual oldDeudaSeleccionada = this.reservaSeleccionada;
        this.reservaSeleccionada = reservaSeleccionada;
        support.firePropertyChange(OBS_RESERVA_SELECCIONADA, oldDeudaSeleccionada,this.reservaSeleccionada);
    }
    public List<IDatoVisual> getReservaMostrada(){
        return reservaMostrada;
    }

    public ReservaCardData reservaSeleccionada(){
        return (ReservaCardData) reservaSeleccionada;
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
