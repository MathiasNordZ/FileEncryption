module org.mnord.fileencryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.mnord.fileencryption to javafx.fxml;
    exports org.mnord.fileencryption;
}