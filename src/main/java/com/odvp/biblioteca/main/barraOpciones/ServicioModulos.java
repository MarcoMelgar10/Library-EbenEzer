package com.odvp.biblioteca.main.barraOpciones;

import com.odvp.biblioteca.main.modulos.autores.ModeloAutores;
import com.odvp.biblioteca.main.modulos.autores.ModuloAutores;
import com.odvp.biblioteca.main.modulos.libros.ModeloLibros;
import com.odvp.biblioteca.main.modulos.libros.ModuloLibros;
import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.main.modulos.multas.ModuloMulta;
import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.main.modulos.prestamos.ModeloPrestamos;
import com.odvp.biblioteca.main.modulos.prestamos.ModuloPrestamo;
import com.odvp.biblioteca.main.modulos.reservas.ModeloReserva;
import com.odvp.biblioteca.main.modulos.reservas.ModuloReserva;
import com.odvp.biblioteca.main.modulos.usuarios.ModeloUsuarios;
import com.odvp.biblioteca.main.modulos.usuarios.ModuloUsuarios;

public class ServicioModulos {

    private static final ModuloLibros moduloLibros = new ModuloLibros(new ModeloLibros());
    private static final ModuloAutores moduloAutores = new ModuloAutores(new ModeloAutores());
    private static final ModuloMulta moduloMulta = new ModuloMulta(new ModeloMulta());
    private static final ModuloUsuarios moduloUsuarios = new ModuloUsuarios(new ModeloUsuarios());
    private static final ModuloReserva moduloReserva = new ModuloReserva(new ModeloReserva());
    private static final ModuloPrestamo moduloPrestamo = new ModuloPrestamo(new ModeloPrestamos());

    public static ModuloLibros getModuloLibros() {
        return moduloLibros;
    }

    public static ModuloAutores getModuloAutores() {
        return moduloAutores;
    }

    public static ModuloMulta getModuloMulta() {
        return moduloMulta;
    }

    public static ModuloUsuarios getModuloUsuarios() {
        return moduloUsuarios;
    }

    public static void recargarVista(IModulo modulo){
        modulo.cargarDatosIniciales();
    }

    public static ModuloReserva getModuloReserva() {return moduloReserva;}

    public static ModuloPrestamo getModuloPrestamo() {return moduloPrestamo;}
}
