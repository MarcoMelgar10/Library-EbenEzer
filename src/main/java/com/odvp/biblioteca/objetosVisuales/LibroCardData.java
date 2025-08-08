package com.odvp.biblioteca.objetosVisuales;

import com.odvp.biblioteca.LibraryApplication;
import javafx.scene.image.Image;

import java.util.List;

/*
    representa la informacion que se verá de un libro en la lista de libros, incluyendo la legenda.
 */

public class LibroCardData implements IDatoVisual {
    private int ID;
    private Image image;
    private String titulo;
    private String autor;
    private int stock;
    private int stockDisponible;

    public Image getImage() {
        return image;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getStock() {
        return stock;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public LibroCardData(int ID, String nombre, String autor, int stock, int stockDisponible) {
        this.ID = ID;
        this.titulo = nombre;
        this.autor = autor;
        this.stock = stock;
        this.stockDisponible = stockDisponible;
        if(stockDisponible == 0){
            image = new Image(LibraryApplication.class.getResource("/com/odvp/biblioteca/Icons/LibrosResources/libro-no-disponible.png").toExternalForm());
        } else {
            image = new Image(LibraryApplication.class.getResource("/com/odvp/biblioteca/Icons/LibrosResources/libro-disponible.png").toExternalForm());
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public List<Object> getDatos() {
        return List.of(image,titulo, autor, stock, stockDisponible);
    }
}
