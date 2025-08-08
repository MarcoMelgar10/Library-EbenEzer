package com.odvp.biblioteca.main.modulos.defaulltComponents;

import java.sql.Date;

public class FiltroFecha implements IFiltro{

    private String nombre, qry;
    private String campo;
    private Date fechaInicial, fechaFinal;

    public FiltroFecha(String nombre, String campo){
        this.nombre = nombre;
        this.campo = campo;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    @Override
    public String getQry() {
        if(campo == null || (fechaInicial == null && fechaFinal == null)) return "";
        else if (fechaInicial == null) {
            return " AND " + campo + " <= '" + fechaFinal+"'";
        } else if (fechaFinal == null) {
            return " AND " + campo + " >= '" + fechaInicial+"'";
        }
        return " AND " + campo + " BETWEEN '" + fechaInicial + "' AND '" + fechaFinal + "'";
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
