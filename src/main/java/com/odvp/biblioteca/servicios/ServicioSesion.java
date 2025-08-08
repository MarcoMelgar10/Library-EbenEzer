package com.odvp.biblioteca.servicios;

import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.IAmbiente;
import com.odvp.biblioteca.objetos.Administrador;

public class ServicioSesion {
    private static Administrador administrador;
    private static IModulo moduloActual;
    private static IAmbiente ambienteAcutal;

    private static ServicioSesion instance;

    private ServicioSesion(){

    }

    public static ServicioSesion getInstance() {
        if(instance == null) instance = new ServicioSesion();
        return instance;
    }

    public static Administrador getAdministrador() {
        return administrador;
    }

    public static void setAdministrador(Administrador administrador) {
        ServicioSesion.administrador = administrador;
    }

    public static IModulo getModuloActual() {
        return moduloActual;
    }

    public static void setModuloActual(IModulo moduloActual) {
        ServicioSesion.moduloActual = moduloActual;
    }

    public static IAmbiente getAmbienteAcutal() {
        return ambienteAcutal;
    }

    public static void setAmbienteAcutal(IAmbiente ambienteAcutal) {
        ServicioSesion.ambienteAcutal = ambienteAcutal;
    }
}
