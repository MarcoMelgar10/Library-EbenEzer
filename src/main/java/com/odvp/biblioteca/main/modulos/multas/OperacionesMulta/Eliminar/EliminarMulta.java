package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Eliminar;
import com.odvp.biblioteca.main.modulos.multas.ModeloMulta;
import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.database.daos.MultaDAO;

import java.util.List;

public class EliminarMulta {
    private final MultaDAO multaDAO = new MultaDAO();
    public EliminarMulta(ModeloMulta modelo) {
        EliminarMultaVentana eliminarMultaVentana = new EliminarMultaVentana(modelo.multaSeleccionada().getID(),multaDAO );
        if (eliminarMultaVentana.isEliminar()) {
            List<IDatoVisual> multas = multaDAO.listaMultasVisual();
            modelo.setMultaMostrada(multas);
        }
    }
}
