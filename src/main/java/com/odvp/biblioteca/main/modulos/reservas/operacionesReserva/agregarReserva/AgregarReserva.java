package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.agregarReserva;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;

import java.util.List;

public class AgregarReserva {
    private ReservaDAO reservaDAO = new ReservaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LibroDAO libroDAO = new LibroDAO();

    public AgregarReserva(ModeloReserva modelo) {
        AgregarReservaVentana agregarReservaVentana = new AgregarReservaVentana(reservaDAO, usuarioDAO, libroDAO);
        if (agregarReservaVentana.isHubieronCambios()) {
            List<IDatoVisual> reservas = reservaDAO.listaReservasIVisual();
            modelo.setReservaMostrada(reservas);
        }

    }
}

