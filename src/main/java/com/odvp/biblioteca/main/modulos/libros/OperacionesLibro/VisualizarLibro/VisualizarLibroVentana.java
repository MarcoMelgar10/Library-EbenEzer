package com.odvp.biblioteca.main.modulos.libros.OperacionesLibro.VisualizarLibro;

import com.odvp.biblioteca.objetos.Libro;
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

public class VisualizarLibroVentana extends Stage {
    public VisualizarLibroVentana(Libro libro) {

        setTitle("Visualizar Libro");

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        // Título
        Label titleWindow = new Label("Visualizar Libro");
        titleWindow.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 22));
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);
        Image icon = new Image(ServicioIconos.OPCION_MODULO_LIBROS);
        this.getIcons().add(icon);

        Separator separator = new Separator();

        // GridPane para los campos del formulario
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label idLabel = new Label("ID:");
        Label idField = new Label(libro.getID() + "");
        idField.setPrefWidth(50);

        Label titleLabel = new Label("Titulo:");
        Label titleField = new Label(libro.getTitulo());

        Label autorLabel = new Label("Autor:");
        Label autorField = new Label(libro.getNombreAutor());
        autorField.setPrefWidth(150);

        Label categoriaLabel = new Label("Categoria:");
        Label categoriaComboBox = new Label(libro.getNombreCategoria());
        categoriaComboBox.setPrefWidth(150);

        Label subCategoriaLabel = new Label("Categoria:");
        Label subCategoriaComboBox = new Label(libro.getNombreSubCategoria());
        categoriaComboBox.setPrefWidth(150);

        Label fechaLabel = new Label("Fecha de publicación:");
        Label publicacionDatePicker = new Label(libro.getPublicacion() +"");
        publicacionDatePicker.setPrefWidth(125);

        Label stockLabel = new Label("Stock:");
        Label stockSpinner = new Label(libro.getStock() +"");
        stockSpinner.setPrefWidth(70);

        Label disponibleLabel = new Label("Stock disponible:");
        Label disponibleSpinner = new Label(libro.getStockDisponible() + "");
        disponibleSpinner.setPrefWidth(70);

        Label observacionLabel = new Label("Observacion:");
        TextArea observacionTextArea = new TextArea();
        observacionTextArea.setText(libro.getObservacion());
        observacionTextArea.setWrapText(true);
        observacionTextArea.setFont(Font.font("System", javafx.scene.text.FontPosture.ITALIC, 12));
        ScrollPane scrollPane = new ScrollPane(observacionTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);
        observacionTextArea.setEditable(false);

        // Agregar elementos al GridPane
        formGrid.addRow(0, idLabel, idField);
        formGrid.addRow(1, titleLabel, titleField);
        formGrid.addRow(2, autorLabel, autorField);
        formGrid.addRow(3, categoriaLabel, categoriaComboBox);
        formGrid.addRow(4, subCategoriaLabel, subCategoriaComboBox);
        formGrid.addRow(5, fechaLabel, publicacionDatePicker);
        formGrid.addRow(6, stockLabel, stockSpinner);
        formGrid.addRow(7, disponibleLabel, disponibleSpinner);
        formGrid.addRow(8, observacionLabel);

        // Contenedor de botones
        Button aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction(e -> close());
        HBox buttonsContainer = new HBox(8, aceptarButton);
        buttonsContainer.setAlignment(Pos.CENTER);

        // Agregar elementos al VBox principal
        root.getChildren().addAll(titleContainer, separator, formGrid, scrollPane, buttonsContainer);

        Scene scene = new Scene(root, 330, 550);
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }
}

