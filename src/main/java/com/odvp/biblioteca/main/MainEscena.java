package com.odvp.biblioteca.main;

import com.odvp.biblioteca.main.barraOpciones.CargadorModulo;
import com.odvp.biblioteca.main.barraOpciones.OpcionMainButton;
import com.odvp.biblioteca.main.barraOpciones.ServicioOpcionesMain;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/*
    Controlador de la escena principal, donde a la izquiera se carga la barra de opciones
    y a la derecha la vista del Maestro que haya sido establecido en la clase ManejadorDeMaestros
 */

public class MainEscena extends BorderPane{

    private final OpcionMainButton opcionLibros, opcionAutores, opcionPrestamos, opcionMultas, opcionReservas, opcionUsuarios;

    VBox panelOpciones;

    CargadorModulo cargadorModulo;


    private ModeloMain modelo;
    
    public MainEscena(ModeloMain modeloMain){
        init();
        this.modelo = modeloMain;
        cargadorModulo = new CargadorModulo(this, modelo);
        opcionLibros = ServicioOpcionesMain.opcionLibros(modelo);
        opcionAutores = ServicioOpcionesMain.opcionAutores(modelo);
        opcionMultas = ServicioOpcionesMain.opcionDeudas(modelo);
        opcionReservas = ServicioOpcionesMain.opcionReservas(modelo);
        opcionPrestamos = ServicioOpcionesMain.opcionPrestamos(modelo);
        opcionUsuarios = ServicioOpcionesMain.opcionUsuarios(modelo);

        panelOpciones.getChildren().addAll(opcionLibros, opcionAutores, opcionMultas, opcionReservas, opcionPrestamos, opcionUsuarios);
        modelo.setCurrentModulo(ModeloMain.MODULO_LIBROS);
    }

    private void init(){
        this.setPrefSize(1080, 720);
        panelOpciones = new VBox();
        setLeft(panelOpciones);
        panelOpciones.setPrefWidth(120);
        panelOpciones.setMinWidth(100);
        panelOpciones.setPadding(new Insets(10));
        panelOpciones.setAlignment(Pos.CENTER);
    }

    public VBox getPanelOpciones() {
        return panelOpciones;
    }
}
