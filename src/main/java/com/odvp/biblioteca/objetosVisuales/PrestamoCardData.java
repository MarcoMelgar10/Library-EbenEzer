package com.odvp.biblioteca.objetosVisuales;

import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.scene.image.Image;

import java.sql.Date;
import java.util.List;

public class PrestamoCardData implements IDatoVisual{
    private int idPrestamo;
    private Date fechaPrestamo;
    private String nombreUsuario;
    private String tituloLibro;
    private String estado;
    private Image image;


    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public PrestamoCardData(int idPrestamo, Date fechaPrestamo, String nombreUsuario, String tituloLibro, String estado) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.nombreUsuario = nombreUsuario;
        this.tituloLibro = tituloLibro;
        ServicioIconos iconos = new ServicioIconos();
        if(estado.equals("activo")){
            image = new Image(iconos.PRESTAMO_ACTIVO);
        } else if (estado.equals("vencido")) {
            image = new Image(iconos.PRESTAMO_VENCIDO);
        }else{
            image = new Image(iconos.PRESTAMO_DEVUELTO);
        }

    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    @Override
    public int getID() {
        return idPrestamo;
    }

    @Override
    public List<Object> getDatos() {
        return List.of(image, idPrestamo, nombreUsuario, tituloLibro, fechaPrestamo);
    }
}
