package com.odvp.biblioteca.main.modulos.reservas;

import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.ReservaCardData;
import javafx.concurrent.Task;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class ModuloReserva extends BorderPane implements IModulo {
    private HeaderReserva header;
    private TablaReserva table;
    private ModeloReserva modelo;
    public ModuloReserva(ModeloReserva modelo) {
        this.modelo = modelo;
        header = new HeaderReserva(modelo);
        table = new TablaReserva(modelo);
        ServicioBusqueda busquedaReserva = new ServicioBusqueda(modelo);
        setTop(header);
        setCenter(table);
        cargarDatosIniciales();
    }
        @Override
        public void cargarDatosIniciales() {
        new Thread(new Task<>() {
                protected Object call() throws Exception {
                        List<IDatoVisual> reservaDatos = new ArrayList<>();
                        ReservaDAO reservaDAO = new ReservaDAO();
                        List<ReservaCardData> reservas = reservaDAO.listaReservasVisual();
                        for (ReservaCardData reserva : reservas) {
                            reservaDatos.add(new ReservaCardData(reserva.getID(), reserva.getNombreUsuario(), reserva.getTituloLibro(), reserva.getFecha_reserva(), reserva.getEstado()));
                        }
                        modelo.setReservaMostrada(reservaDatos);
                        return null;
                    }
                }).start();
        }
}
