package com.odvp.biblioteca.main.modulos.defaulltComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DefaultDynamicSearcher extends HBox implements PropertyChangeListener {

    protected TextField buscador;
    protected StackPane iconoContenedor;
    protected ImageView icono;

    private List<TipoBusqueda> tipoBusquedas;
    private TipoBusqueda tipoSeleccionado;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public DefaultDynamicSearcher(){
        support.addPropertyChangeListener(this);
        tipoBusquedas = new ArrayList<>();
        buscador = new TextField();
        buscador.setPromptText("Buscar");
        buscador.scaleYProperty().setValue(1.05);
        buscador.getStyleClass().add("text-field-search");
        icono = new ImageView();
        icono.setFitWidth(15);
        icono.setFitHeight(15);
        iconoContenedor = new StackPane();
        iconoContenedor.setAlignment(Pos.CENTER);
        iconoContenedor.getStyleClass().add("button-search-shape");
        iconoContenedor.setPadding(new Insets(0,8,0,8));
        iconoContenedor.getChildren().add(icono);
        setAlignment(Pos.CENTER);
        getChildren().addAll(buscador, iconoContenedor);
        setButtonAction();
        setBuscadorAction();
    }

    public void addTiposBusqueda(List<TipoBusqueda> tiposDeBusqueda) {
        tipoBusquedas.addAll(tiposDeBusqueda);
    }

    public void setTipoSeleccionado(TipoBusqueda tipoBusqueda){
        tipoSeleccionado = tipoBusqueda;
        icono.setImage(new Image(tipoSeleccionado.getSrcImage()));
        iconoContenedor.getStyleClass().clear();
        iconoContenedor.getStyleClass().addAll("button-search-shape", tipoSeleccionado.getButtonStyle());
        String predeterminado = buscador.getStyleClass().getFirst();
        buscador.getStyleClass().clear();
        buscador.getStyleClass().addAll(predeterminado,"text-field-search", tipoSeleccionado.getTextFieldStyle());
        Tooltip tooltip = new Tooltip(tipoSeleccionado.getPrompt());
        Tooltip.install(iconoContenedor, tooltip);
    }

    public void setBuscadorAction(){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public class TipoBusqueda{
        private String prompt, srcImage, textFieldStyle, buttonStyle;
        public TipoBusqueda(String propmt, String srcImage, String textFieldStyle, String buttonStyle){
            this.prompt = propmt;
            this.srcImage = srcImage;
            this.textFieldStyle = textFieldStyle;
            this.buttonStyle = buttonStyle;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getSrcImage() {
            return srcImage;
        }

        public void setSrcImage(String srcImage) {
            this.srcImage = srcImage;
        }

        public String getTextFieldStyle() {
            return textFieldStyle;
        }

        public void setTextFieldStyle(String textFieldStyle) {
            this.textFieldStyle = textFieldStyle;
        }

        public String getButtonStyle() {
            return buttonStyle;
        }

        public void setButtonStyle(String buttonStyle) {
            this.buttonStyle = buttonStyle;
        }
    }

    public void setButtonAction(){

    }
}