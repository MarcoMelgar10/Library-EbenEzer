package com.odvp.biblioteca.main.modulos.prestamos;

import com.odvp.biblioteca.database.daos.PrestamoDAO;
import com.odvp.biblioteca.main.modulos.IModulo;
import javafx.scene.layout.BorderPane;

public class ModuloPrestamo extends BorderPane implements IModulo {
    private ModeloPrestamos modelo;
    private HeaderPrestamos header;
    private TablePrestamo table;
    private ServicioBusquedaPrestamo busquedaPrestamo;

    public ModuloPrestamo(ModeloPrestamos modelo){
        this.modelo = modelo;
        header = new HeaderPrestamos(this.modelo);
        table = new TablePrestamo(this.modelo);
        busquedaPrestamo = new ServicioBusquedaPrestamo(this.modelo);
        setTop(header);
        setCenter(table);
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales() {
        new Thread(() -> {
            PrestamoDAO prestamoDAO = new PrestamoDAO();
            modelo.setPrestamosMostrados(prestamoDAO.listaPrestamosCardData());
        }).start();
    }

}