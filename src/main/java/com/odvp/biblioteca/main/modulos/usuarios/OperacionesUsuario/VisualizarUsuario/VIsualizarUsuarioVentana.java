package com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.VisualizarUsuario;

import com.odvp.biblioteca.objetos.Usuario;
import com.odvp.biblioteca.servicios.ServicioIconos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VIsualizarUsuarioVentana extends Stage {

    private Label nombreField, apellidoPaternoField, apellidoMaternoField, telefonoField, direccionField, idField;
    private Button aceptarButton;
    private Usuario usuario;
    private boolean hubieronCambios = false;

    public VIsualizarUsuarioVentana(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Modificar Usuario");
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
        root.setSpacing(40);

        Image icon = new Image(ServicioIconos.OPCION_MODULO_USUARIOS);
        this.getIcons().add(icon);

        // Título
        Label titleWindow = new Label("Visualizar Usuario");
        titleWindow.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 22));
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        // GridPane para los campos del formulario
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label idLabel = new Label("CI:");
        idField = new Label();

        Label nombreLabel = new Label("Nombre:");
        nombreField = new Label();

        Label apellidoPaternoLabel = new Label("Apellido Pat.:");
        apellidoPaternoField = new Label();


        Label apellidoMaternoLabel = new Label("Apellido Mat.:");
        apellidoMaternoField = new Label();

        Label telefonoLabel = new Label("Telefono:");
        telefonoField = new Label();

        Label direccionLabel = new Label("Dirección:");
        direccionField = new Label();

        // Agregar elementos al GridPane
        formGrid.addRow(0, idLabel, idField);
        formGrid.addRow(1, nombreLabel, nombreField);
        formGrid.addRow(2, apellidoPaternoLabel, apellidoPaternoField);
        formGrid.addRow(3, apellidoMaternoLabel, apellidoMaternoField);
        formGrid.addRow(4, telefonoLabel, telefonoField);
        formGrid.addRow(5, direccionLabel, direccionField);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setMinWidth(100);
        formGrid.getColumnConstraints().add(columnConstraints);
        // Contenedor de botones
        aceptarButton = new Button("Cerrar");
        aceptarButton.setOnAction(e -> close());
        HBox buttonsContainer = new HBox(8, aceptarButton);
        buttonsContainer.setAlignment(Pos.CENTER);

        // Agregar elementos al VBox principal
        root.getChildren().addAll(titleContainer, separator, formGrid, buttonsContainer);

        return new Scene(root);
    }

    public void initValues(){
        idField.setText(Integer.toString(usuario.getId()));
        nombreField.setText(usuario.getNombre());
        apellidoPaternoField.setText(usuario.getApellidoPaterno());
        apellidoMaternoField.setText(usuario.getApellidoMaterno());
        telefonoField.setText(usuario.getTelefono());
        direccionField.setText(usuario.getDireccion());
    }

}

