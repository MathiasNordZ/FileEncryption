module org.mnord.fileencryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.mnord.fileencryption.controller to javafx.fxml;
    exports org.mnord.fileencryption.scene;
  exports org.mnord.fileencryption.logic;
  opens org.mnord.fileencryption.logic to javafx.fxml;
  exports org.mnord.fileencryption.application;
}