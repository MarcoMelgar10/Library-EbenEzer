package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.EditarUsuario;

import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;
import com.odvp.biblioteca.objetos.Usuario;
import com.odvp.biblioteca.database.daos.*;

/*
    crea la ventana para editar un libro
 */

public class EditarUsuario {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    public EditarUsuario(ModeloUsuarios modelo){
        Usuario usuario = usuarioDAO.obtener(modelo.getUsuarioSeleccionado().getID());

        EditarUsuarioVentana editarLibro = new EditarUsuarioVentana(usuarioDAO,usuario);
        if(editarLibro.isHubieronCambios()) modelo.anunciarCambio();
    }


}
