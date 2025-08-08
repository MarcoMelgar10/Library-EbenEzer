package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.main.modulos.IModulo;
import com.odvp.biblioteca.database.daos.UsuarioDAO;
import javafx.scene.layout.BorderPane;


public class ModuloUsuarios extends BorderPane implements IModulo {
    private HeaderUsuarios header;
    private TableUsuarios table;
    private ModeloUsuarios modelo;
    private ServicioBusquedaUsuarios servicioBusquedaUsuarios;
    public ModuloUsuarios(ModeloUsuarios modelo){
        this.modelo = modelo;
        header = new HeaderUsuarios(this.modelo);
        table = new TableUsuarios(this.modelo);
        servicioBusquedaUsuarios= new ServicioBusquedaUsuarios(modelo);
        setTop(header);
        setCenter(table);
        cargarDatosIniciales();
    }


    @Override
    public void cargarDatosIniciales(){
        new Thread(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            modelo.setUsuariosMostrados(usuarioDAO.listaUsuarios());
        }).start();
    }
}
