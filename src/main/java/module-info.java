module org.example.biblijava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;

    opens org.example.biblijava to javafx.fxml;
    opens org.example.biblijava.controller to javafx.fxml;
    opens org.example.biblijava.controller.auth to javafx.fxml;
    opens org.example.biblijava.model to javafx.base;

    exports org.example.biblijava;
    exports org.example.biblijava.controller;
    exports org.example.biblijava.controller.auth;
    exports org.example.biblijava.model;
}
