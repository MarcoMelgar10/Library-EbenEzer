package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.agregarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Libro;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetos.Usuario;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgregarPrestamoVentana extends Stage {
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private TextField idLibroField, idUsuarioField;
    private Label idLabel, fechaPrestamoLabel, fechaDevolucionLabel, tituloLibroLabel, nombreUsuarioLabel, estado;
    private Button guardarButton, cancelarButton;
    private final HashMap<String, Integer> usuarios = new LinkedHashMap<>();
    private final HashMap<String, Integer> libros = new LinkedHashMap<>();
    private boolean guardado = false;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AgregarPrestamoVentana(LibroDAO libroDAO, UsuarioDAO usuarioDAO) {
        this.libroDAO = libroDAO;
        this.usuarioDAO = usuarioDAO;
        this.prestamoDAO = new PrestamoDAO();

        setTitle("Nuevo Préstamo");
        Scene scene = buildEscene();
        initValues();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private Scene buildEscene() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Image icon = new Image(ServicioIconos.OPCION_MODULO_PRESTAMOS);
        this.getIcons().add(icon);

        Label titleWindow = new Label("Nuevo Préstamo");
        titleWindow.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Separator separator = new Separator();


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        idLabel = new Label();
        fechaPrestamoLabel = new Label(LocalDate.now().toString());
        fechaDevolucionLabel = new Label(LocalDate.now().plusDays(10).toString());
        tituloLibroLabel = new Label("(Ingrese código de libro)");
        nombreUsuarioLabel = new Label("(Ingrese carnet de usuario)");
        estado = new Label("activo");

        idLibroField = new TextField();
        idLibroField.setPromptText("Código del libro");
        idUsuarioField = new TextField();
        idUsuarioField.setPromptText("Código del usuario");

        // Listeners optimizados
        idLibroField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateLibroInfo(newValue);
            } else {
                tituloLibroLabel.setText("(Ingrese código de libro)");
            }
        });

        idUsuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateUsuarioInfo(newValue);
            } else {
                nombreUsuarioLabel.setText("(Ingrese carnet de usuario)");
            }
        });

        // Agregar elementos al GridPane
        grid.addRow(0, new Label("Codigo:"), idLabel);
        grid.addRow(1, new Label("Fecha préstamo:"), fechaPrestamoLabel);
        grid.addRow(2, new Label("Fecha devolución:"), fechaDevolucionLabel);
        grid.addRow(3, new Label("Código libro:"), idLibroField);
        grid.addRow(4, new Label("Título libro:"), tituloLibroLabel);
        grid.addRow(5, new Label("Código usuario:"), idUsuarioField);
        grid.addRow(6, new Label("Nombre usuario:"), nombreUsuarioLabel);
        grid.addRow(7, new Label("Estado:"), estado);

        // Botones
        guardarButton = new Button("Guardar");
        guardarButton.setOnAction(e -> {
            if (validar()) {
                guardarPrestamo();
            } else {
                mostrarAlerta("Error", "Hay datos inválidos", Alert.AlertType.ERROR);
            }
        });

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox buttonBox = new HBox(10, guardarButton, cancelarButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleWindow, separator, grid, buttonBox);
        return new Scene(root, 400, 400);
    }

    private void initValues() {
        executor.execute(() -> {
            try {
                int nextId = prestamoDAO.getNextId();
                Platform.runLater(() -> idLabel.setText(String.valueOf(nextId)));
            } catch (Exception e) {
                Platform.runLater(() -> mostrarAlerta("Error", "Ocurrió un error al obtener el próximo ID: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        });
    }

    private void updateLibroInfo(String idLibroStr) {
        executor.execute(() -> {
            try {
                int idLibro = Integer.parseInt(idLibroStr);
                Libro libro = libroDAO.obtener(idLibro);
                Platform.runLater(() -> {
                    if (libro != null) {
                        tituloLibroLabel.setText(libro.getTitulo());
                        libros.put(libro.getTitulo(), idLibro);
                    } else {
                        tituloLibroLabel.setText("Libro no encontrado");
                    }
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> tituloLibroLabel.setText("ID inválido"));
            } catch (Exception e) {
                Platform.runLater(() -> tituloLibroLabel.setText("Error al buscar libro"));
            }
        });
    }

    private void updateUsuarioInfo(String idUsuarioStr) {
        executor.execute(() -> {
            try {
                int idUsuario = Integer.parseInt(idUsuarioStr);
                Usuario usuario = usuarioDAO.obtener(idUsuario);
                Platform.runLater(() -> {
                    if (usuario != null) {
                        String nombreCompleto = usuario.getNombre() + " " +
                                usuario.getApellidoPaterno() + " " +
                                (usuario.getApellidoMaterno() != null ? usuario.getApellidoMaterno() : "");
                        nombreUsuarioLabel.setText(nombreCompleto);
                        usuarios.put(nombreCompleto, idUsuario);
                    } else {
                        nombreUsuarioLabel.setText("Usuario no encontrado");
                    }
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> nombreUsuarioLabel.setText("ID inválido"));
            } catch (Exception e) {
                Platform.runLater(() -> nombreUsuarioLabel.setText("Error al buscar usuario"));
            }
        });
    }

    private boolean validar() {
        try {
            int idUsuario = Integer.parseInt(idUsuarioField.getText());
            int idLibro = Integer.parseInt(idLibroField.getText());
            return usuarios.containsValue(idUsuario) && libros.containsValue(idLibro);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void guardarPrestamo() {
        try {
            int idLibro = Integer.parseInt(idLibroField.getText());
            int idUsuario = Integer.parseInt(idUsuarioField.getText());

            Prestamo nuevoPrestamo = new Prestamo(
                    Integer.parseInt(idLabel.getText()),
                    Date.valueOf(fechaPrestamoLabel.getText()),
                    Date.valueOf(fechaDevolucionLabel.getText()),
                    null,
                    idUsuario,
                    idLibro,
                    estado.getText()
            );

            prestamoDAO.agregar(nuevoPrestamo);
            guardado = true;
            close();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo guardar el préstamo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public boolean fueGuardado() {
        return guardado;
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