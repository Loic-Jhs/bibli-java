module org.example.biblijava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.biblijava to javafx.fxml;
    exports org.example.biblijava;
}