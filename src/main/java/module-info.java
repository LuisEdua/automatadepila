module com.example.automatadepila {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.automatadepila to javafx.fxml;
    exports com.example.automatadepila;
    exports com.example.automatadepila.controllers;
    opens com.example.automatadepila.controllers to javafx.fxml;
}