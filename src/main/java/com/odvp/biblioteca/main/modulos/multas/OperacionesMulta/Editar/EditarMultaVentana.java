package com.odvp.biblioteca.main.modulos.multas.OperacionesMulta.Editar;

import com.odvp.biblioteca.objetos.Multa;
import com.odvp.biblioteca.servicios.ServicioIconos;
import com.odvp.biblioteca.database.daos.MultaDAO;
import com.odvp.biblioteca.database.daos.PrestamoDAO;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;


public class EditarMultaVentana extends Stage {
    private Spinner<Integer> montoSpinner;
    private TextField descripcionField;
    private Label codigoLabel, usuarioLabel, libroLabel, codigoPrestamo;
    private Button cancelarButton, aceptarButton;
    private Multa multa;
    private final MultaDAO multaDao;
    private PrestamoDAO prestamoDAO;
    private DatePicker fechaMulta;
    private boolean hubieronCambios = false;
    private static Logger log = LogManager.getRootLogger();



    public EditarMultaVentana(MultaDAO multaDAO, Multa multaInicial){
        this.multaDao = multaDAO;
        multa = multaInicial;
        prestamoDAO = new PrestamoDAO();
        setTitle("Editar");
        Scene scene = buildScene();
        Image icon = new Image(ServicioIconos.OPCION_MODULO_MULTAS);
        this.getIcons().add(icon);
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

        Label titleWindow = new Label("Multa");
        titleWindow.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, javafx.scene.text.FontPosture.ITALIC, 22));
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label codigoLabel = new Label("Codigo:");
        this.codigoLabel = new Label("");
        this.codigoLabel.setPrefWidth(50);

        Label descripcionLabel = new Label("Descripcion:");
        descripcionField = new TextField();

        Label codigoPrestamoLabel = new Label("Prestamo:");
        codigoPrestamo = new Label();
        codigoPrestamo.setPrefWidth(70);

        //Fecha
        Label fechaMultaLabel = new Label("Fecha multa: ");
        fechaMulta = new DatePicker();
        fechaMulta.setPrefWidth(120);


        Label nombreUsuario = new Label("Usuario:");
        usuarioLabel = new Label("");
        usuarioLabel.setPrefWidth(100);


        Label nombreLibro = new Label("Libro:");
        libroLabel = new Label("");
        libroLabel.setPrefWidth(200);

        Label montoLabel = new Label("Monto:");
        montoSpinner = new Spinner<>(1, 10000, 1);
        montoSpinner.setEditable(true);
        montoSpinner.setPrefWidth(70);


        montoSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Permite solo números
                montoSpinner.getEditor().setText(oldValue);
            }
        });

        // Agregar elementos al GridPane
        formGrid.addRow(0, codigoLabel, this.codigoLabel);
        formGrid.addRow(1, codigoPrestamoLabel, codigoPrestamo);
        formGrid.addRow(2, nombreUsuario, usuarioLabel);
        formGrid.addRow(3, nombreLibro, libroLabel);
        formGrid.addRow(4, descripcionLabel,descripcionField);
        formGrid.addRow(5, fechaMultaLabel, fechaMulta);
        formGrid.addRow(6, montoLabel, montoSpinner);


        // Contenedor de botones
        cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction( e -> close());
        aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction( e-> {
            if(validar()) ejecutar();
            else log.error("Error al obtener los datos");
        });
        HBox buttonsContainer = new HBox(10, cancelarButton, aceptarButton);
        VBox.setMargin(buttonsContainer, new Insets(30, 0, 0, 0));
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);

        // Agregar elementos al VBox principal
        root.getChildren().addAll(titleContainer, separator, formGrid, buttonsContainer);

        return new Scene(root, 350, 350);
    }

    public void initValues() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String usuarioName = prestamoDAO.getUsuario(multa.getIdPrestamo());
                String tituloLibro = prestamoDAO.getLibro(multa.getIdPrestamo());

                Platform.runLater(() -> {
                    codigoLabel.setText(String.valueOf(multa.getIdMulta()));
                    usuarioLabel.setText(usuarioName);
                    libroLabel.setText(tituloLibro);
                    descripcionField.setText(multa.getDescripcion());
                    montoSpinner.getValueFactory().setValue(multa.getMonto());
                    fechaMulta.setValue(multa.getFechaMulta().toLocalDate());
                    codigoPrestamo.setText(String.valueOf(multa.getIdPrestamo()));
                });

                return null;
            }
        };

        new Thread(task).start();
    }


    public boolean validar() {
        return montoSpinner.getValue() != null &&
                montoSpinner.getValue() > 0 &&
                fechaMulta.getValue() != null &&
                !fechaMulta.getValue().isAfter(LocalDate.now());
    }


    public void ejecutar(){
        int monto = montoSpinner.getValue();
        String descripcion = descripcionField.getText().isEmpty() ? "" : descripcionField.getText();
        Date fecha = fechaMulta.getValue() != null ? Date.valueOf(fechaMulta.getValue()) : null;
        multa = new Multa(multa.getIdMulta(), descripcion, monto, fecha, true, null, false, multa.getIdPrestamo());
        multaDao.modificar(multa);
        hubieronCambios = true;
        close();
    }

    public boolean isHubieronCambios() {
        return hubieronCambios;
    }
}
