package com.odvp.biblioteca.main.modulos.multas;

import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.MultaCardData;
import com.odvp.biblioteca.database.daos.MultaDAO;
import javafx.concurrent.Task;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class ModuloMulta extends BorderPane implements IModulo {
    private HeaderMultas header;
    private TablaMulta table;
    private ModeloMulta modelo;

    public ModuloMulta(ModeloMulta modelo) {
        this.modelo = modelo;
        header = new HeaderMultas(modelo);
        table = new TablaMulta(modelo);
        ServicioBusquedaMulta busquedaMulta = new ServicioBusquedaMulta(modelo);
        setTop(header);
        setCenter(table);
        cargarDatosIniciales();
    }

    @Override
    public void cargarDatosIniciales() {
        new Thread(new Task<>() {
            @Override
            protected Object call() throws Exception {
                List<IDatoVisual> datoDeudas = new ArrayList<>();
                MultaDAO multaDAO = new MultaDAO();
                List<MultaCardData> multas = multaDAO.listaMultas();
                for (MultaCardData multa : multas) {
                    datoDeudas.add(new MultaCardData(multa.getID(), multa.getNombreUsuario(), multa.getMonto(),multa.getFecha(), multa.isEstado(), multa.getId_prestamo()));
                }
               modelo.setMultaMostrada(datoDeudas);
                return null;
            }
        }).start();
    }
}
