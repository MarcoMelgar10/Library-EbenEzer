package com.odvp.biblioteca.main.barraOpciones;

import com.odvp.biblioteca.database.daos.*;
import com.odvp.biblioteca.main.MainEscena;
import com.odvp.biblioteca.main.ModeloMain;
import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.main.modulos.autores.ModuloAutores;
import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos.OBS_CAMBIO_GENERICO;

/*
    Esta clase se encarga de guardar las opciones establecidas en el OpcionServicio
    y asociarla a el controlador de cada card de opcion que se ve a la izquierda en la app.
    Cuando una card de opcion ese seleccionada se colorea y el resto se pone sin color.
*/

public class CargadorModulo implements PropertyChangeListener {

    private MainEscena escenePrincipal;
    private ModeloMain modelo;


    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public CargadorModulo(MainEscena escenePrincipal, ModeloMain modelo){
        this.modelo = modelo;
        this.escenePrincipal = escenePrincipal;
        support.addPropertyChangeListener(this);
        this.modelo.addObserver(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloMain.OBS_CURRENT_MODULO)){
            switch(evt.getNewValue().toString()) {
                case ModeloMain.MODULO_LIBROS:
                    escenePrincipal.setCenter(ServicioModulos.getModuloLibros());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloLibros());
                    break;
                case ModeloMain.MODULO_AUTORES:
                    escenePrincipal.setCenter(ServicioModulos.getModuloAutores());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloAutores());
                    break;
                case ModeloMain.MODULO_MULTAS:
                    escenePrincipal.setCenter(ServicioModulos.getModuloMulta());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloMulta());
                    break;
                case ModeloMain.MODULO_USUARIOS:
                    escenePrincipal.setCenter(ServicioModulos.getModuloUsuarios());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloUsuarios());
                    break;
                case ModeloMain.MODULO_PRESTAMOS:
                    escenePrincipal.setCenter(ServicioModulos.getModuloPrestamo());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloPrestamo());
                    break;
                case ModeloMain.MODULO_RESERVAS:
                    escenePrincipal.setCenter(ServicioModulos.getModuloReserva());
                    ServicioModulos.recargarVista(ServicioModulos.getModuloReserva());
                    break;
            }
        }
    }
    public void anunciarCambio(){
        support.firePropertyChange(OBS_CAMBIO_GENERICO, false, true);
    }

}
