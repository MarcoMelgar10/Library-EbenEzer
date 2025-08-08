package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.VisualizarUsuario;

import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;
import com.odvp.biblioteca.objetos.Usuario;
import com.odvp.biblioteca.database.daos.UsuarioDAO;

/*
    crea la vista para visualizar libro
 */

public class VisualizarUsuario {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    public VisualizarUsuario(ModeloUsuarios modelo){
        Usuario usuario = usuarioDAO.obtener(modelo.getUsuarioSeleccionado().getID());
        VIsualizarUsuarioVentana vIsualizarUsuarioVentana = new VIsualizarUsuarioVentana(usuario);
    }
}
