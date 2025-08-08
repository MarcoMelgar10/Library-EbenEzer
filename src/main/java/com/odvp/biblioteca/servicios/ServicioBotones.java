package com.odvp.biblioteca.servicios;

import com.odvp.biblioteca.main.modulos.defaulltComponents.ButtonDefault;

public class ServicioBotones {

    public static ButtonDefault createBotonAgregar(){
        return new ButtonDefault(ServicioIconos.AGREGAR_BUTTON,"button-yellow");
    }
    public static ButtonDefault createButtonEditar(){
        return  new ButtonDefault(ServicioIconos.EDITAR_BUTTON, "button-blue");
    }
    public static ButtonDefault createButtonVisualizar(){
        return  new ButtonDefault(ServicioIconos.VISUALIZAR_BUTTON, "button-green");
    }
    public static ButtonDefault createButtonEliminar(){
        return  new ButtonDefault(ServicioIconos.ELIMINAR_BUTTON, "button-red");
    }
    public static ButtonDefault createBotonPersonalizado(String iconoURL, String styleClass){
        return new ButtonDefault(iconoURL, styleClass);
    }

}
