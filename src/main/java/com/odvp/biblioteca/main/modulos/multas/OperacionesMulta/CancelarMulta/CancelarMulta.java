package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.CancelarMulta;

import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.objetosVisuales.MultaCardData;
import com.odvp.biblioteca.database.daos.MultaDAO;

import java.util.List;

public class CancelarMulta {
    MultaDAO multaDAO = new MultaDAO();

    public CancelarMulta(ModeloMulta modelo) {
        MultaCardData multaCardData = modelo.multaSeleccionada();
        CancelarMultaVentana cancelarMultaVentana = new CancelarMultaVentana(multaDAO, multaCardData);
        if (cancelarMultaVentana.isHubieronCambios()) {
            List<IDatoVisual> multas = multaDAO.listaMultasVisual();
            modelo.setMultaMostrada(multas);
        }
    }
}
