package com.odvp.biblioteca.main.modulos.defaulltComponents;

import com.odvp.biblioteca.LibraryApplication;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ParametersDefault extends VBox implements PropertyChangeListener {

    List<VBox> subwindows = new ArrayList<>();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ParametersDefault() {
        support.addPropertyChangeListener(this);
        this.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
    }

    public VBox addSubWindow(String title, List<Parent> params){
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        VBox window = new VBox();
        VBox.setVgrow(window, Priority.ALWAYS);

        StackPane titlePane = new StackPane();
        titlePane.setStyle("-fx-background-color: #dfdfdf;");
        titlePane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        titlePane.setPrefHeight(20);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold;");
        titlePane.getChildren().add(titleLabel);
        StackPane.setMargin(titleLabel, new Insets(0, 0, 0, 6));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(200, USE_COMPUTED_SIZE);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox bodyScroll = new VBox();
        bodyScroll.setPadding(new Insets(12,0,12,0));
        bodyScroll.setSpacing(12);
        bodyScroll.setPrefSize(200, USE_COMPUTED_SIZE);
        Platform.runLater(() -> {
            for (Parent param : params) {
                bodyScroll.getChildren().add(param);
            }
        });
        scrollPane.setContent(bodyScroll);
        window.getChildren().addAll(titlePane, scrollPane);
        window.setMinHeight(300);
        getChildren().add(window);

        subwindows.add(window);
        return bodyScroll;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    protected class SimpleParam{
        private CheckBox checkBox;
        private String name;
        public SimpleParam(String name){
            this.name = name;
            CheckBox simpleParam = new CheckBox(this.name);
            simpleParam.setStyle("-fx-font-style: italic;");
            simpleParam.setPadding(new Insets(0,0,0,6));
            this.checkBox = simpleParam;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    protected class DateParam{
        private CheckBox checkBox;
        private FiltroFecha filtro;
        private TextField inputFechaInicial, inputFechaFinal;
        private VBox contenedor;
        private GridPane gridFechas;

        public DateParam(FiltroFecha filtroFecha){
            this.filtro = filtroFecha;
            init();
        }

        private void init(){
            VBox dateContainer = new VBox();
            CheckBox parametroFecha = new CheckBox(filtro.getNombre());
            parametroFecha.setStyle("-fx-font-style: italic;");
            StackPane dateFilterPane = new StackPane(parametroFecha);
            dateFilterPane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            StackPane.setMargin(parametroFecha, new Insets(0, 0, 0, 6));

            GridPane gridFecha = new GridPane();
            gridFecha.setDisable(true);
            gridFecha.setPadding(new Insets(3, 0, 0, 30));
            parametroFecha.setOnAction(event -> gridFecha.setDisable(!parametroFecha.isSelected()));

            Label desdeLabel = new Label("Desde");
            desdeLabel.setStyle("-fx-font-size: 10;");
            GridPane.setColumnIndex(desdeLabel, 0);
            GridPane.setRowIndex(desdeLabel, 0);


            TextField fieldDesdeFecha = new TextField();
            fieldDesdeFecha.setPrefSize(50, 6);
            fieldDesdeFecha.setScaleY(0.7);
            GridPane.setColumnIndex(fieldDesdeFecha, 1);
            GridPane.setRowIndex(fieldDesdeFecha, 0);

            Label hastaLabel = new Label("Hasta");
            hastaLabel.setStyle("-fx-font-size: 10;");
            GridPane.setColumnIndex(hastaLabel, 0);
            GridPane.setRowIndex(hastaLabel, 1);

            TextField fieldHastaFecha = new TextField();
            fieldHastaFecha.setPrefSize(50, 6);
            fieldHastaFecha.setScaleY(0.7);
            GridPane.setColumnIndex(fieldHastaFecha, 1);
            GridPane.setRowIndex(fieldHastaFecha, 1);

            gridFecha.getChildren().addAll(desdeLabel, fieldDesdeFecha, hastaLabel, fieldHastaFecha);

            dateContainer.getChildren().addAll(dateFilterPane, gridFecha);

            this.contenedor = dateContainer;
            this.inputFechaInicial = fieldDesdeFecha;
            this.inputFechaFinal = fieldHastaFecha;
            this.checkBox = parametroFecha;
            this.gridFechas = gridFecha;
        }

        public FiltroFecha getFiltro() {
            return filtro;
        }

        public TextField getInputFechaFinal() {
            return inputFechaFinal;
        }

        public TextField getInputFechaInicial() {
            return inputFechaInicial;
        }

        public GridPane getGridFechas() {
            return gridFechas;
        }

        public VBox getContenedor() {
            return contenedor;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }
}
