package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.EliminarUsuario;

import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.login.SesionAdministrador;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class EliminarUsuarioVentana extends Stage {

    private Button aceptarButton, cancelarButton;
    private PasswordField passwordField;
    private boolean eliminar = false;
    private UsuarioDAO usuarioDAO;
    private int ID;
    private static Logger log = LogManager.getRootLogger();
    private AdministradorDAO administradorDAO = new AdministradorDAO();


    public EliminarUsuarioVentana(int usuarioId, UsuarioDAO usuarioDAO) {
        setTitle("Eliminar Usuario");
        this.usuarioDAO = usuarioDAO;
        this.ID = usuarioId;
        String administrador = SesionAdministrador.getAdministradorActivo();
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(332, 290);

        Image icon = new Image(ServicioIconos.OPCION_MODULO_USUARIOS);
        this.getIcons().add(icon);

        Label titleLabel = new Label("Eliminar Usuario");
        titleLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 16));

        Separator separator = new Separator();
        separator.setPrefWidth(200);

        Label alertaLabel = new Label("Mensaje de alerta");
        alertaLabel.setText("¿Estás seguro que deseas dar de baja el usuario con el ID: " +
                ID + " ? EL usuario dejará de estar disponible para prestamos");
        alertaLabel.setWrapText(true);
        alertaLabel.setAlignment(Pos.CENTER);
        alertaLabel.setPrefWidth(280);
        alertaLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 12));

        Label passwordPromptLabel = new Label("Para continuar introduce la clave de seguridad:");

        passwordField = new PasswordField();
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Clave");


        aceptarButton = new Button("Confirmar");
        aceptarButton.setOnAction(e-> {
            if(administradorDAO.comprobarContrasena(administrador ,passwordField.getText())) ejecutar();
           });

        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(e-> close());

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

    private void ejecutar(){
        usuarioDAO.eliminar(ID);
        eliminar = true;
        close();
    }
}
