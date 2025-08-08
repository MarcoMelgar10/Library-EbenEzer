package com.odvp.biblioteca.main.modulos.autores.OperacionesAutores.VisualizarAutor;

import com.odvp.biblioteca.objetos.Autor;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VisualizarAutorVentana extends Stage {

    private Label nombreField;
    private Label idField;
    private TextArea resenaArea;
    private Button cancelarButton;
    private Autor autor;

    public VisualizarAutorVentana(Autor autor) {
        this.autor = autor;
        setTitle("Visualizar");
        Scene scene = buildScene();
        initValues();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    public Scene buildScene(){
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setPrefWidth(330);
       // root.setSpacing(40);
        Image icon = new Image(ServicioIconos.OPCION_MODULO_AUTORES);
        this.getIcons().add(icon);

        // Título
        Label titleWindow = new Label("Visualizar Autor");
        titleWindow.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 22));
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        // GridPane para los campos del formulario
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label idLabel = new Label("ID:");
        idField = new Label();

        Label nombreLabel = new Label("Nombre:");
        nombreField = new Label();

        Label resenaLabel = new Label("Resena:");
        resenaArea = new TextArea();
        resenaArea.setPromptText("Escriba alguna observacion...");
        resenaArea.setWrapText(true);
        resenaArea.setFont(Font.font("System", javafx.scene.text.FontPosture.ITALIC, 12));
        resenaArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(resenaArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);

        // Agregar elementos al GridPane
        formGrid.addRow(0, idLabel, idField);
        formGrid.addRow(1, nombreLabel, nombreField);
        formGrid.addRow(2, resenaLabel);

        // Contenedor de botones
        cancelarButton = new Button("Cerrar");
        cancelarButton.setOnAction( e -> close());
        HBox buttonsContainer = new HBox(8, cancelarButton);
        buttonsContainer.setAlignment(Pos.CENTER);

        // Agregar elementos al VBox principal
        root.getChildren().addAll(titleContainer, separator, formGrid,scrollPane, buttonsContainer);

        return new Scene(root);
    }

    public void initValues(){
        idField.setText(autor.getID()+"");
        nombreField.setText(autor.getNombre());
        resenaArea.setText(autor.getDescripcion());
    }

}

