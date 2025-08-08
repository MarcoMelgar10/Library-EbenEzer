package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Agregar;

import com.odvp.biblioteca.objetos.Multa;
import com.odvp.biblioteca.servicios.ServicioIconos;
import com.odvp.biblioteca.database.daos.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class AgregarMultaVentana extends Stage {

    private Spinner<Integer> montoSpinner, codigoPrestamoSpinner;
    private TextField descripcionField;
    private Label codigoLabel, usuarioLabel, libroLabel;
    private Button cancelarButton, aceptarButton;
    private Multa multa;
    private final MultaDAO multaDao;
    private int nextID;
    private   PrestamoDAO prestamoDAO;
    private static Logger log = LogManager.getRootLogger();

    private boolean hubieronCambios = false;

    public AgregarMultaVentana(MultaDAO multaDAO) {
        this.multaDao = multaDAO;
        prestamoDAO = new PrestamoDAO();
        setTitle("Agregar multa");
        Scene scene = buildScene();
        Image icon = new Image(ServicioIconos.OPCION_MODULO_MULTAS);
        this.getIcons().add(icon);
        initValues();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    public Scene buildScene(){
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        // Título
        Label titleWindow = new Label("Agregar multa");
        titleWindow.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 22));
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        // GridPane para los campos del formulario
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label codigoLabel = new Label("Codigo:");
        this.codigoLabel = new Label("");
        this.codigoLabel.setPrefWidth(50);

        Label descripcionLabel = new Label("Descripcion:");
        descripcionField = new TextField();

        Label codigoPrestamoLabel = new Label("Codigo Prestamo:");
        codigoPrestamoSpinner = new Spinner<>(1, 1000, 1);
        codigoPrestamoSpinner.setEditable(true);
        codigoPrestamoSpinner.setPrefWidth(70);
        codigoPrestamoSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Permite solo números
                codigoPrestamoSpinner.getEditor().setText(oldValue);
            }
        });

        Label nombreUsuario = new Label("Usuario:");
        usuarioLabel = new Label("");
        usuarioLabel.setPrefWidth(100);


        Label nombreLibro = new Label("Libro:");
        libroLabel = new Label("");
        libroLabel.setPrefWidth(200);

        Label montoLabel = new Label("Monto:");
        montoSpinner = new Spinner<>(1, 1000, 1);
        montoSpinner.setEditable(true);
        montoSpinner.setPrefWidth(70);


        montoSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Permite solo números
                montoSpinner.getEditor().setText(oldValue);
            }
        });

        formGrid.addRow(0, codigoLabel, this.codigoLabel);
        formGrid.addRow(1, descripcionLabel,descripcionField);
        formGrid.addRow(2, codigoPrestamoLabel, codigoPrestamoSpinner);
        formGrid.addRow(3, nombreUsuario, usuarioLabel);
        formGrid.addRow(4, nombreLibro, libroLabel);
        formGrid.addRow(5, montoLabel, montoSpinner);

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction( e -> close());
        aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction( e-> {
            if(validar()) ejecutar();
            else log.info("Hay datos invalidos");
            JOptionPane.showMessageDialog(null, "Hay datos invalidos" , "Verificacion", JOptionPane.INFORMATION_MESSAGE);
        });
        HBox buttonsContainer = new HBox(10, cancelarButton, aceptarButton);
        VBox.setMargin(buttonsContainer, new Insets(30, 0, 0, 0));
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(titleContainer, separator, formGrid, buttonsContainer);

        return new Scene(root, 350, 350);
    }

    public void initValues(){
        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                 nextID = multaDao.getNextId();

                Platform.runLater(() -> codigoLabel.setText(String.valueOf(nextID)));

                codigoPrestamoSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        String usuarioName = prestamoDAO.getUsuario(newValue.intValue());
                        String tituloLibro = prestamoDAO.getLibro(newValue.intValue());
                        Platform.runLater(() -> usuarioLabel.setText(usuarioName));
                        Platform.runLater(() -> libroLabel.setText(tituloLibro));
                    }
                });

        return null;
            }
        }).start();
    }

    public boolean validar() {
        boolean idPrestamoValido = codigoPrestamoSpinner.getValue() != null;
        boolean montoValidar = montoSpinner.getValue() != null;
        return idPrestamoValido && montoValidar;
    }

    public void ejecutar(){
        int idPrestamo = codigoPrestamoSpinner.getValue();
        int monto = montoSpinner.getValue();
        String descripcion = descripcionField.getText().isEmpty() ? "" : descripcionField.getText();
        multa = new Multa(nextID, descripcion, monto, null, true, null, true,  idPrestamo);
        multaDao.insertar(multa);
        hubieronCambios = true;
        close();
    }

    public boolean isHubieronCambios() {
        return hubieronCambios;
    }
}


