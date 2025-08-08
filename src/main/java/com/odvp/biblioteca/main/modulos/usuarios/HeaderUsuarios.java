package com.odvp.biblioteca.main.modulos.usuarios;

import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;
import com.odvp.biblioteca.main.modulos.defaulltComponents.HeaderDefault;
import com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.AgregarUsuario.AgregarUsuario;
import com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.EditarUsuario.EditarUsuario;
import com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.EliminarUsuario.EliminarUsuario;
import com.odvp.biblioteca.main.modulos.usuarios.OperacionesUsuario.VisualizarUsuario.VisualizarUsuario;
import com.odvp.biblioteca.servicios.ServicioBotones;

import java.beans.PropertyChangeEvent;

public class HeaderUsuarios extends HeaderDefault {
    private final ButtonDefault buttonNew = ServicioBotones.createBotonAgregar();
    private final ButtonDefault buttonEdit = ServicioBotones.createButtonEditar();
    private final ButtonDefault buttonView = ServicioBotones.createButtonVisualizar();
    private final ButtonDefault buttonDelete = ServicioBotones.createButtonEliminar();

    private final SearcherUsuario searcher;
    private ModeloUsuarios modelo;

    public HeaderUsuarios(ModeloUsuarios modelo) {
        super("Usuarios");
        this.modelo = modelo;
        this.modelo.addObserver(this);
        searcher = new SearcherUsuario(modelo);
        buttonNew.setOnMouseClicked(e-> new AgregarUsuario(modelo));
        buttonEdit.setOnMouseClicked(e -> new EditarUsuario(modelo));
        buttonView.setOnMouseClicked(e->new VisualizarUsuario(modelo));
        buttonDelete.setOnMouseClicked(e->new EliminarUsuario(modelo));
        addButtons(buttonNew, buttonView, buttonEdit, buttonDelete);
        setSearcherContainer(searcher);


    }

    @Override
    public void deshabilitarBotones(boolean deshabilitar){

        buttonEdit.desactivar(deshabilitar);
        buttonView.desactivar(deshabilitar);
        buttonDelete.desactivar(deshabilitar);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModeloUsuarios.OBS_USUARIO_SELECCIONADO)){
            if(evt.getOldValue() != null && evt.getNewValue() != null) return;
            deshabilitarBotones(evt.getNewValue() == null);
        }
    }
}
