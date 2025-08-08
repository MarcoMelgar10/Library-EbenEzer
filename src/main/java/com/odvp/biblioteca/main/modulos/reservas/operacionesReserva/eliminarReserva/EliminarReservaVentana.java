package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.eliminarReserva;

import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.login.LoginScene;
import com.odvp.biblioteca.login.SesionAdministrador;
import com.odvp.biblioteca.objetos.Reserva;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EliminarReservaVentana extends Stage {

    private Button aceptarButton, cancelarButton;
    private PasswordField passwordField;
    private boolean eliminar = false;
    private ReservaDAO reservaDAO;
    private int ID;
    private AdministradorDAO administradorDAO = new AdministradorDAO();
    private LoginScene login = new LoginScene();

    public EliminarReservaVentana(int reservaId, ReservaDAO reservaDAO) {
        setTitle("Eliminar");
        this.reservaDAO = reservaDAO;
        this.ID = reservaId;
        String administrador = SesionAdministrador.getAdministradorActivo();
        Image icon = new Image(ServicioIconos.OPCION_MODULO_RESERVAS);
        this.getIcons().add(icon);
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(332, 290);
        Label titleLabel = new Label("Eliminar Reserva");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 16));
        Separator separator = new Separator();
        separator.setPrefWidth(200);

        Label alertaLabel = new Label();
        alertaLabel.setText("¿Estás seguro que deseas eliminar la reserva con el ID: " + reservaId);
        alertaLabel.setWrapText(true);
        alertaLabel.setAlignment(Pos.CENTER);
        alertaLabel.setPrefWidth(280);
        alertaLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 12));

        Label passwordPromptLabel = new Label("Para continuar introduce la clave de seguridad:");

        passwordField = new PasswordField();
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Clave");

        aceptarButton = new Button("Confirmar");
        aceptarButton.setOnAction(e -> {
            if(administradorDAO.comprobarContrasena(administrador ,passwordField.getText())) ejecutar();
        });

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e -> close());

        HBox buttonContainer = new HBox(cancelarButton, aceptarButton);
        buttonContainer.setSpacing(8);
        buttonContainer.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleLabel, separator, alertaLabel, passwordPromptLabel, passwordField, buttonContainer);

        Scene scene = new Scene(root);
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    public boolean isEliminar() {
        return eliminar;
    }

    private void ejecutar() {
        try {
            reservaDAO.eliminar(ID);
            eliminar = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Reserva eliminada correctamente");
            alert.showAndWait();
            close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo eliminar la reserva: " + e.getMessage());
            alert.showAndWait();
        }
    }
}