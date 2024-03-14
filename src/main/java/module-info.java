module org.example.biblijava {
    exports org.example.biblijava.model; // Export du package contenant la classe Book

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.biblijava to javafx.fxml;
    exports org.example.biblijava;
    exports org.example.biblijava.controller;
    opens org.example.biblijava.controller to javafx.fxml;
}