package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.agregarPrestamo;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;

public class AgregarPrestamo {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private PrestamoDAO  prestamoDAO = new PrestamoDAO();
    public AgregarPrestamo(ModeloPrestamos modelo) {
    AgregarPrestamoVentana agregarPrestamoVentana = new AgregarPrestamoVentana(libroDAO, usuarioDAO);
    if(agregarPrestamoVentana.fueGuardado())modelo.setPrestamosMostrados(prestamoDAO.listaPrestamosCardData());
    }
}
