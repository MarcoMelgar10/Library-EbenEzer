package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.EliminarUsuario;

import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;
import com.odvp.biblioteca.database.daos.UsuarioDAO;

/*
    crea la ventana de eliminar libro
 */

public class EliminarUsuario {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    public EliminarUsuario(ModeloUsuarios modelo){
        EliminarUsuarioVentana eliminarUsuarioVentana = new EliminarUsuarioVentana(modelo.getUsuarioSeleccionado().getID(), usuarioDAO);
        if(eliminarUsuarioVentana.isEliminar()) modelo.anunciarCambio();
    }
}
