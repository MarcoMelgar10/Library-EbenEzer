package com.odvp.biblioteca.objetos;

import java.sql.Date;


public class Multa {
    private int idMulta;
    private String descripcion;
    private int monto;
    private Date fechaMulta;
    private boolean estado;
    private Date fechaCancelacion;
    private int idPrestamo;
    private boolean D_E_L_E_T_E;

    // Constructor
    public Multa(int idMulta, String descripcion, int monto, Date fechaMulta, boolean estado, Date fechaCancelacion, boolean D_E_L_E_T_E, int idPrestamo) {
        this.idMulta = idMulta;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaMulta = fechaMulta;
        this.estado = estado;
        this.fechaCancelacion = fechaCancelacion;
        this.idPrestamo = idPrestamo;
        this.D_E_L_E_T_E = D_E_L_E_T_E;
    }

    public boolean isD_E_L_E_T_E() {
        return D_E_L_E_T_E;
    }

    public void setD_E_L_E_T_E(boolean d_E_L_E_T_E) {
        D_E_L_E_T_E = d_E_L_E_T_E;
    }

    // Getters y Setters
    public int getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(Date fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }


    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "idMulta=" + idMulta +
                ", descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", fechaMulta=" + fechaMulta +
                ", estado=" + estado +
                ", fechaEliminacion=" + fechaCancelacion +
                ", idPrestamo=" + idPrestamo +
                ", D_E_L_E_T_E= " + D_E_L_E_T_E
                +                 '}';
    }
}
