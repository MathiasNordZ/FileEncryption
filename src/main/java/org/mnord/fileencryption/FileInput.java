package org.mnord.fileencryption;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileInput extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    try {
      stage.setTitle("File Input");

      FileChooser fileChooser = new FileChooser();

      Label noFiles = new Label("No files selected!");
      Button button = new Button("Browse Files");

      EventHandler<ActionEvent> fileEvent = actionEvent -> {
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
          noFiles.setText(file.getAbsolutePath() + " selected.");
        }
      };
      button.setOnAction(fileEvent);

      Button buttonOne = new Button("Save");

      EventHandler<ActionEvent> eventOne = actionEvent -> {
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
          noFiles.setText(file.getAbsolutePath() + " selected.");
        }
      };
      buttonOne.setOnAction(eventOne);

      TextField password = new TextField();
      password.setPromptText("Enter password for encryption");

      Button encryptButton = new Button("Encrypt");



      VBox vbox = new VBox(30, noFiles, button, buttonOne, password, encryptButton);
      vbox.setAlignment(Pos.CENTER);

      Scene scene = new Scene(vbox, 800, 500);
      stage.setScene(scene);

      stage.show();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
