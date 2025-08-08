package com.odvp.biblioteca.main.modulos.defaulltComponents;

import com.odvp.biblioteca.LibraryApplication;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class HeaderDefault extends HBox implements PropertyChangeListener {

    private String titulo;

    private Label titleLabel;
    private Pane spacer;
    private HBox buttonContainer;
    private HBox searcherContainer = new HBox();
    private List<ButtonDefault> botones;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public HeaderDefault(String titulo) {
        support.addPropertyChangeListener(this);
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        this.titulo = titulo;
        initialize();
        botones = new ArrayList<>();
    }

    private void initialize() {
        // Configuración de la caja principal
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setMinHeight(70.0);
        this.setPrefHeight(70.0);
        this.setPadding(new Insets(20, 20, 20, 20));

        // Label principal
        titleLabel = new Label(titulo);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI';");


        // Espaciador flexible
        spacer = new Pane();
        spacer.setPrefHeight(200.0);
        spacer.setPrefWidth(200.0);
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Contenedor de botones
        buttonContainer = new HBox();
        buttonContainer.setAlignment(javafx.geometry.Pos.CENTER);
        buttonContainer.setSpacing(8.0);
        HBox.setHgrow(buttonContainer, javafx.scene.layout.Priority.NEVER);
        HBox.setMargin(buttonContainer, new Insets(0, 20, 0, 0));
        // Agregar elementos a la caja principal
        this.getChildren().addAll(titleLabel, spacer, buttonContainer);
        setButtonsAction();
    }


    public void addButton(ButtonDefault button){
        buttonContainer.getChildren().add(button);
        botones.add(button);
    }

    public void addButtons(ButtonDefault... buttons){
        for(ButtonDefault button : buttons){
            buttonContainer.getChildren().add(button);
            botones.add(button);
        }
    }

    public void setSearcherContainer(HBox searcher){
        getChildren().remove(searcherContainer);
        searcherContainer = searcher;
        getChildren().add(searcherContainer);

    }

    public HBox getButtonContainer() {
        return buttonContainer;
    }

    public void deshabilitarBotones(boolean deshabilitar){
        for(ButtonDefault button : botones){
            button.desactivar(deshabilitar);
        }
    }
    public void setButtonsAction(){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
