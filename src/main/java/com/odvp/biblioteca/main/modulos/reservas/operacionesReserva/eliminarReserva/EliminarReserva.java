package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.eliminarReserva;

import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;

import java.util.List;

public class EliminarReserva {
    private ReservaDAO reservaDAO = new ReservaDAO();
    public EliminarReserva(ModeloReserva modelo) {
     EliminarReservaVentana eliminarReservaVentana = new EliminarReservaVentana(modelo.reservaSeleccionada().getID(),reservaDAO);
        if(eliminarReservaVentana.isEliminar()){
            List<IDatoVisual> reservas = reservaDAO.listaReservasIVisual();
            modelo.setReservaMostrada(reservas);
        }
    }
}
