package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.visualizarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.visualizarReserva.VisualizarReservaVentana;
import com.odvp.biblioteca.objetos.Prestamo;

public class VisualizarPrestamo {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    public VisualizarPrestamo(ModeloPrestamos modelo) {
    Prestamo prestamo = prestamoDAO.obtener(modelo.getPrestamoSeleccionado().getID());
    new VisualizarPrestamoVentana(prestamo, libroDAO, usuarioDAO);

    }
}
