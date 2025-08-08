package com.odvp.biblioteca.main.barraOpciones;

import com.odvp.biblioteca.main.modulos.defaulltComponents.OpcionButton;
import com.odvp.biblioteca.main.ModeloMain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OpcionMainButton extends OpcionButton implements PropertyChangeListener {

    private final String moduloName;
    private final ModeloMain modelo;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public OpcionMainButton(String title, String iconPath, String moduloName, ModeloMain modelo) {
        super(title, iconPath);
        this.moduloName = moduloName;
        this.modelo = modelo;
        support.addPropertyChangeListener(this);
        this.modelo.addObserver(this);
        setOnMouseClicked(e ->{
            this.modelo.setCurrentModulo(moduloName);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloMain.OBS_CURRENT_MODULO)){
            setSelected(evt.getNewValue().equals(moduloName));
        }
    }
}
