package org.mnord.fileencryption.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class FileInput extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/mnord/fileencryption/fileinput-view.fxml")));
    Scene scene = new Scene(root, 1440, 1024);
    scene.getStylesheets().add("/org/mnord/fileencryption/style.css");
    stage.setTitle("File Input");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
