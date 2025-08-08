package com.odvp.biblioteca.main.modulos.defaulltComponents;

import com.odvp.biblioteca.LibraryApplication;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class ButtonDefault extends StackPane {

    public ButtonDefault(String icono, String styleclass){
        setPrefWidth(50);
        setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image(icono));
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        getChildren().add(imageView);
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        getStyleClass().addAll("button-shape");
        getStyleClass().add(styleclass);
    }


    public void desactivar(boolean deshabilitar){
        setDisable(deshabilitar);

        if(deshabilitar){
            getStyleClass().add("button-disable");
        }
        else {
            getStyleClass().remove("button-disable");
        }
    }

}