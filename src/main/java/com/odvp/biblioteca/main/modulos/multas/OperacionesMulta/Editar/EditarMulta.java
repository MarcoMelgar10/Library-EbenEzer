package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Editar;

import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.objetos.Multa;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.MultaDAO;

import java.util.List;

public class EditarMulta {
    private MultaDAO multaDAO;
    public EditarMulta(ModeloMulta modelo) {
            multaDAO = new MultaDAO();
            Multa multa = multaDAO.obtener(modelo.multaSeleccionada().getID());
            EditarMultaVentana editarMultaVentana = new EditarMultaVentana(multaDAO, multa );
            if(editarMultaVentana.isHubieronCambios()){
                List<IDatoVisual> multas = multaDAO.listaMultasVisual();
                modelo.setMultaMostrada(multas);
            }
    }
}
