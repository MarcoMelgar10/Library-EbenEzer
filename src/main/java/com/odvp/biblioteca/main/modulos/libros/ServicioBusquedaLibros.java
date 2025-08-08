package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.LibroDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServicioBusquedaLibros implements PropertyChangeListener {
        private final LibroDAO libroDAO = new LibroDAO();  // DAO para acceder a la BD
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private ScheduledFuture<?> scheduledTask;
        private ModeloLibros modelo;
        private PropertyChangeSupport support = new PropertyChangeSupport(this);

        public ServicioBusquedaLibros(ModeloLibros modelo) {
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
            List<CategoryData> categoriasSeleccionadas = modelo.getCategoriasSeleccionadas();
            String tipoBusqueda = modelo.getTipo_de_busqueda();
            List<IFiltro> filtrosSeleccionados = modelo.getFiltrosSeleccionados();

            List<IDatoVisual> libros = libroDAO.listaLibrosVisualParametrizada(textoBusqueda,categoriasSeleccionadas,filtrosSeleccionados,tipoBusqueda);
            modelo.setLibrosMostrados(libros);


        }


        public void shutdown() {
            scheduler.shutdown();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ModeloLibros.OBS_CATEGORIAS_SELECCIONADAS)
                    || evt.getPropertyName().equals(ModeloLibros.OBS_TEXTO_BUSCADOR)
                    || evt.getPropertyName().equals(ModeloLibros.OBS_TIPO_DE_BUSQUEDA)
                    || evt.getPropertyName().equals(ModeloLibros.OBS_FILTROS_SELECCIONADOS)
                    || evt.getPropertyName().equals(ModeloLibros.OBS_CAMBIO_GENERICO)
            ){
                iniciarBusquedaConRetraso();
            }
        }
}


