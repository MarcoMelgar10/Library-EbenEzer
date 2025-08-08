package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.EliminarLibro;

import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.login.SesionAdministrador;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class EliminarLibroVentana extends Stage {

    private Button aceptarButton, cancelarButton;
    private PasswordField passwordField;
    private boolean eliminar = false;
    private LibroDAO libroDAO;
    private int ID;
    private static Logger log = LogManager.getRootLogger();
    private AdministradorDAO administradorDAO = new AdministradorDAO();

    public EliminarLibroVentana(int libroId, LibroDAO libroDAO) {
        setTitle("Eliminar");
        this.libroDAO = libroDAO;
        this.ID = libroId;
        String administrador = SesionAdministrador.getAdministradorActivo();
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setPrefSize(332, 290);

        Label titleLabel = new Label("Eliminar libro");
        titleLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 16));

        Image icon = new Image(ServicioIconos.OPCION_MODULO_LIBROS);
        this.getIcons().add(icon);

        Separator separator = new Separator();
        separator.setPrefWidth(200);

        Label alertaLabel = new Label("Mensaje de alerta");
        alertaLabel.setText("¿Estás seguro que deseas dar de baja el libro con el ID: " +
                libroId + " ? EL libro dejará de estar disponible para prestamos");
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
            if(administradorDAO.comprobarContrasena(administrador,passwordField.getText())) ejecutar();
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
        libroDAO.eliminar(ID);
        eliminar = true;
        close();
    }
}
