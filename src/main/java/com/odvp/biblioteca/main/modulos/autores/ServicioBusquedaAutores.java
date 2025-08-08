package com.odvp.biblioteca.main.modulos.autores;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.AutorDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServicioBusquedaAutores implements PropertyChangeListener {
        private final AutorDAO autorDAO = new AutorDAO();  // DAO para acceder a la BD
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private ScheduledFuture<?> scheduledTask;
        private ModeloAutores modelo;
        private PropertyChangeSupport support = new PropertyChangeSupport(this);

        public ServicioBusquedaAutores(ModeloAutores modelo) {
            this.modelo = modelo;
            support.addPropertyChangeListener(this);
            this.modelo.addObserver(this);
        }

        private void iniciarBusquedaConRetraso() {
            if (scheduledTask != null) {
                scheduledTask.cancel(false); // Cancela la búsqueda anterior si el usuario sigue escribiendo
            }

            scheduledTask = scheduler.schedule(this::realizarBusqueda, 800, TimeUnit.MILLISECONDS); // Espera 300ms antes de ejecutar la consulta
        }

        private void realizarBusqueda() {
            String textoBusqueda = modelo.getTextoBusqueda();
            List<IDatoVisual> autores = autorDAO.listaAutoresParametrizado(textoBusqueda);
            modelo.setAutoresMostrados(autores);
        }


        public void shutdown() {
            scheduler.shutdown();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ModeloAutores.OBS_TEXTO_BUSQUEDA) ||
                    evt.getPropertyName().equals(ModeloAutores.OBS_CAMBIO_GENERICO)){
                iniciarBusquedaConRetraso();
            }
        }
}


