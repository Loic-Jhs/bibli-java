module org.example.biblijava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires org.apache.poi.ooxml;
    requires java.sql;

    opens org.example.biblijava to javafx.fxml;
    opens org.example.biblijava.controller.auth to javafx.fxml;
    exports org.example.biblijava;
    exports org.example.biblijava.controller;
    opens org.example.biblijava.controller to javafx.fxml;
}