package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.UsuarioData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ModeloUsuarios {
    private String textoBusqueda;
    private IDatoVisual usuarioSeleccionado;
    private List<IDatoVisual> usuariosMostrados;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public static final String OBS_CAMBIO_GENERICO = "CAMBIO_GENERICO";
    public static final String OBS_TEXTO_BUSCADOR = "OBS_TEXTO_BUSCADOR";
    public static final String OBS_USUARIOS_MOSTRADOS = "OBS_USUARIOS_MOSTRADOS";
    public static final String OBS_USUARIO_SELECCIONADO = "OBS_USUARIO_SELECCIONADO";

    public ModeloUsuarios(){
        usuariosMostrados = new ArrayList<>();
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public void setUsuariosMostrados(List<IDatoVisual> usuarios){
        List<IDatoVisual> oldUsuariosMostrados = List.copyOf(this.usuariosMostrados);
        this.usuariosMostrados = usuarios;
        support.firePropertyChange(OBS_USUARIOS_MOSTRADOS, oldUsuariosMostrados, this.usuariosMostrados);
        setUsuarioSeleccionado(null);
    }
    public void setUsuarioSeleccionado(IDatoVisual usuarioSeleccionado){
        if(this.usuarioSeleccionado == usuarioSeleccionado) usuarioSeleccionado = null;
        IDatoVisual oldUsuarioSeleccionado = this.usuarioSeleccionado;
        this.usuarioSeleccionado = usuarioSeleccionado;
        support.firePropertyChange(OBS_USUARIO_SELECCIONADO, oldUsuarioSeleccionado,this.usuarioSeleccionado);
    }
    public void anunciarCambio(){
        support.firePropertyChange(OBS_CAMBIO_GENERICO, false, true);
    }

    public UsuarioData getUsuarioSeleccionado() {
        return (UsuarioData) usuarioSeleccionado;
    }

    public List<IDatoVisual> getUsuariosMostrados() {
        return usuariosMostrados;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }
    public void setTextoBusqueda(String textoBusqueda) {
        String oldText = this.textoBusqueda;
        this.textoBusqueda = textoBusqueda;
        support.firePropertyChange(OBS_TEXTO_BUSCADOR, oldText, this.textoBusqueda);
    }
}
