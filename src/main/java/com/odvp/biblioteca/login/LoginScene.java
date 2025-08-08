package com.odvp.biblioteca.login;

import com.odvp.biblioteca.database.daos.AdministradorDAO;
import com.odvp.biblioteca.database.daos.LogDAO;
import com.odvp.biblioteca.main.MainEscena;
import com.odvp.biblioteca.main.ModeloAdministrador;
import com.odvp.biblioteca.main.ModeloMain;
import com.odvp.biblioteca.LibraryApplication;
import com.odvp.biblioteca.objetos.Administrador;
import com.odvp.biblioteca.objetos.Log;
import com.odvp.biblioteca.servicios.ServicioIconos;
import com.odvp.biblioteca.servicios.ServicioSesion;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginScene extends VBox{

    private static Logger log = LogManager.getRootLogger();

    LogDAO logDAO = LogDAO.getInstancia();
    private HBox topBar;
    private StackPane botonAmbiente;
    private ImageView ambienteIcon;
    private Label titleLabel, ambienteLabel;
    private TextField userField;
    private PasswordField passwordField;
    private Button aceptarButton;
    private BooleanProperty estadoAdministrador = new SimpleBooleanProperty(false);


    public LoginScene(){
        init();
    }

    public void init(){
        setPrefHeight(400);
        // Barra superior con icono
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());

        topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10, 10, 0, 10));

        ambienteIcon = new ImageView(ServicioIconos.LOGIN_CONFIGURADOR);
        ambienteIcon.setFitHeight(30);
        ambienteIcon.setFitWidth(30);


        botonAmbiente = new StackPane(ambienteIcon);
        botonAmbiente.setPrefSize(30, 30);
        topBar.getChildren().add(botonAmbiente);

       configurarComportamiento();

        // Contenedor de Login
        VBox loginContainer = new VBox();
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setPrefSize(565, 270);
        loginContainer.setSpacing(10);

        titleLabel = new Label("EBEN-EZER");
        titleLabel.getStyleClass().add("label-title");
        titleLabel.setFont(new Font(13));

        ambienteLabel = new Label("BIBLIOTECA");
        ambienteLabel.getStyleClass().add("label-sub-title");
        ambienteLabel.setFont(new Font(13));

        userField = new TextField();
        userField.setPromptText("Usuario");
        userField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setMaxWidth(200);

        aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction(event -> onAceptarButtonClick());




        // Agregar elementos al contenedor de login
        loginContainer.getChildren().addAll(titleLabel, ambienteLabel, userField, passwordField, aceptarButton);

        // Agregar componentes al root
        getChildren().addAll(topBar, loginContainer);

    }

    private void onAceptarButtonClick() {
       AdministradorDAO administradorDAO = new AdministradorDAO();
        //Authentication line
        if(estadoAdministrador.get()) {
            if(administradorDAO.comprobarContrasena(userField.getText(), passwordField.getText())){
            SesionAdministrador.setAdministradorActivo(userField.getText());
            String mensajeInfo = "Ingreso el administrador: " + userField.getText();
            log.info(mensajeInfo);
            Log logEntrada = new Log("ERROR", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
            logDAO.insertar(logEntrada);
            setConfiguracionAmbiente();
             }
        }else {
            if(administradorDAO.comprobarContrasena(userField.getText(), passwordField.getText())) {
                SesionAdministrador.setAdministradorActivo(userField.getText());
                String mensajeInfo = "Ingreso el administrador: " + userField.getText();
                log.info(mensajeInfo);
                Log logEntrada = new Log("ERROR", mensajeInfo, new java.sql.Date(System.currentTimeMillis()));
                logDAO.insertar(logEntrada);
                setAmbiente();
            }
        }
    }

    private void setAmbiente(){
        Stage stage = (Stage) getScene().getWindow();
        Scene scene = new Scene(new MainEscena(new ModeloMain()));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
      //stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
    private void setConfiguracionAmbiente(){
        Stage stage = (Stage) getScene().getWindow();
        Scene scene = new Scene(new ModeloAdministrador());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        //stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
    private void configurarComportamiento() {
        // Configurar el manejador del clic
        botonAmbiente.setOnMouseClicked(event -> {
            estadoAdministrador.set(!estadoAdministrador.get()); // Alternar estado
        });

        // Vincular el cambio de estado a la actualización del texto
        estadoAdministrador.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ambienteLabel.setText("ADMINISTRADOR");
            } else {
                ambienteLabel.setText("BIBLIOTECA");
            }
        });
    }
}
