package com.odvp.biblioteca.main.modulos.reservas;

import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.MultaDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServicioBusqueda implements PropertyChangeListener {
    private final ReservaDAO reservaDAO = new ReservaDAO(); // DAO para acceder a la BD
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledTask;
    private ModeloReserva modelo;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ServicioBusqueda(ModeloReserva modelo) {
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }

    private void iniciarBusquedaConRetraso() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false); // Cancela la búsqueda anterior si el usuario sigue escribiendo
        }

        scheduledTask = scheduler.schedule(this::realizarBusqueda, 300, TimeUnit.MILLISECONDS); // Espera 300ms antes de ejecutar la consulta
    }

    private void realizarBusqueda() {
        String textoBusqueda = modelo.getTextoBusqueda();
        List<IDatoVisual> reserva = reservaDAO.listaReservasVisual(textoBusqueda);
        modelo.setReservaMostrada(reserva);

    }


    public void shutdown() {
        scheduler.shutdown();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloReserva.OBS_CAMBIO_TEXTO)
        ){iniciarBusquedaConRetraso();}
    }
}

