package com.odvp.biblioteca.objetos;

import java.sql.Date;

public class Log {
private String tipo;
private String descripcion;
private Date fecha;
    public Log(String tipo, String descripcion, Date fecha){
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
