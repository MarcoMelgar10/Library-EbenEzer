package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.CancelarMulta;

import com.odvp.biblioteca.objetosVisuales.MultaCardData;
import com.odvp.biblioteca.database.daos.MultaDAO;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
public class CancelarMultaVentana extends Stage {
    public boolean isHubieronCambios() {
        return hubieronCambios;
    }
    private boolean hubieronCambios;
    private MultaDAO multaDAO;
    private MultaCardData multaCardData;

    public CancelarMultaVentana(MultaDAO multaDAO, MultaCardData multaCardData) {
        this.multaDAO = multaDAO;
        this.multaCardData = multaCardData;
        buildStage();
    }

    private void buildStage() {
        setTitle("Confirmar Cancelación");
        initModality(Modality.APPLICATION_MODAL);
        Image icon = new Image(ServicioIconos.OPCION_MODULO_MULTAS);
        this.getIcons().add(icon);
        setResizable(false);
        VBox root = createMainLayout();
        setScene(new Scene(root));
        showAndWait();
    }

    private VBox createMainLayout() {
        VBox root = new VBox(20);

        Label titleWindow = new Label("Cancelar Multa");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Label labelId = new Label("ID:");
        String id = String.valueOf(multaCardData.getID());
        Label lblId = new Label(id);

        Label labelMonto = new Label("Monto:");
        Label lblMonto = new Label(multaCardData.getMonto() + "Bs.");

        Label labelUsuario = new Label("Usuario:");
        Label lblUsuario = new Label(multaCardData.getNombreUsuario());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, labelId, lblId);
        grid.addRow(1, labelMonto, lblMonto);
        grid.addRow(2, labelUsuario, lblUsuario);

        Button confirmarButton = new Button("Confirmar");
        confirmarButton.setOnAction(e -> {
            multaDAO.cancelarMulta(multaCardData.getID());
            hubieronCambios = true;
            close();
        });

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox buttonContainer = new HBox(10, confirmarButton, cancelarButton);
        buttonContainer.setAlignment(Pos.CENTER);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(250, 200);
        Separator separator = new Separator();
        separator.setPrefWidth(250);
        root.getChildren().addAll(titleContainer, separator, grid, buttonContainer);

        return root;
    }
}
