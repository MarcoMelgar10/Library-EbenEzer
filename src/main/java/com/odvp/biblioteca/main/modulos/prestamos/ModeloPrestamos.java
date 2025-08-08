package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.LibroCardData;
import com.odvp.biblioteca.objetosVisuales.PrestamoCardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ModeloPrestamos {
    private static final Logger log = LogManager.getLogger(ModeloPrestamos.class);
    private List<CategoryData> categorias;
    private List<IFiltro> filtros;
    private String textoBusqueda;
    private IDatoVisual prestamoSeleccionado;
    private List<IDatoVisual> prestamosMostrados;
    private String tipo_de_busqueda;

    public static final String BUSQUEDA_POR_USUARIO = "BUSQUEDA_POR_USUARIO";
    public static final String BUSQUEDA_POR_TITULO = "BUSQUEDA_POR_TITULO";
    public static final String OBS_CAMBIO_GENERICO = "CAMBIO GENERICO";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public static final String OBS_TEXTO_BUSCADOR = "OBS_TEXTO_BUSCADOR";
    public static final String OBS_PRESTAMOS_MOSTRADOS = "OBS_PRESTAMOS_MOSTRADOS";
    public static final String OBS_PRESTAMOS_SELECCIONADO = "OBS_PRESTAMOS_SELECCIONADO";
    public static final String OBS_TIPO_DE_BUSQUEDA = "TIPO DE BUSQUEDA";

    public ModeloPrestamos(){
        categorias = new ArrayList<>();
        filtros = new ArrayList<>();
        prestamosMostrados = new ArrayList<>();
        textoBusqueda = "";
        prestamoSeleccionado = null;
    }


    public String getTipo_de_busqueda() {
        return tipo_de_busqueda;
    }

    public void anunciarCambio(){
        support.firePropertyChange(OBS_CAMBIO_GENERICO, false, true);
    }

    public void setTipo_de_busqueda(String tipo_de_busqueda) {
        String oldTipoBusqueda = this.tipo_de_busqueda;
        this.tipo_de_busqueda = tipo_de_busqueda;
        support.firePropertyChange(OBS_TIPO_DE_BUSQUEDA, oldTipoBusqueda, this.tipo_de_busqueda);
    }

    public void setPrestamosMostrados(List<IDatoVisual> prestamos){
        List<IDatoVisual> oldPrestamo = List.copyOf(this.prestamosMostrados);
        prestamosMostrados = prestamos;
        support.firePropertyChange(OBS_PRESTAMOS_MOSTRADOS, oldPrestamo, this.prestamosMostrados);
        setPrestamoSeleccionado(null);
    }

    public void setTextoBusqueda(String textoBusqueda){
        String oldTexto = this.textoBusqueda;
        this.textoBusqueda = textoBusqueda;
        support.firePropertyChange(OBS_TEXTO_BUSCADOR, oldTexto, this.textoBusqueda);
    }

    public void setPrestamoSeleccionado(IDatoVisual prestamoSeleccionado){
        if(this.prestamoSeleccionado == prestamoSeleccionado) prestamoSeleccionado = null;
        IDatoVisual oldPrestamo = this.prestamoSeleccionado;
        this.prestamoSeleccionado = prestamoSeleccionado;
        support.firePropertyChange(OBS_PRESTAMOS_SELECCIONADO, oldPrestamo, this.prestamoSeleccionado);
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public PrestamoCardData getPrestamoSeleccionado() {
        return (PrestamoCardData) prestamoSeleccionado;
    }

    public List<IDatoVisual> getPrestamosMostrados() {
        return prestamosMostrados;
    }

    public List<CategoryData> getCategoriasMostradas() {
        return categorias;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }
}
