package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.eliminarPrestamo;

import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.login.SesionAdministrador;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EliminarPrestamoVentana extends Stage {
    private final int id;
    private final PasswordField passwordField;
    private boolean eliminado = false;
    private AdministradorDAO administradorDAO;

    public EliminarPrestamoVentana(int id) {
        this.id = id;
        this.passwordField = new PasswordField();
        buildStage();
    }

    private void buildStage() {
        setTitle("Confirmar Eliminación de Préstamo");
        VBox root = createMainLayout();
        Scene scene = new Scene(root);

        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private VBox createMainLayout() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(350, 300);

        Image icon = new Image(ServicioIconos.OPCION_MODULO_PRESTAMOS);
        this.getIcons().add(icon);

        Label titleLabel = createTitleLabel();
        Separator separator = createSeparator();
        Label alertaLabel = createAlertLabel();
        Label passwordPromptLabel = createPasswordPromptLabel();
        HBox buttonContainer = createButtonContainer();


        configurePasswordField();

        root.getChildren().addAll(
                titleLabel, separator, alertaLabel,
                passwordPromptLabel, passwordField, buttonContainer
        );

        return root;
    }

    private Label createTitleLabel() {
        Label label = new Label("Eliminar Préstamo");
        label.setFont(Font.font("System", 16));
        label.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        return label;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPrefWidth(250);
        return separator;
    }

    private Label createAlertLabel() {
        Label label = new Label(
                "¿Está seguro que desea eliminar el préstamo con código: " + id + "?"
        );
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(280);
        label.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        return label;
    }

    private Label createPasswordPromptLabel() {
        Label label = new Label("Para continuar, introduzca la clave de seguridad:");
        label.setStyle("-fx-font-style: italic;");
        return label;
    }

    private void configurePasswordField() {
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Clave de seguridad");
        passwordField.setOnAction(e -> handleConfirmAction());
    }

    private HBox createButtonContainer() {
        Button aceptarButton = new Button("Confirmar");
        aceptarButton.setOnAction(e -> handleConfirmAction());

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox container = new HBox(10, cancelarButton, aceptarButton);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private void handleConfirmAction() {
        String administrador = SesionAdministrador.getAdministradorActivo();
        if(administradorDAO.comprobarContrasena(administrador,passwordField.getText())) eliminarPrestamo();
    }


    private boolean eliminarPrestamo() {
        try {
            PrestamoDAO prestamoDAO = new PrestamoDAO();
            prestamoDAO.eliminar(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean fueEliminado() {
        return eliminado;
    }
}