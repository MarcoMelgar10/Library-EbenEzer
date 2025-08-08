package com.odvp.biblioteca.objetos;

import java.util.Date;

public class Reserva {
    private int idReserva;
    private Date fechaReserva;
    private Date fechaVencimiento;
    private Date fechaRecogida;
    private String estado;
    private int idUsuario;
    private int idLibro;
    private String observacion;


    public Reserva(int idReserva, int idUsuario, int idLibro, Date fechaReserva, Date fechaVencimiento, Date fechaRecogida,String estado, String observacion ) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaRecogida = fechaRecogida;
        this.observacion = observacion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
    }


    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String isEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getObservacion() {
        return observacion;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", fechaReserva=" + fechaReserva +
                ", estado=" + estado +
                ", idUsuario=" + idUsuario +
                ", idLibro=" + idLibro +
                '}';
    }

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

