module com.odvp.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires java.smartcardio;
    requires jbcrypt;
    requires org.apache.logging.log4j;
    requires com.zaxxer.hikari;
    requires java.sql;

    opens com.odvp.biblioteca to javafx.fxml;
    exports com.odvp.biblioteca;
    exports com.odvp.biblioteca.main.modulos;
    opens com.odvp.biblioteca.main.modulos to javafx.fxml;
    exports com.odvp.biblioteca.main.modulos.libros;
    opens com.odvp.biblioteca.main.modulos.libros to javafx.fxml;
    exports com.odvp.biblioteca.main.modulos.defaulltComponents;
    opens com.odvp.biblioteca.main.modulos.defaulltComponents to javafx.fxml;
    opens com.odvp.biblioteca.main.modulos.usuarios to javafx.fxml;
    exports com.odvp.biblioteca.login;
    opens com.odvp.biblioteca.login to javafx.fxml;
    exports com.odvp.biblioteca.main;
    opens com.odvp.biblioteca.main to javafx.fxml;
}