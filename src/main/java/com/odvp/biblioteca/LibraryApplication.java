package com.odvp.biblioteca;
import com.odvp.biblioteca.database.daos.MultaDAO;
import com.odvp.biblioteca.login.LoginScene;
import com.odvp.biblioteca.database.ConexionDB;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
    realiza el arranque de la app y carga la ventana con el loggin.
 */

public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        new Thread(ConexionDB::getOrCreate).start();
        MultaDAO multaDAO = new MultaDAO();
        multaDAO.validarPrestamosVencidos();
        Scene scene = new Scene(new LoginScene());
        stage.setResizable(false);
        Image icon = new Image(ServicioIconos.ICONO_BIBLIOTECA);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}