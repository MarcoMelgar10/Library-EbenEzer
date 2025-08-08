package com.odvp.biblioteca.main.modulos.prestamos.operacionesPrestamo.devolverLibro;

import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;
import com.odvp.biblioteca.objetos.Prestamo;

import javax.swing.*;

public class DevolverLibro {
    private Prestamo prestamo;
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    public DevolverLibro(ModeloPrestamos modelo) {
         prestamo = prestamoDAO.obtener(modelo.getPrestamoSeleccionado().getID());
         if(prestamo.getEstado().equals("activo")) {
             DevolverLibroVentana devolverLibroVentana = new DevolverLibroVentana(prestamo);
             if (devolverLibroVentana.getHubieronCambios()) {
                 modelo.anunciarCambio();
             }
         }else{
             JOptionPane.showMessageDialog(null, "Prestamo no activo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
         }

    }
}
