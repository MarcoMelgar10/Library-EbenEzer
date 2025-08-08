package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServicioBusquedaPrestamo implements PropertyChangeListener {
        private final PrestamoDAO prestamoDAO = new PrestamoDAO();
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private ScheduledFuture<?> scheduledTask;
        private ModeloPrestamos modelo;
        private PropertyChangeSupport support = new PropertyChangeSupport(this);

        public ServicioBusquedaPrestamo(ModeloPrestamos modelo) {
            this.modelo = modelo;
            support.addPropertyChangeListener(this);
            this.modelo.addObserver(this);
        }

        private void iniciarBusquedaConRetraso() {
            if (scheduledTask != null) {
                scheduledTask.cancel(false);
            }

            scheduledTask = scheduler.schedule(this::realizarBusqueda, 800, TimeUnit.MILLISECONDS);
        }

        private void realizarBusqueda() {
            String textoBusqueda = modelo.getTextoBusqueda();
            String tipoBusqueda = modelo.getTipo_de_busqueda();
            if(!modelo.getTextoBusqueda().isEmpty()){
                if(tipoBusqueda.equals(modelo.BUSQUEDA_POR_USUARIO)){
                    try {
                        Integer.parseInt(textoBusqueda);
                        List<IDatoVisual> prestamos = prestamoDAO.listaPrestamosBusqueda(textoBusqueda, tipoBusqueda);
                        modelo.setPrestamosMostrados(prestamos);
                        }catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Ingrese un formato valido (numerico)", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                }else {
                    List<IDatoVisual> prestamos = prestamoDAO.listaPrestamosBusqueda(textoBusqueda, tipoBusqueda);
                    modelo.setPrestamosMostrados(prestamos);
                }
            }else {
                List<IDatoVisual> prestamos = prestamoDAO.listaPrestamosCardData();
                modelo.setPrestamosMostrados(prestamos);
            }
        }


        public void shutdown() {
            scheduler.shutdown();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(     evt.getPropertyName().equals(ModeloPrestamos.OBS_TEXTO_BUSCADOR)
                    || evt.getPropertyName().equals(ModeloPrestamos.OBS_TIPO_DE_BUSQUEDA)
                    || evt.getPropertyName().equals(ModeloPrestamos.OBS_CAMBIO_GENERICO)
            ){iniciarBusquedaConRetraso();}
        }
}


