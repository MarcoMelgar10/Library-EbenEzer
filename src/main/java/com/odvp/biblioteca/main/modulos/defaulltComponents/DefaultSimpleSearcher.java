package com.odvp.biblioteca.main.modulos.defaulltComponents;

import com.odvp.biblioteca.LibraryApplication;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DefaultSimpleSearcher extends HBox implements PropertyChangeListener {

    protected TextField buscador;

    public DefaultSimpleSearcher(){
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        buscador = new TextField();
        buscador.setPromptText("Buscar");
        buscador.scaleYProperty().setValue(1.05);
        buscador.getStyleClass().add("text-field-search");
        setAlignment(Pos.CENTER);
        getChildren().addAll(buscador);
        setBuscadorAction();
    }
    public TextField getBuscador() {
        return buscador;
    }
    public void setBuscadorAction(){}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
