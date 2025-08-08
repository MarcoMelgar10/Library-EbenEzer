package com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.EliminarAutor;

import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.database.daos.AutorDAO;

import javax.swing.*;

/*
    crea la ventana de eliminar libro
 */

public class EliminarAutor {
    private final AutorDAO autorDAO = new AutorDAO();
    public EliminarAutor(ModeloAutores modelo){
        Integer nroLibrosConAutor = autorDAO.getNroLibrosConAutor(modelo.getAutorSeleccionado().getID());
        if(nroLibrosConAutor != null && nroLibrosConAutor == 0){
            EliminarAutorVentana eliminarAutorVentana = new EliminarAutorVentana(modelo.getAutorSeleccionado().getID(),  autorDAO);
            if(eliminarAutorVentana.isEliminar()) modelo.anunciarCambio();
        }else{
            JOptionPane.showMessageDialog(null,"El autor con el id '"+modelo.getAutorSeleccionado().getID()+"' no puede ser eliminado" +
                    "\nporque existen libros registrados con el autor");
        }

    }
}
