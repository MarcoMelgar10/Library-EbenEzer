package com.odvp.biblioteca.main.barraOpciones;

import com.odvp.biblioteca.main.ModeloMain;
import com.odvp.biblioteca.servicios.ServicioIconos;

public class ServicioOpcionesMain {

    public static OpcionMainButton opcionLibros(ModeloMain modelo){
        return new OpcionMainButton("Libros", ServicioIconos.OPCION_MODULO_LIBROS, ModeloMain.MODULO_LIBROS, modelo);
    }
    public static OpcionMainButton opcionAutores(ModeloMain modelo){
        return new OpcionMainButton("Autores", ServicioIconos.OPCION_MODULO_AUTORES, ModeloMain.MODULO_AUTORES, modelo);
    }
    public static OpcionMainButton opcionPrestamos(ModeloMain modelo){
        return new OpcionMainButton("Prestamos", ServicioIconos.OPCION_MODULO_PRESTAMOS, ModeloMain.MODULO_PRESTAMOS, modelo);
    }
    public static OpcionMainButton opcionDeudas(ModeloMain modelo){
        return new OpcionMainButton("Multas", ServicioIconos.OPCION_MODULO_MULTAS, ModeloMain.MODULO_MULTAS, modelo);
    }
    public static OpcionMainButton opcionReservas(ModeloMain modelo){
        return new OpcionMainButton("Reservas", ServicioIconos.OPCION_MODULO_RESERVAS, ModeloMain.MODULO_RESERVAS, modelo);
    }
    public static OpcionMainButton opcionUsuarios(ModeloMain modelo){
        return new OpcionMainButton("Usuarios", ServicioIconos.OPCION_MODULO_USUARIOS, ModeloMain.MODULO_USUARIOS, modelo);
    }
}
