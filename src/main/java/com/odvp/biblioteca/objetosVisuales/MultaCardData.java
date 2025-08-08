package com.odvp.biblioteca.objetosVisuales;

import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.scene.image.Image;

import java.sql.Date;
import java.util.List;

/*
Representa la informacion de las multas que se veran.
 */


public class MultaCardData implements IDatoVisual {
    private int Id;
    private String nombreUsuario;
    private int monto;
    private Date fecha;
    private int id_prestamo;
    private Image image;
    private boolean estado;


    public MultaCardData(int Id, String nombreUsuario, int monto, Date fecha, boolean estado, int id_prestamo) {
        this.Id = Id;
        this.nombreUsuario = nombreUsuario;
        this.monto = monto;
        this.fecha = fecha;
        this.id_prestamo = id_prestamo;
        this.estado = estado;
        ServicioIconos icon = new ServicioIconos();
        if (!estado){
            image = new Image(icon.MULTA_CANCELADA);
        }else{
            image = new Image(icon.MULTA_SIN_CANCELAR);
        }

    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public int getID() {
        return this.Id;
    }

    @Override
    public List<Object> getDatos() {
        return List.of(image,Id, nombreUsuario, monto, fecha, id_prestamo);
    }
}
