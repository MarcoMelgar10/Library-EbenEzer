package com.odvp.biblioteca.main.modulos.multas;

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

public class ServicioBusquedaMulta implements PropertyChangeListener {
    private final MultaDAO multaDAO = new MultaDAO();  // DAO para acceder a la BD
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledTask;
    private ModeloMulta modelo;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ServicioBusquedaMulta(ModeloMulta modelo) {
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
        List<IDatoVisual> multas = multaDAO.listaMultasVisual(textoBusqueda);
        modelo.setMultaMostrada(multas);

    }


    public void shutdown() {
        scheduler.shutdown();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloMulta.OBS_CAMBIO_TEXTO)
        ){iniciarBusquedaConRetraso();}
    }
}
