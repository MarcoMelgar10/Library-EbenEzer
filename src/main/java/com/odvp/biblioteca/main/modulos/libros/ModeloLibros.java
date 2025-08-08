package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.LibroCardData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ModeloLibros {
    private static final Logger log = LogManager.getLogger(ModeloLibros.class);
    private List<CategoryData> categorias;
    private List<IFiltro> filtros;
    private String textoBusqueda;
    private IDatoVisual libroSeleccionado;
    private List<IDatoVisual> librosMostrados;
    private List<CategoryData> categoriasSeleccionadas;
    private List<IFiltro> filtrosSeleccionados;
    private String tipo_de_busqueda;

    public static final String BUSQUEDA_POR_AUTOR = "BUSQUEDA POR AUTOR";
    public static final String BUSQUEDA_POR_TITULO = "BUSQUEDA POR TITULO";
    public static final String OBS_CAMBIO_GENERICO = "CAMBIO GENERICO";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public static final String OBS_CATEGORIAS_SELECCIONADAS = "OBS_CATEGORIAS_SELECCIONADAS";
    public static final String OBS_CATEGORIAS_MOSTRADAS = "OBS_CATEGORIAS_MOSTRADAS";
    public static final String OBS_FILTROS_SELECCIONADOS = "OBS_FILTROS_SELECCIONADOS";
    public static final String OBS_FILTROS_MOSTRADOS = "OBS_FILTROS_MOSTRADOS";
    public static final String OBS_TEXTO_BUSCADOR = "OBS_TEXTO_BUSCADOR";
    public static final String OBS_LIBROS_MOSTRADOS = "OBS_LIBROS_MOSTRADOS";
    public static final String OBS_LIBRO_SELECCIONADO = "OBS_LIBRO_SELECCIONADO";
    public static final String OBS_TIPO_DE_BUSQUEDA = "TIPO DE BUSQUEDA";

    public ModeloLibros(){
        categorias = new ArrayList<>();
        categoriasSeleccionadas = new ArrayList<>();
        filtrosSeleccionados = new ArrayList<>();
        filtros = new ArrayList<>();
        librosMostrados = new ArrayList<>();
        textoBusqueda = "";
        libroSeleccionado = null;
    }

    public void setCategoriaSelected(CategoryData categoria, boolean selected){
        List<CategoryData> oldCategorias = List.copyOf(categoriasSeleccionadas);
        if(selected) categoriasSeleccionadas.add(categoria);
        else categoriasSeleccionadas.remove(categoria);
        support.firePropertyChange(OBS_CATEGORIAS_SELECCIONADAS, oldCategorias, this.categoriasSeleccionadas);
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

    public void setLibrosMostrados(List<IDatoVisual> libros){
        List<IDatoVisual> oldLibros = List.copyOf(this.librosMostrados);
        librosMostrados = libros;
        support.firePropertyChange(OBS_LIBROS_MOSTRADOS, oldLibros, this.librosMostrados);
        setLibroSeleccionado(null);
    }

    public void setCategoriasMostradas(List<CategoryData> categorias){
        List<CategoryData> oldCategorias = List.copyOf(this.categorias);
        this.categorias = categorias;
        support.firePropertyChange(OBS_CATEGORIAS_MOSTRADAS, oldCategorias, this.categorias);
    }

    public void setFiltroSelected(IFiltro filtro, boolean selected){
        List<IFiltro> oldFiltros = List.copyOf(filtrosSeleccionados);
        if(selected) filtrosSeleccionados.add(filtro);
        else filtrosSeleccionados.remove(filtro);
        support.firePropertyChange(OBS_FILTROS_SELECCIONADOS, oldFiltros, this.filtrosSeleccionados);
    }

    public void setFiltrosMostrados(List<IFiltro> filtros){
        List<IFiltro> oldFiltros = List.copyOf(this.filtros);
        this.filtros = filtros;
        support.firePropertyChange(OBS_FILTROS_MOSTRADOS, oldFiltros, this.filtros);
    }

    public void setTextoBusqueda(String textoBusqueda){
        String oldTexto = this.textoBusqueda;
        this.textoBusqueda = textoBusqueda;
        support.firePropertyChange(OBS_TEXTO_BUSCADOR, oldTexto, this.textoBusqueda);
    }

    public void setLibroSeleccionado(IDatoVisual libroSeleccionado){
        if(this.libroSeleccionado == libroSeleccionado) libroSeleccionado = null;
        IDatoVisual oldLibro = this.libroSeleccionado;
        this.libroSeleccionado = libroSeleccionado;
        support.firePropertyChange(OBS_LIBRO_SELECCIONADO, oldLibro, this.libroSeleccionado);
    }

    public List<IFiltro> getFiltros() {
        return filtros;
    }

    public void addObserver(PropertyChangeListener observer){
        support.addPropertyChangeListener(observer);
    }

    public LibroCardData getLibroSeleccionado() {
        return (LibroCardData) libroSeleccionado;
    }

    public List<IDatoVisual> getLibrosMostrados() {
        return librosMostrados;
    }

    public List<CategoryData> getCategoriasSeleccionadas() {
        return categoriasSeleccionadas;
    }

    public List<CategoryData> getCategoriasMostradas() {
        return categorias;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public List<IFiltro> getFiltrosSeleccionados() {
        return filtrosSeleccionados;
    }
}
