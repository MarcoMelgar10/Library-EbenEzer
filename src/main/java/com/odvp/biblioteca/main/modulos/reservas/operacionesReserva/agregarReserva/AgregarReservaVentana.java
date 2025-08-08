package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.agregarReserva;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Reserva;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgregarReservaVentana extends Stage {
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final ReservaDAO reservaDAO;
    private TextField usuarioTextField, idLibroTextField;
    private TextArea observacionTextArea;
    private Label fechaReservaPicker, fechaVencimientoPicker;
    private Button cancelarButton, aceptarButton;
    private Label idReservaLabel, nombreUsuario, tituloLibro;
    private final HashMap<String, Integer> usuarios = new LinkedHashMap<>();
    private final HashMap<String, Integer> libros = new LinkedHashMap<>();
    private boolean hubieronCambios = false;
    private int nextID;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AgregarReservaVentana(ReservaDAO reservaDAO, UsuarioDAO usuarioDAO, LibroDAO libroDAO) {
        this.reservaDAO = reservaDAO;
        this.usuarioDAO = usuarioDAO;
        this.libroDAO = libroDAO;
        setTitle("Agregar");
        Scene scene = buildScene();
        initValues();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    public Scene buildScene() {
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        Image icon = new Image(ServicioIconos.OPCION_MODULO_RESERVAS);
        this.getIcons().add(icon);

        Label titleWindow = new Label("Reservar libro");
        titleWindow.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label idLabel = new Label("ID Reserva:");
        idReservaLabel = new Label("");
        idReservaLabel.setPrefWidth(50);

        Label usuarioLabel = new Label("Carnet usuario:");
        usuarioTextField = new TextField();
        usuarioTextField.setPrefWidth(150);
        // Add listener for real-time updates
        usuarioTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    updateUsuarioInfo(newValue);
                } else {
                    nombreUsuario.setText("");
                }
            }
        });

        Label usuarioNombreLabel = new Label("Nombre usuario:");
        usuarioNombreLabel.setPrefWidth(100);
        nombreUsuario = new Label();
        nombreUsuario.setPrefWidth(200);

        Label libroLabel = new Label("Id libro:");
        idLibroTextField = new TextField();
        idLibroTextField.setPrefWidth(150);
        // Add listener for real-time updates
        idLibroTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    updateLibroInfo(newValue);
                } else {
                    tituloLibro.setText("");
                }
            }
        });

        Label libroTituloLabel = new Label("Titulo:");
        libroTituloLabel.setPrefWidth(100);
        tituloLibro = new Label();
        tituloLibro.setPrefWidth(200);

        Label fechaReservaLabel = new Label("Fecha de reserva:");
        fechaReservaPicker = new Label(LocalDate.now().toString());
        fechaReservaPicker.setPrefWidth(125);



        Label fechaVencimientoLabel = new Label("Fecha de vencimiento:");
        fechaVencimientoPicker = new Label(LocalDate.now().plusDays(10).toString());
        fechaVencimientoPicker.setPrefWidth(125);


        Label observacionLabel = new Label("Observacion:");
        observacionTextArea = new TextArea();
        observacionTextArea.setPromptText("Escriba alguna observacion...");
        observacionTextArea.setWrapText(true);
        observacionTextArea.setFont(Font.font("System", javafx.scene.text.FontPosture.ITALIC, 12));
        ScrollPane scrollPane = new ScrollPane(observacionTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);

        formGrid.addRow(0, idLabel, idReservaLabel);
        formGrid.addRow(1, usuarioLabel, usuarioTextField);
        formGrid.addRow(2, usuarioNombreLabel, nombreUsuario);
        formGrid.addRow(3, libroLabel, idLibroTextField);
        formGrid.addRow(4, libroTituloLabel, tituloLibro);
        formGrid.addRow(5, fechaReservaLabel, fechaReservaPicker);
        formGrid.addRow(6, fechaVencimientoLabel, fechaVencimientoPicker);
        formGrid.addRow(7, observacionLabel);

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> {
            executor.shutdown();
            close();
        });

        aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction(e -> {
            if (validar()) ejecutar();
            else mostrarAlerta("Error", "Hay datos inválidos", Alert.AlertType.ERROR);
        });

        HBox buttonsContainer = new HBox(8, cancelarButton, aceptarButton);
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);
        root.getChildren().addAll(titleContainer, separator, formGrid, scrollPane, buttonsContainer);

        return new Scene(root, 400, 550);
    }

    public void initValues() {
        executor.execute(() -> {
            try {
                nextID = reservaDAO.getNextId();
                Platform.runLater(() -> idReservaLabel.setText(String.valueOf(nextID)));
            } catch (Exception e) {
                Platform.runLater(() -> mostrarAlerta("Error", "Ocurrió un error al obtener el próximo ID: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        });
    }

    private void updateUsuarioInfo(String idUsuarioStr) {
        executor.execute(() -> {
            try {
                int idUsuario = Integer.parseInt(idUsuarioStr);
                String nombre = usuarioDAO.obtener(idUsuario).getNombre();
                Platform.runLater(() -> {
                    if (nombre != null && !nombre.isEmpty()) {
                        nombreUsuario.setText(nombre);
                        usuarios.put(nombre, idUsuario);
                    } else {
                        nombreUsuario.setText("Usuario no encontrado");
                    }
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> nombreUsuario.setText("ID inválido"));
            } catch (Exception e) {
                Platform.runLater(() -> nombreUsuario.setText(""));
            }
        });
    }

    private void updateLibroInfo(String idLibroStr) {
        executor.execute(() -> {
            try {
                int idLibro = Integer.parseInt(idLibroStr);
                String titulo = libroDAO.obtener(idLibro).getTitulo();
                Platform.runLater(() -> {
                    if (titulo != null && !titulo.isEmpty()) {
                        tituloLibro.setText(titulo);
                        libros.put(titulo, idLibro);
                    } else {
                        tituloLibro.setText("Libro no encontrado");
                    }
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> tituloLibro.setText("ID inválido"));
            } catch (Exception e) {
                Platform.runLater(() -> tituloLibro.setText(""));
            }
        });
    }

    public boolean validar() {
        try {
            int idUsuario = Integer.parseInt(usuarioTextField.getText());
            int idLibro = Integer.parseInt(idLibroTextField.getText());
            return usuarios.containsValue(idUsuario) && libros.containsValue(idLibro);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void ejecutar() {
        try {
            int idUsuario = Integer.parseInt(usuarioTextField.getText());
            int idLibro = Integer.parseInt(idLibroTextField.getText());
            Date fechaReserva = Date.valueOf(fechaReservaPicker.getText());
            Date fechaDevolucion = Date.valueOf(fechaVencimientoPicker.getText());
            Reserva reserva = new Reserva(nextID, idUsuario, idLibro, fechaReserva, fechaDevolucion, null, "pendiente", observacionTextArea.getText());
            reservaDAO.insertar(reserva);
            hubieronCambios = true;
            executor.shutdown();
            close();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo realizar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public boolean isHubieronCambios() {
        return hubieronCambios;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void close() {
        executor.shutdown();
        super.close();
    }
}