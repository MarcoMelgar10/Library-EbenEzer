package com.odvp.biblioteca.login;

public class SesionAdministrador {
    private static String administradorActivo;

    public static void setAdministradorActivo(String nombre) {
        administradorActivo = nombre;
    }

    public static String getAdministradorActivo() {
        return administradorActivo;
    }
}
