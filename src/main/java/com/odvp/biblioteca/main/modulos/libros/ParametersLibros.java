package com.odvp.biblioteca.main.modulos.libros;

import com.odvp.biblioteca.main.modulos.defaulltComponents.FiltroBasico;
import com.odvp.biblioteca.main.modulos.defaulltComponents.FiltroFecha;
import com.odvp.biblioteca.main.modulos.defaulltComponents.IFiltro;
import com.odvp.biblioteca.main.modulos.defaulltComponents.ParametersDefault;
import com.odvp.biblioteca.objetosVisuales.CategoryData;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParametersLibros extends ParametersDefault {

    private VBox ventanaCategorias, ventanaFiltros;

    private ModeloLibros modelo;


    public ParametersLibros(ModeloLibros modelo){
        this.modelo = modelo;
        this.modelo.addObserver(this);

        List<Parent> categorias = new ArrayList<>();
        ventanaCategorias = addSubWindow("Categorias",categorias);

        List<Parent> filtros = new ArrayList<>();

        ventanaFiltros = addSubWindow("Filtros", filtros);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloLibros.OBS_CATEGORIAS_MOSTRADAS)){
            Platform.runLater(() -> {
                ventanaCategorias.getChildren().clear();
                for(CategoryData categoria : modelo.getCategoriasMostradas()){
                    SimpleParam simpleParam = new SimpleParam(categoria.getNombre());
                    CheckBox checkBox = simpleParam.getCheckBox();
                    checkBox.setOnAction( e -> modelo.setCategoriaSelected(categoria, checkBox.isSelected()));
                    ventanaCategorias.getChildren().add(checkBox);
                }
            });
        }

        if(evt.getPropertyName().equals(ModeloLibros.OBS_FILTROS_MOSTRADOS)){
            Platform.runLater(() -> {
                ventanaFiltros.getChildren().clear();
                for(IFiltro filtro : modelo.getFiltros()){
                    if(filtro instanceof  FiltroBasico){
                        SimpleParam simpleParam = new SimpleParam(filtro.getNombre());
                        CheckBox checkBox = simpleParam.getCheckBox();
                        checkBox.setOnAction( e -> modelo.setFiltroSelected(filtro, checkBox.isSelected()));
                        ventanaFiltros.getChildren().add(checkBox);
                    }
                    else if(filtro instanceof FiltroFecha){
                        DateParam dateParam = new DateParam((FiltroFecha) filtro);
                        VBox contenedor = dateParam.getContenedor();
                        CheckBox checkBox = dateParam.getCheckBox();
                        dateParam.getInputFechaInicial().textProperty().addListener((observable, oldValue, newValue) -> {
                            Date date = null;
                            if(!newValue.isEmpty() && newValue.matches("\\d*"))  date = Date.valueOf(LocalDate.of(Integer.parseInt(newValue), 1, 1));
                            ((FiltroFecha) filtro).setFechaInicial(date);
                            modelo.anunciarCambio();
                        });
                        dateParam.getInputFechaFinal().textProperty().addListener((observable, oldValue, newValue) -> {
                            Date date = null;
                            if(!newValue.isEmpty() && newValue.matches("\\d*"))  date = Date.valueOf(LocalDate.of(Integer.parseInt(newValue), 1, 1));
                            ((FiltroFecha) filtro).setFechaFinal(date);
                            modelo.anunciarCambio();
                        });
                        checkBox.setOnAction( e ->{
                            dateParam.getGridFechas().setDisable(!checkBox.isSelected());
                            modelo.setFiltroSelected(filtro, checkBox.isSelected());
                        });
                        ventanaFiltros.getChildren().add(contenedor);
                    }

                }
            });
        }
    }

    public VBox getVentanaCategorias() {
        return ventanaCategorias;
    }

    public VBox getVentanaFiltros() {
        return ventanaFiltros;
    }
}
