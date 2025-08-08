package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.editarReserva;

import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.eliminarReserva.EliminarReservaVentana;
import com.odvp.biblioteca.objetos.Reserva;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;

import java.util.List;

public class EditarReserva {
    private ReservaDAO reservaDAO = new ReservaDAO();
    public EditarReserva(ModeloReserva modelo) {
        Reserva reserva = reservaDAO.obtener(modelo.reservaSeleccionada().getID());
        EditarReservaVentana editarReservaVentana = new EditarReservaVentana(reservaDAO, reserva);
        if(editarReservaVentana.huboCambios()){
            List<IDatoVisual> reservas = reservaDAO.listaReservasIVisual();
            modelo.setReservaMostrada(reservas);
        }
    }
}
