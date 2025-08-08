package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.visualizarReserva;

import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.objetos.Reserva;

public class VIsualizarReserva {
    ReservaDAO reservaDAO = new ReservaDAO();
    public VIsualizarReserva(ModeloReserva modelo) {
        Reserva reseva = reservaDAO.obtener(modelo.reservaSeleccionada().getID());
        new VisualizarReservaVentana(reservaDAO, reseva);
    }
}
