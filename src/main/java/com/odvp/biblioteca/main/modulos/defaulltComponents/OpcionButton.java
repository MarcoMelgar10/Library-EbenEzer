package com.odvp.biblioteca.main.modulos.defaulltComponents;


import com.odvp.biblioteca.LibraryApplication;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class OpcionButton extends VBox {

    private ImageView optionIcon;
    private Label optionLabel;
    public OpcionButton(String title, String iconPath) {
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        // Aplicar estilos desde CSS (opcional)
        getStyleClass().add("option-container");
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setPrefSize(80, 110);

        // Configurar imagen
        optionIcon = new ImageView();
        optionIcon.setFitHeight(70);
        optionIcon.setFitWidth(70);
        optionIcon.setPreserveRatio(true);
        optionIcon.setPickOnBounds(true);

        if (iconPath != null && !iconPath.isEmpty()) {
            optionIcon.setImage(new Image(iconPath));
        }

        // Configurar etiqueta
        optionLabel = new Label(title);
        optionLabel.getStyleClass().add("T3");

        // Agregar nodos al VBox
        this.getChildren().addAll(optionIcon, optionLabel);
    }


    public void setSelected(boolean selected) {
        if(selected) getStyleClass().add("option-container-selected");
        else getStyleClass().remove("option-container-selected");

    }
}
