package com.odvp.biblioteca.main;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModeloMain {
    public static final String MODULO_LIBROS = "MODULO LIBROS";
    public static final String MODULO_AUTORES = "MODULO AUTORES";
    public static final String MODULO_MULTAS = "MODULO MULTAS";
    public static final String MODULO_PRESTAMOS = "MODULO PRESTAMOS";
    public static final String MODULO_USUARIOS = "MODULO USUARIOS";
    public static final String MODULO_RESERVAS = "MODULO RESERVAS";

    private final PropertyChangeSupport suport = new PropertyChangeSupport(this);

    public static final String OBS_CURRENT_MODULO = "OBS CURRENT MODULO";

    private String currentModulo;


    public ModeloMain(){

    }

    public String getCurrentModulo() {
        return currentModulo;
    }

    public void setCurrentModulo(String currentModulo) {
        String oldCurrentModulo = this.currentModulo;
        this.currentModulo = currentModulo;
        suport.firePropertyChange(OBS_CURRENT_MODULO, oldCurrentModulo, this.currentModulo);
    }

    public void addObserver(PropertyChangeListener observer){
        suport.addPropertyChangeListener(observer);
    }
}
