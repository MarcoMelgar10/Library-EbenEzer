package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Agregar;

import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.MultaDAO;

import java.util.List;

public class AgregarMulta {
    private MultaDAO multaDAO;
    public AgregarMulta(ModeloMulta modelo){
        multaDAO = new MultaDAO();
        AgregarMultaVentana agregarMulta = new AgregarMultaVentana(multaDAO);
        if(agregarMulta.isHubieronCambios()){
            List<IDatoVisual> multas = multaDAO.listaMultasVisual();
            modelo.setMultaMostrada(multas);
        }

    }
}
