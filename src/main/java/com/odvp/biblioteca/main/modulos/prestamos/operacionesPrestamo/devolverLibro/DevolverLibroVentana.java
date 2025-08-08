package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.devolverLibro;

import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

public class DevolverLibroVentana extends Stage {
    private boolean hubieronCambios;
    private Prestamo prestamo;
    private Label idPrestamo, idUsuario, nombreUsuario, idLibro, libroTitulo;
    private Button aceptar, cancelar;
    private PrestamoDAO prestamoDAO;
    public DevolverLibroVentana (Prestamo prestamo){
        this.prestamo = prestamo;
        prestamoDAO = new PrestamoDAO();
        buildStage();
    }

    private void buildStage() {
        setTitle("Devolver libro");
        VBox root = createMainLayout();
        setScene(new Scene(root));
        showAndWait();
    }

    private VBox createMainLayout() {
        VBox root = new VBox(20);
        aceptar = new Button("Aceptar");
        cancelar = new Button("Cancelar");

        Label labelIdPrestamo = new Label("Prestamo Id:");
        idPrestamo = new Label(String.valueOf(prestamo.getId()));

        Label labelIdUsuario = new Label("Usuario Id:");
        idUsuario = new Label(String.valueOf(prestamo.getIdUsuario()));

        Label labelNombreUsuaio = new Label("Usuario");
        nombreUsuario = new Label(prestamoDAO.getUsuario(prestamo.getId()));

        Label labelIdLibro = new Label("Libro Id:");
        idLibro = new Label(String.valueOf(prestamo.getIdLibro()));

        Label labelTituloLibro = new Label("Titulo: ");
        libroTitulo = new Label(prestamoDAO.getLibro(prestamo.getId()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Image icon = new Image(ServicioIconos.OPCION_MODULO_PRESTAMOS);
        this.getIcons().add(icon);


        Label titleWindow = new Label("Devolver libro");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Button guardarButton = new Button("Confirmar");
        guardarButton.setOnAction(e -> guardarCambios());

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox buttonContainer = new HBox(10, guardarButton, cancelarButton);
        buttonContainer.setAlignment(Pos.CENTER);

        grid.addRow(0, labelIdPrestamo , idPrestamo);
        grid.addRow(1, labelIdUsuario, idUsuario);
        grid.addRow(2, labelNombreUsuaio, nombreUsuario);
        grid.addRow(3, labelIdLibro, idLibro);
        grid.addRow(4, labelTituloLibro, libroTitulo);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(250, 200);
        Separator separator = new Separator();
        separator.setPrefWidth(250);
        root.getChildren().addAll(titleContainer, separator, grid, buttonContainer);

        return root;
    }

    private void guardarCambios() {
        try {
            prestamoDAO.realizarDevolucion(prestamo.getId());
            JOptionPane.showMessageDialog(null, "Devolución realizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            hubieronCambios = true;
            close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la devolución:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public boolean getHubieronCambios(){
        return hubieronCambios;
    }
}
