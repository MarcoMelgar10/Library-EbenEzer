package com.odvp.biblioteca.objetos;

import java.sql.Date;

public class Prestamo {
    private Date fecha;
    private Date fecha_vencimiento;
    private Date fecha_devolucion;
    private int idUsuario;
    private int idLibro;
    private String estado;
    private int id;

    public Prestamo(int id, Date fecha, Date fecha_vencimiento, Date fecha_devolucion, int idUsuario, int idLibro, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.fecha_vencimiento = fecha_vencimiento;
        this.fecha_devolucion = fecha_devolucion;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaVencimiento() {
        return fecha_vencimiento;
    }

    public void setFechaVencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Date getFechaDevolucion() {
        return fecha_devolucion;
    }

    public void setFechaDevolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "fecha=" + fecha +
                ", fecha_vencimiento=" + fecha_vencimiento +
                ", fecha_devolucion=" + fecha_devolucion +
                ", usuario='" + idUsuario + '\'' +
                ", libro='" + idLibro + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

