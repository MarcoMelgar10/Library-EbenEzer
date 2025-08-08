package com.odvp.biblioteca.objetos;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String direccion;
    private boolean estadoBloqueo;


    public Usuario(Builder builder) {
        this.id = builder.idUsuario;
        this.nombre = builder.nombre;
        this.apellidoPaterno = builder.apellidoPaterno;
        this.apellidoMaterno = builder.apellidoMaterno;
        this.telefono = builder.telefono;
        this.direccion = builder.direccion;
        this.estadoBloqueo = builder.estadoBloqueo;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {return apellidoMaterno;}

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public boolean isEstadoBloqueo() {
        return estadoBloqueo;
    }

    // Clase Builder
    public static class Builder {
        private int idUsuario;
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String telefono;
        private String direccion;
        private boolean estadoBloqueo;

        // Métodos de configuración para cada campo
        public Builder idUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
            return this;
        }
        public Builder apellidoMaterno(String apellidoMaterno){
            this.apellidoMaterno = apellidoMaterno;
            return this;
        }


        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }


        public Builder estadoBloqueo(boolean estadoBloqueo) {
            this.estadoBloqueo = estadoBloqueo;
            return this;
        }

        // Método para construir el objeto Usuario
        public Usuario build() {
            return new Usuario(this);
        }
    }
}
