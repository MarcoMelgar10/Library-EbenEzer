package com.odvp.biblioteca.main;
import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.objetos.Administrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModeloAdministrador extends BorderPane {

    private static final Logger log = LogManager.getLogger(ModeloAdministrador.class);
    private VBox panelOpciones;
    private TableView<Administrador> tablaAdministradores;
    private TextField campoUsuario, campoContrasena;
    private PasswordField campoContrasenaConfirmacion;
    private Button btnAgregar, btnModificar, btnEliminar;
    private Label lblTitulo;
    private AdministradorDAO administradorDAO = new AdministradorDAO();

    public ModeloAdministrador() {
        init();
        cargarDatos();
    }

    private void init() {
        this.setPrefSize(1080, 720);
        crearPanelLateral();
        crearPanelCentral();
        crearPanelSuperior();
    }

    private void crearPanelLateral() {
        panelOpciones = new VBox(15);
        panelOpciones.setPrefWidth(200);
        panelOpciones.setPadding(new Insets(20));
        panelOpciones.setStyle("-fx-background-color: #2c3e50;");

        lblTitulo = new Label("Opciones");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblTitulo.setTextFill(Color.WHITE);

        btnAgregar = crearBotonOpcion("Agregar Administrador");
        btnModificar = crearBotonOpcion("Modificar Administrador");
        btnEliminar = crearBotonOpcion("Eliminar Administrador");

        panelOpciones.getChildren().addAll(lblTitulo, btnAgregar, btnModificar, btnEliminar);
        this.setLeft(panelOpciones);

        btnAgregar.setOnAction(e -> mostrarFormularioAgregar());
        btnModificar.setOnAction(e -> mostrarFormularioModificar());
        btnEliminar.setOnAction(e -> eliminarAdministrador());
    }

    private Button crearBotonOpcion(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setPadding(new Insets(10));
        return btn;
    }

    private void crearPanelCentral() {
        VBox panelCentral = new VBox(20);
        panelCentral.setPadding(new Insets(20));

        // Crear tabla de administradores
        tablaAdministradores = new TableView<>();

        TableColumn<Administrador, Integer> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Administrador, String> columnaUsuario = new TableColumn<>("Usuario");
        columnaUsuario.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());

        TableColumn<Administrador, String> columnaFecha = new TableColumn<>("Fecha Creación");
        columnaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaCreacionProperty());

        tablaAdministradores.getColumns().addAll(columnaId, columnaUsuario, columnaFecha);
        tablaAdministradores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        panelCentral.getChildren().add(tablaAdministradores);
        this.setCenter(panelCentral);
    }

    private void crearPanelSuperior() {
        HBox panelSuperior = new HBox();
        panelSuperior.setPadding(new Insets(15));
        panelSuperior.setStyle("-fx-background-color: #34495e;");

        Label lblTitulo = new Label("Gestión de Administradores");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTitulo.setTextFill(Color.WHITE);

        panelSuperior.getChildren().add(lblTitulo);
        this.setTop(panelSuperior);
    }

    private void mostrarFormularioAgregar() {
        Dialog<Administrador> dialog = new Dialog<>();
        dialog.setTitle("Agregar Nuevo Administrador");

        // Crear campos del formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        campoUsuario = new TextField();
        campoUsuario.setPromptText("Nombre de usuario");
        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Contraseña");
        campoContrasenaConfirmacion = new PasswordField();
        campoContrasenaConfirmacion.setPromptText("Confirmar contraseña");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(campoUsuario, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(campoContrasena, 1, 1);
        grid.add(new Label("Confirmar:"), 0, 2);
        grid.add(campoContrasenaConfirmacion, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Agregar botones
        ButtonType btnAgregar = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAgregar, ButtonType.CANCEL);

        // Validar y procesar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAgregar) {
                if (validarFormulario()) {
                    administradorDAO.insertar(new Administrador(
                            0,
                            campoUsuario.getText(),
                            campoContrasena.getText()));
                }
                return null;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(admin -> {
            log.info("Administrador a agregar...");
            cargarDatos();
        });
    }

    private void mostrarFormularioModificar() {
        Administrador seleccionado = tablaAdministradores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Por favor seleccione un administrador para modificar.");
            return;
        }

        Dialog<Administrador> dialog = new Dialog<>();
        dialog.setTitle("Modificar Administrador");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        campoUsuario = new TextField(seleccionado.getUsuario());
        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Nueva contraseña (dejar vacío para no cambiar)");
        campoContrasenaConfirmacion = new PasswordField();
        campoContrasenaConfirmacion.setPromptText("Confirmar nueva contraseña");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(campoUsuario, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(campoContrasena, 1, 1);
        grid.add(new Label("Confirmar:"), 0, 2);
        grid.add(campoContrasenaConfirmacion, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType btnModificar = new ButtonType("Modificar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnModificar, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnModificar) {
                if (validarFormularioModificacion()) {

                    if (!campoContrasena.getText().isEmpty()) {
                        seleccionado.setContrasena(campoContrasena.getText()); // Encriptar en producción
                    }
                    return seleccionado;
                }
                return null;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(admin -> {
           log.info("Administrador a agregar...");
            cargarDatos();
        });
    }

    private void eliminarAdministrador() {
        Administrador seleccionado = tablaAdministradores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selección requerida", "Por favor seleccione un administrador para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar al administrador " + seleccionado.getUsuario() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                administradorDAO.eliminar(seleccionado.getId());
                cargarDatos();
            }
        });
    }

    private boolean validarFormulario() {
        if (campoUsuario.getText().isEmpty() || campoContrasena.getText().isEmpty() || campoContrasenaConfirmacion.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Todos los campos son obligatorios.");
            return false;
        }

        if (!campoContrasena.getText().equals(campoContrasenaConfirmacion.getText())) {
            mostrarAlerta("Contraseñas no coinciden", "Las contraseñas ingresadas no coinciden.");
            return false;
        }

        if (campoContrasena.getText().length() < 8) {
            mostrarAlerta("Contraseña insegura", "La contraseña debe tener al menos 8 caracteres.");
            return false;
        }

        return true;
    }

    private boolean validarFormularioModificacion() {
        if (campoUsuario.getText().isEmpty()) {
            mostrarAlerta("Campo vacío", "El nombre de usuario es obligatorio.");
            return false;
        }

        if (!campoContrasena.getText().isEmpty() && !campoContrasena.getText().equals(campoContrasenaConfirmacion.getText())) {
            mostrarAlerta("Contraseñas no coinciden", "Las contraseñas ingresadas no coinciden.");
            return false;
        }

        if (!campoContrasena.getText().isEmpty() && campoContrasena.getText().length() < 8) {
            mostrarAlerta("Contraseña insegura", "La contraseña debe tener al menos 8 caracteres.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarDatos() {
        AdministradorDAO administradorDAO = new AdministradorDAO();
        ObservableList<Administrador> administradores = FXCollections.observableArrayList(
           administradorDAO.listaAdministradores()
        );
        tablaAdministradores.setItems(administradores);

    }
}
