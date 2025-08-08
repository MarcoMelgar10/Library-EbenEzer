package com.odvp.biblioteca.main.modulos.defaulltComponents;

import com.odvp.biblioteca.objetosVisuales.IDatoVisual;
import com.odvp.biblioteca.LibraryApplication;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class TableDefault extends VBox implements PropertyChangeListener {

    private VBox cardsPane;
    private List<Card> cards = new ArrayList<>();
    private List<ColumnConstraints> columnConstraints;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static Logger log = LogManager.getRootLogger();

    public TableDefault(List<String> titulos, List<Integer> ancho, List<Boolean> seExpanden, List<Boolean> centrar) {
        getStylesheets().add(LibraryApplication.class.getResource("Styles/Styles.css").toExternalForm());
        this.columnConstraints = new ArrayList<>();
        GridPane headerGrid = new GridPane();
        headerGrid.setPadding(new Insets(0, 18, 0, 8));
        headerGrid.setMaxHeight(20);
        support.addPropertyChangeListener(this);

        for (int i=0;i<titulos.size(); i++) {
            ColumnConstraints cc = new ColumnConstraints(ancho.get(i));
            cc.setMaxWidth(USE_COMPUTED_SIZE);
            if(seExpanden.get(i)) cc.setPercentWidth(-1);
            else cc.setPercentWidth(0);
            if(centrar.get(i)) cc.setHalignment(HPos.CENTER);
            cc.setHgrow(Priority.SOMETIMES);
            cc.setFillWidth(true);
            headerGrid.getColumnConstraints().add(cc);
            Label label = new Label(titulos.get(i));
            label.getStyleClass().add("negrita");
            headerGrid.add(label, i,0);
            this.columnConstraints.add(cc);
        }
        cardsPane = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(cardsPane);
        scrollPane.getStyleClass().add("scrollPane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        setVgrow(scrollPane, Priority.ALWAYS);
        getChildren().addAll(headerGrid, scrollPane);
    }


    public void addCards(List<IDatoVisual> datos) {
        // Crear el indicador de carga (círculo giratorio)
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(25, 25);

        // Asegurar que la UI se actualiza incluso si la pestaña no está visible
        Platform.runLater(() -> {
            StackPane progressContainer = new StackPane(progressIndicator);
            setVgrow(progressContainer, Priority.ALWAYS);
            progressContainer.setAlignment(Pos.CENTER);
            cardsPane.getChildren().setAll(progressContainer);
        });

        Task<List<Card>> task = getListTask(datos);

        new Thread(task).start();
    }

    private Task<List<Card>> getListTask(List<IDatoVisual> datos) {
        Task<List<Card>> task = new Task<>() {
            @Override
            protected List<Card> call() throws Exception {
                List<Card> nuevasCards = new ArrayList<>();
                for (IDatoVisual dato : datos) {
                    nuevasCards.add(new Card(dato));
                }
                return nuevasCards;
            }
        };

        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                cardsPane.getChildren().clear();
                cards.clear();
                cards.addAll(task.getValue());

                for (Card card : cards) {
                    cardsPane.getChildren().add(card.getVista());
                }

                setCardsAction();
            });
        });

        task.setOnFailed(event -> {
            Platform.runLater(() -> cardsPane.getChildren().setAll(new Label("Error al cargar datos")));
            Platform.runLater(()-> log.error(task.getException().getMessage()));
        });
        return task;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public List<Card> getCards() {
        return cards;
    }

    public abstract void setCardsAction();


    public class Card{
        private IDatoVisual datoVisual;
        private GridPane vista;
        public Card(IDatoVisual dato){
            datoVisual= dato;
            GridPane bookGrid = new GridPane();
            List<Node> elementos = new ArrayList<>();
            for(Object elemento : dato.getDatos()){
                if(elemento instanceof String){
                    elementos.add(new Label((String) elemento));
                } else if (elemento instanceof  Integer) {
                    elementos.add(new Label(elemento + ""));
                } else if(elemento instanceof Image){
                    ImageView image = new ImageView((Image) elemento);
                    image.setFitHeight(32);
                    image.setFitWidth(32);
                    elementos.add(image);
                }
                else if(elemento instanceof Date){
                    elementos.add(new Label(elemento.toString()));
                }
            }
            int nroElemento = 0;
            for (ColumnConstraints cc : columnConstraints) {
                bookGrid.getColumnConstraints().add(cc);
                bookGrid.add(elementos.get(nroElemento), nroElemento, 0);
                nroElemento++;
            }
            bookGrid.getStyleClass().add("card");
            bookGrid.setPadding(new Insets(8,8,8,8));
            vista = bookGrid;
        }

        public void setSelected(boolean selected){
            if(selected) getVista().getStyleClass().add("selected-card");
            else getVista().getStyleClass().remove("selected-card");
        }

        public IDatoVisual getDatoVisual() {
            return datoVisual;
        }

        public GridPane getVista() {
            return vista;
        }


    }
}
