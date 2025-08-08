package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.AgregarUsuario;

import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;
import com.odvp.biblioteca.database.daos.*;

/*
    crea la ventana para agregar un libro
 */

public class AgregarUsuario {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    public AgregarUsuario(ModeloUsuarios modelo){
        AgregarUsuarioVentana agregarLibro = new AgregarUsuarioVentana(usuarioDAO);
        if(agregarLibro.isHubieronCambios()) modelo.anunciarCambio();
    }
}
