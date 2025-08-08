package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.editarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetos.Usuario;
import com.odvp.biblioteca.servicios.ServicioIconos;
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

import javax.swing.*;
import java.sql.Date;


public class EditarPrestamoVentana extends Stage {
    private Prestamo prestamo;
    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    private Label idLabel, nombreUsuario, tituloLibro;
    private DatePicker fechaPrestamoField, fechaVencimientoField, fechaDevolucionField;
    private TextField idUsuarioField, idLibroField;
    private boolean modificado = false;

    public EditarPrestamoVentana(Prestamo prestamo, PrestamoDAO prestamoDAO, LibroDAO libroDAO, UsuarioDAO usuarioDAO) {
        this.prestamoDAO = prestamoDAO;
        this.prestamo = prestamo;
        this.libroDAO = libroDAO;
        this.usuarioDAO = usuarioDAO;
        Scene scene = buildEscene();
        setTitle("Editar");
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        loadValues();
        showAndWait();
    }

    public boolean fueModificado() {
        return modificado;
    }

    public Prestamo getPrestamoModificado() {
        return prestamo;
    }

    private Scene buildEscene() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Image icon = new Image(ServicioIconos.OPCION_MODULO_PRESTAMOS);
        this.getIcons().add(icon);

        // Título
        Label titleWindow = new Label("Visualizar de Préstamo");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        // GridPane para los datos
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Campos editables
        idLabel = new Label();
        fechaPrestamoField = new DatePicker();
        fechaVencimientoField = new DatePicker();
        fechaDevolucionField = new DatePicker();
        idUsuarioField = new TextField();
        idLibroField = new TextField();
        nombreUsuario = new Label();
        tituloLibro = new Label();

        // Hacer campos no editables según requerimiento
        idUsuarioField.setEditable(false);
        idLibroField.setEditable(false);

        // Agregar elementos al GridPane
        grid.addRow(0, new Label("Código:"), idLabel);
        grid.addRow(1, new Label("Fecha préstamo:"), fechaPrestamoField);
        grid.addRow(2, new Label("Fecha vencimiento:"), fechaVencimientoField);
        grid.addRow(3, new Label("Fecha devolución:"), fechaDevolucionField);
        grid.addRow(4, new Label("Código usuario:"), idUsuarioField);
        grid.addRow(5, new Label("Nombre usuario:"), nombreUsuario);
        grid.addRow(6, new Label("Código libro:"), idLibroField);
        grid.addRow(7, new Label("Título libro:"), tituloLibro);

        // Botones
        Button guardarButton = new Button("Guardar");
        guardarButton.setOnAction(e -> guardarCambios());

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox buttonContainer = new HBox(10, guardarButton, cancelarButton);
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0));

        root.getChildren().addAll(titleContainer, separator, grid, buttonContainer);

        return new Scene(root, 400, 450);
    }

    private void loadValues() {
        Usuario usuario = usuarioDAO.obtener(prestamo.getIdUsuario());
        String nombreCompletoUsuario = usuario.getNombre() + " " +
                usuario.getApellidoPaterno() + " " +
                usuario.getApellidoMaterno();
        String titulo = libroDAO.obtener(prestamo.getIdLibro()).getTitulo();

        idLabel.setText(String.valueOf(prestamo.getId()));
        fechaPrestamoField.setValue(java.time.LocalDate.parse(prestamo.getFecha().toString()));
        fechaVencimientoField.setValue(java.time.LocalDate.parse(prestamo.getFechaVencimiento().toString()));
        if (prestamo.getFechaDevolucion() != null) {
            fechaDevolucionField.setValue(java.time.LocalDate.parse(prestamo.getFechaDevolucion().toString()));
        }
        idUsuarioField.setText(String.valueOf(prestamo.getIdUsuario()));
        nombreUsuario.setText(nombreCompletoUsuario);
        idLibroField.setText(String.valueOf(prestamo.getIdLibro()));
        tituloLibro.setText(titulo);
    }

    private void guardarCambios() {
        try {
            prestamo.setFecha(new Date(java.sql.Date.valueOf(fechaPrestamoField.getValue()).getTime()));
            prestamo.setFechaVencimiento(new Date(java.sql.Date.valueOf(fechaVencimientoField.getValue()).getTime()));
            if (fechaDevolucionField.getValue() != null) {
                prestamo.setFechaDevolucion(new Date(java.sql.Date.valueOf(fechaDevolucionField.getValue()).getTime()));
            }
            prestamoDAO.modificar(prestamo);
            modificado = true;
            JOptionPane.showMessageDialog(null, "Editado");
            close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al guardar los cambios");
            alert.setContentText("Por favor verifique los datos ingresados.");
            alert.showAndWait();
        }
    }
}