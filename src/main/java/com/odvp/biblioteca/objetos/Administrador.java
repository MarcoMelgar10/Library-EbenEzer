package com.odvp.biblioteca.objetos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Administrador {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty usuario;
    private final SimpleStringProperty contrasena;
    private final SimpleStringProperty fechaCreacion;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Administrador(int id, String usuario, String contrasena) {
        this.id = new SimpleIntegerProperty(id);
        this.usuario = new SimpleStringProperty(usuario);
        this.contrasena = new SimpleStringProperty(contrasena);
        this.fechaCreacion = new SimpleStringProperty();
    }
    public Administrador(int id, String usuario, String contrasena, Date date) {
        this.id = new SimpleIntegerProperty(id);
        this.usuario = new SimpleStringProperty(usuario);
        this.contrasena = new SimpleStringProperty(contrasena);
        fechaCreacion = new SimpleStringProperty(date != null ? dateFormat.format(date) : "Fecha no disponible");
    }

    // Getters y propiedades para JavaFX
    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getUsuario() { return usuario.get(); }
    public SimpleStringProperty usuarioProperty() { return usuario; }
    public void setUsuario(String usuario) { this.usuario.set(usuario); }

    public String getContrasena() { return contrasena.get(); }
    public SimpleStringProperty contrasenaProperty() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena.set(contrasena); }

    public String getFechaCreacion() { return fechaCreacion.get(); }
    public SimpleStringProperty fechaCreacionProperty() { return fechaCreacion; }
}