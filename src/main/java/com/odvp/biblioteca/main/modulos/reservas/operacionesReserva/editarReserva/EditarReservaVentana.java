package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.editarReserva;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Reserva;
import com.odvp.biblioteca.objetos.Usuario;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditarReservaVentana extends Stage {
    private final ReservaDAO reservaDAO;
    private  UsuarioDAO usuarioDAO;
    private LibroDAO libroDAO;
    private final Reserva reserva;
    private Label idReservaLabel, tituloLibro, nombreUsuario;
    private TextField usuarioTextField, libroTextField;
    private ComboBox<String> estadoReserva;
    private TextArea observacionTextArea;
    private DatePicker fechaReservaPicker, fechaVencimientoPicker, fechaRecogidaPicker;
    private Button cancelarButton, guardarButton;
    private boolean huboCambios;
    private final HashMap<String, Integer> usuarios = new LinkedHashMap<>();
    private final HashMap<String, Integer> libros = new LinkedHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public EditarReservaVentana(ReservaDAO reservaDAO, Reserva reserva) {
        this.reservaDAO = reservaDAO;
        this.reserva = reserva;
        setTitle("Editar Reserva");
        Scene scene = buildScene();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private Scene buildScene() {
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        Image icon = new Image(ServicioIconos.OPCION_MODULO_RESERVAS);
        this.getIcons().add(icon);

        Label titleWindow = new Label("Editar Reserva");
        titleWindow.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        idReservaLabel = new Label(String.valueOf(reserva.getIdReserva()));
        usuarioDAO = new UsuarioDAO();
        libroDAO = new LibroDAO();
        String usuarioId = String.valueOf(reserva.getIdUsuario());
        usuarioTextField = new TextField(usuarioId);
        usuarioTextField.setDisable(false);
        String libroId = String.valueOf(reserva.getIdLibro());
        libroTextField = new TextField(libroId);
        libroTextField.setDisable(false);
        libroTextField.setDisable(false);

        libroTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    updateLibroInfo(newValue);
                } else {
                    tituloLibro.setText("");
                }
            }
        });
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

        fechaReservaPicker = new DatePicker();
        LocalDate fechaReserva = reserva.getFechaReserva() != null
                ? LocalDate.of(
                reserva.getFechaReserva().getYear() + 1900,  // java.sql.Date usa año desde 1900
                reserva.getFechaReserva().getMonth() + 1,
                reserva.getFechaReserva().getDate()
        )
                : null;

        LocalDate fechaVencimiento = reserva.getFechaVencimiento() != null
                ? LocalDate.of(
                reserva.getFechaVencimiento().getYear() + 1900,
                reserva.getFechaVencimiento().getMonth() + 1,
                reserva.getFechaVencimiento().getDate()
        )
                : null;

        LocalDate fechaRecogida = reserva.getFechaRecogida() != null
                ? LocalDate.of(
                reserva.getFechaRecogida().getYear() + 1900,
                reserva.getFechaRecogida().getMonth() + 1,
                reserva.getFechaRecogida().getDate()
        )
                : null;

        fechaReservaPicker.setValue(fechaReserva);
        fechaVencimientoPicker = new DatePicker(fechaVencimiento);
        fechaRecogidaPicker = new DatePicker(fechaRecogida);
        estadoReserva = new ComboBox<>();
        estadoReserva.setPromptText(reserva.getEstado());
        estadoReserva.getItems().addAll("confirmado","pendiente");
        observacionTextArea = new TextArea(reserva.getObservacion());
        observacionTextArea.setWrapText(true);
        observacionTextArea.setFont(Font.font("System", javafx.scene.text.FontPosture.ITALIC, 12));
        ScrollPane scrollPane = new ScrollPane(observacionTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);
        Usuario usuario = usuarioDAO.obtener(reserva.getIdUsuario());
        nombreUsuario = new Label(usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno());
        tituloLibro = new Label(libroDAO.obtener(reserva.getIdLibro()).getTitulo());

        formGrid.addRow(0, new Label("ID Reserva:"), idReservaLabel);
        formGrid.addRow(1, new Label("ID Usuario:"), usuarioTextField);
        formGrid.addRow(2, new Label("Nombre usuario:"), nombreUsuario);
        formGrid.addRow(3, new Label("ID Libro:"), libroTextField);
        formGrid.addRow(4, new Label("Titulo Libro:"), tituloLibro);
        formGrid.addRow(5, new Label("Fecha Reserva:"), fechaReservaPicker);
        formGrid.addRow(6, new Label("Fecha Vencimiento:"), fechaVencimientoPicker);
        formGrid.addRow(7, new Label("Fecha Recogida:"), fechaRecogidaPicker);
        formGrid.addRow(8, new Label("Estado:"), estadoReserva);
        formGrid.addRow(9, new Label("Observaciones:"));

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        guardarButton = new Button("Guardar");
        guardarButton.setOnAction(e -> guardarCambios());
        HBox buttonsContainer = new HBox(8, cancelarButton, guardarButton);
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(titleContainer, separator, formGrid, scrollPane, buttonsContainer);
        return new Scene(root, 400, 550);
    }

    private void guardarCambios() {
        try {
            reserva.setFechaReserva(java.sql.Date.valueOf(fechaReservaPicker.getValue()));
            reserva.setFechaVencimiento(java.sql.Date.valueOf(fechaVencimientoPicker.getValue()));
            reserva.setFechaRecogida(fechaRecogidaPicker.getValue() != null ? java.sql.Date.valueOf(fechaRecogidaPicker.getValue()) : null);
            reserva.setEstado(estadoReserva.getValue());
            reserva.setObservacion(observacionTextArea.getText());
            reservaDAO.actualizar(reserva);
            huboCambios = true;
            close();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public boolean huboCambios() {
        return huboCambios;
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
}