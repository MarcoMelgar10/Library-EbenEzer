package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Visualizar;

import com.odvp.biblioteca.objetos.Multa;
import com.odvp.biblioteca.servicios.ServicioIconos;
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

import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualizarMultaVentana extends Stage {
    private Label idLabel, descripcionLabel, montoLabel, fechaMultaLabel, estadoLabel, fechaCancelacionLabel, idPrestamoLabel;
    private Button finalizarButton;
    private final Multa multa;

    public VisualizarMultaVentana(Multa multa) {
        this.multa = multa;
        setTitle("Visualizar");
        Scene scene = buildScene();
        Image icon = new Image(ServicioIconos.OPCION_MODULO_MULTAS);
        this.getIcons().add(icon);
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        initValues();
        showAndWait();
    }

    private Scene buildScene() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        // Título
        Label titleWindow = new Label("Visualizar de Multa");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        // GridPane para los datos
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Etiquetas
        idLabel = new Label();
        descripcionLabel = new Label();
        montoLabel = new Label();
        fechaMultaLabel = new Label();
        estadoLabel = new Label();
        fechaCancelacionLabel = new Label();
        idPrestamoLabel = new Label();

        // Agregar elementos al GridPane
        grid.addRow(0, new Label("ID:"), idLabel);
        grid.addRow(1, new Label("Descripción:"), descripcionLabel);
        grid.addRow(2, new Label("Monto:"), montoLabel);
        grid.addRow(3, new Label("Fecha Multa:"), fechaMultaLabel);
        grid.addRow(4, new Label("Estado:"), estadoLabel);
        grid.addRow(5, new Label("Fecha Cancelación:"), fechaCancelacionLabel);
        grid.addRow(6, new Label("ID Préstamo:"), idPrestamoLabel);

        // Botón finalizar
        finalizarButton = new Button("Aceptar");
        finalizarButton.setOnAction(e -> close());
        HBox buttonContainer = new HBox(finalizarButton);
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0));

        // Agregar elementos al VBox principal
        root.getChildren().addAll(titleContainer, separator, grid, buttonContainer);

        return new Scene(root, 300, 350);
    }

    private void initValues() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    idLabel.setText(String.valueOf(multa.getIdMulta()));
                    descripcionLabel.setText(multa.getDescripcion());
                    montoLabel.setText(String.valueOf(multa.getMonto()));
                    fechaMultaLabel.setText(multa.getFechaMulta().toLocalDate().toString());
                    estadoLabel.setText(multa.isEstado() ? "Activa" : "Cancelada");
                    fechaCancelacionLabel.setText(multa.getFechaCancelacion() != null ? multa.getFechaCancelacion().toLocalDate().toString() : "N/A");
                    idPrestamoLabel.setText(String.valueOf(multa.getIdPrestamo()));
                });
                return null;
            }
        };
        new Thread(task).start();
    }
}
