package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.eliminarPrestamo;

import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;

public class EliminarPrestamo {
    PrestamoDAO prestamoDAO = new PrestamoDAO();
    public EliminarPrestamo(ModeloPrestamos modelo) {
        EliminarPrestamoVentana eliminarPrestamoVentana = new EliminarPrestamoVentana(modelo.getPrestamoSeleccionado().getID());
        if(eliminarPrestamoVentana.fueEliminado())modelo.setPrestamosMostrados(prestamoDAO.listaPrestamosCardData());
    }
}
