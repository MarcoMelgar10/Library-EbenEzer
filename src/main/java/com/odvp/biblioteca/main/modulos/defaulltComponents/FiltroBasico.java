package com.odvp.biblioteca.main.modulos.defaulltComponents;

public class FiltroBasico implements IFiltro{

    private String nombre, sql;

    public FiltroBasico(String nombre, String sql){
        this.nombre = nombre;
        this.sql = sql;
    }


    @Override
    public String getQry() {
        return sql;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
