package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.visualizarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetos.Usuario;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualizarPrestamoVentana extends Stage {
    private Prestamo prestamo;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    private Label idLabel, fechaPrestamoLabel, fechaVencimientoLabel, fechaDevolucionLabel, idUsuarioLabel, idLibroLabel, estadoLabel, nombreUsuario, tituloLibro;

    public VisualizarPrestamoVentana(Prestamo prestamo, LibroDAO libroDAO, UsuarioDAO usuarioDAO) {
    this.prestamo = prestamo;
    this.libroDAO =libroDAO;
    this.usuarioDAO = usuarioDAO;
    Scene scene = buildEscene();
    setTitle("Visualizar");
    setScene(scene);
    centerOnScreen();
    initModality(Modality.APPLICATION_MODAL);
    loadValues();
    showAndWait();
    }

    private Scene buildEscene() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));


        Image icon = new Image(ServicioIconos.OPCION_MODULO_PRESTAMOS);
        this.getIcons().add(icon);

        Label titleWindow = new Label("Visualizar Prestamo");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        idLabel = new Label();
        fechaPrestamoLabel = new Label();
        fechaVencimientoLabel = new Label();
        fechaDevolucionLabel = new Label();
        estadoLabel = new Label();
        idUsuarioLabel = new Label();
        idLibroLabel = new Label();
        nombreUsuario = new Label();
        tituloLibro = new Label();


        grid.addRow(0, new Label("Codigo:"), idLabel);
        grid.addRow(1, new Label("Fecha prestamo:"), fechaPrestamoLabel);
        grid.addRow(2, new Label("Fecha vencimiento:"), fechaVencimientoLabel);
        grid.addRow(3, new Label("Fecha devolucion:"), fechaDevolucionLabel);
        grid.addRow(4, new Label("Estado:"), estadoLabel);
        grid.addRow(5, new Label("Codigo usuario:"), idUsuarioLabel);
        grid.addRow(6, new Label("Nombre usuario:"), nombreUsuario);
        grid.addRow(7, new Label("Codigo libro:"), idLibroLabel);
        grid.addRow(8, new Label("Titulo libro:"), tituloLibro);


        Button finalizarButton = new Button("Aceptar");
        finalizarButton.setOnAction(e -> close());
        HBox buttonContainer = new HBox(finalizarButton);
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0));


        root.getChildren().addAll(titleContainer, separator, grid, buttonContainer);

        return new Scene(root, 300, 400);
    }

    private void loadValues() {
        Usuario usuario = usuarioDAO.obtener(prestamo.getIdUsuario());
        String nombreCompletoUsuario = usuario.getNombre();
        nombreCompletoUsuario += " ";
        nombreCompletoUsuario +=  usuario.getApellidoPaterno();
        nombreCompletoUsuario += " ";
        nombreCompletoUsuario += usuario.getApellidoMaterno();
        String titulo = libroDAO.obtener(prestamo.getIdLibro()).getTitulo();

        idLabel.setText(String.valueOf(prestamo.getId()));
        fechaPrestamoLabel.setText(String.valueOf(prestamo.getFecha()));
        fechaVencimientoLabel.setText(String.valueOf(prestamo.getFechaVencimiento()));
        fechaDevolucionLabel.setText(String.valueOf(prestamo.getFechaDevolucion()));
        estadoLabel.setText(prestamo.getEstado());
        idUsuarioLabel.setText(String.valueOf(prestamo.getIdUsuario()));
        nombreUsuario.setText(nombreCompletoUsuario);
        idLibroLabel.setText(String.valueOf(prestamo.getIdLibro()));
        tituloLibro.setText(titulo);
    }
}
