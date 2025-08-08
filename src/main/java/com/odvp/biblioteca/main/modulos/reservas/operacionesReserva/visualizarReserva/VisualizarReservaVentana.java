package com.odvp.biblioteca.main.modulos.reservas.operacionesReserva.visualizarReserva;

import com.odvp.biblioteca.database.daos.LibroDAO;
import com.odvp.biblioteca.database.daos.ReservaDAO;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import com.odvp.biblioteca.objetos.Prestamo;
import com.odvp.biblioteca.objetos.Reserva;
import com.odvp.biblioteca.objetos.Usuario;
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

import java.time.LocalDate;

public class VisualizarReservaVentana extends Stage {
    private final ReservaDAO reservaDAO;
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final Reserva reserva;

    public VisualizarReservaVentana(ReservaDAO reservaDAO, Reserva reserva) {
        this.reservaDAO = reservaDAO;
        this.reserva = reserva;
        this.usuarioDAO = new UsuarioDAO();
        this.libroDAO = new LibroDAO();

        setTitle("Visualizar");
        Scene scene = buildScene();
        setScene(scene);
        centerOnScreen();
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }



    private Scene buildScene() {
        VBox root = new VBox(8);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Image icon = new Image(ServicioIconos.OPCION_MODULO_RESERVAS);
        this.getIcons().add(icon);

        Label titleWindow = new Label("Visualizar Reserva");
        titleWindow.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        StackPane titleContainer = new StackPane(titleWindow);
        titleContainer.setPrefHeight(40);

        Separator separator = new Separator();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        // Convertir fechas para mostrar
        LocalDate fechaReserva = reserva.getFechaReserva() != null
                ? LocalDate.of(
                reserva.getFechaReserva().getYear() + 1900,
                reserva.getFechaReserva().getMonth() + 1,
                reserva.getFechaReserva().getDate()
        )
                : null;

        LocalDate fechaVencimiento = reserva.getFechaVencimiento() != null
                ? LocalDate.of(
                reserva.getFechaVencimiento().getYear() + 1900,
                reserva.getFechaVencimiento().getMonth() + 1,
                reserva.getFechaVencimiento().getDate()
        )
                : null;

        LocalDate fechaRecogida = reserva.getFechaRecogida() != null
                ? LocalDate.of(
                reserva.getFechaRecogida().getYear() + 1900,
                reserva.getFechaRecogida().getMonth() + 1,
                reserva.getFechaRecogida().getDate()
        )
                : null;

        // Crear componentes de solo lectura
        Label idReservaLabel = new Label(String.valueOf(reserva.getIdReserva()));
        Label usuarioIdLabel = new Label(String.valueOf(reserva.getIdUsuario()));
        Label libroIdLabel = new Label(String.valueOf(reserva.getIdLibro()));
        Usuario usuario =  usuarioDAO.obtener(reserva.getIdUsuario());
        Label nombreUsuario = new Label(usuario.getNombre() + " " +
                usuario.getApellidoPaterno() + " " +
                usuario.getApellidoMaterno());
        Label tituloLibro = new Label(libroDAO.obtener(reserva.getIdLibro()).getTitulo());
        Label fechaReservaLabel = new Label(fechaReserva != null ? fechaReserva.toString() : "N/A");
        Label fechaVencimientoLabel = new Label(fechaVencimiento != null ? fechaVencimiento.toString() : "N/A");
        Label fechaRecogidaLabel = new Label(fechaRecogida != null ? fechaRecogida.toString() : "N/A");
        Label estadoReservaLabel = new Label(reserva.getEstado());

        TextArea observacionTextArea = new TextArea(reserva.getObservacion());
        observacionTextArea.setEditable(false);
        observacionTextArea.setWrapText(true);
        observacionTextArea.setFont(Font.font("System", javafx.scene.text.FontPosture.ITALIC, 12));
        ScrollPane scrollPane = new ScrollPane(observacionTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);

        // Agregar filas al grid
        formGrid.addRow(0, new Label("ID Reserva:"), idReservaLabel);
        formGrid.addRow(1, new Label("ID Usuario:"), usuarioIdLabel);
        formGrid.addRow(2, new Label("Nombre usuario:"), nombreUsuario);
        formGrid.addRow(3, new Label("ID Libro:"), libroIdLabel);
        formGrid.addRow(4, new Label("Título Libro:"), tituloLibro);
        formGrid.addRow(5, new Label("Fecha Reserva:"), fechaReservaLabel);
        formGrid.addRow(6, new Label("Fecha Vencimiento:"), fechaVencimientoLabel);
        formGrid.addRow(7, new Label("Fecha Recogida:"), fechaRecogidaLabel);
        formGrid.addRow(8, new Label("Estado:"), estadoReservaLabel);
        formGrid.addRow(9, new Label("Observaciones:"));

        Button cerrarButton = new Button("Cerrar");
        cerrarButton.setOnAction(e -> close());

        HBox buttonsContainer = new HBox(8, cerrarButton);
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);

        root.getChildren().addAll(titleContainer, separator, formGrid, scrollPane, buttonsContainer);
        return new Scene(root, 400, 550);
    }
}