package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.UsuarioDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServicioBusquedaUsuarios implements PropertyChangeListener {
        private final UsuarioDAO usuarioDAO = new UsuarioDAO();  // DAO para acceder a la BD
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private ScheduledFuture<?> scheduledTask;
        private ModeloUsuarios modelo;
        private PropertyChangeSupport support = new PropertyChangeSupport(this);

        public ServicioBusquedaUsuarios(ModeloUsuarios modelo) {
            this.modelo = modelo;
            support.addPropertyChangeListener(this);
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
            List<IDatoVisual> usuarios = usuarioDAO.listaUsuariosParametrizado(textoBusqueda);
            modelo.setUsuariosMostrados(usuarios);

        }


        public void shutdown() {
            scheduler.shutdown();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ModeloUsuarios.OBS_TEXTO_BUSCADOR) ||
                    evt.getPropertyName().equals(ModeloUsuarios.OBS_CAMBIO_GENERICO)){
                iniciarBusquedaConRetraso();
            }
        }
}


