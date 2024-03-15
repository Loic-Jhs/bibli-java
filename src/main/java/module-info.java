module org.example.biblijava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens org.example.biblijava to javafx.fxml;
    exports org.example.biblijava;
    exports org.example.biblijava.controller;
    opens org.example.biblijava.controller to javafx.fxml;
}