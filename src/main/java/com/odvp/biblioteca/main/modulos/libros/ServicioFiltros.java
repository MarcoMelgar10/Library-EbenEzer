package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.FiltroBasico;
import com.odvp.biblioteca.main.modulos.defaulltComponents.FiltroFecha;
import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;

import java.util.List;

public class ServicioFiltros {
    public static List<IFiltro> obtenerFiltrosPredeterminados() {
        return List.of(
                new FiltroBasico("Hay disponible", " AND stock_disponible > 0"),
                new FiltroBasico("Sin observación", " AND (observacion IS NULL OR observacion = '')"),
                new FiltroFecha("Fecha publicación", "fecha_publicacion")
        );
    }
}