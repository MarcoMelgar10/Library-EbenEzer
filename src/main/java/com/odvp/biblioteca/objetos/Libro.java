package com.odvp.biblioteca.objetos;

import java.sql.Date;

/*
    clase que representa libros mediante objetos, usa builder pattern,se usa para visualizar
    mejor la creacion de un objeto cuando su constructor recibe demasiado parametros, no se abrume Marquino
 */

public class Libro {
    private int ID;
    private String titulo;
    private String observacion;
    private Date publicacion;
    private int stock;
    private int stockDisponible;
    private String nombreCategoria;
    private String nombreAutor;
    private String nombreSubCategoria;
    private int idAutor;
    private int idCategoria;
    private int idSubCategoria;

    public Libro(Builder builder){
        this.ID = builder.ID;
        this.titulo = builder.titulo;
        this.observacion = builder.observacion;
        this.publicacion = builder.publicacion;
        this.stock = builder.stock;
        this.stockDisponible = builder.stockDisponible;
        this.nombreCategoria = builder.categoria;
        this.nombreAutor = builder.autor;
        this.nombreSubCategoria = builder.subCategoria;
        this.idAutor = builder.idAutor;
        this.idCategoria = builder.idCategoria;
        this.idSubCategoria = builder.idSubCategoria;
    }

    public String getNombreSubCategoria() {
        return nombreSubCategoria;
    }

    public void setNombreSubCategoria(String nombreSubCategoria) {
        this.nombreSubCategoria = nombreSubCategoria;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getPublicacion() {
         return publicacion;
    }

    public void setPublicacion(Date publicacion) {
        this.publicacion = publicacion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        nombreCategoria = nombreCategoria;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public static class Builder {
        private int ID;
        private String titulo;
        private String observacion;
        private Date publicacion;
        private int stock;
        private int stockDisponible;
        private String categoria;
        private int idCategoria;
        private String autor;
        private int idAutor;
        private String subCategoria;
        private int idSubCategoria;

        public Builder ID(int ID) {
            this.ID = ID;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder observacion(String observacion) {
            this.observacion = observacion;
            return this;
        }

        public Builder publicacion(Date publicacion) {
            this.publicacion = publicacion;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder stockDisponible(int stockDisponible) {
            this.stockDisponible = stockDisponible;
            return this;
        }

        public Builder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder autor(String autor) {
            this.autor = autor;
            return this;
        }
        public Builder subCategoria(String subCategoria){
           this.subCategoria = subCategoria;
            return this;
        }
        public Builder idAutor(int idAutor){
            this.idAutor = idAutor;
            return this;
        }
        public Builder idCategoria(int idCategoria){
            this.idCategoria = idCategoria;
            return this;
        }
        public Builder idSubCategoria(int idSubCategoria){
            this.idSubCategoria = idSubCategoria;
            return this;
        }

        public Libro build() {
            return new Libro(this);
        }


    }
}
