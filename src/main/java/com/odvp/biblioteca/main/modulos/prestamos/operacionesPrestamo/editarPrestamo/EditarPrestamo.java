package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.editarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetos.Usuario;

public class EditarPrestamo {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    public EditarPrestamo(ModeloPrestamos modelo) {
        Prestamo prestamo = prestamoDAO.obtener(modelo.getPrestamoSeleccionado().getID());
        EditarPrestamoVentana editarPrestamoVentana = new EditarPrestamoVentana(prestamo, prestamoDAO, libroDAO, usuarioDAO);
        if(editarPrestamoVentana.fueModificado()) modelo.setPrestamosMostrados(prestamoDAO.listaPrestamosCardData());
    }
}
