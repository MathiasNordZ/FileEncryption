package org.mnord.fileencryption.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mnord.fileencryption.logic.CryptoException;
import org.mnord.fileencryption.logic.FileEncryption;
import java.io.File;

public class FileInputController {
  @FXML
  private Label fileLabel;
  @FXML
  private Button fileButton;
  @FXML
  private TextField passwordField;
  @FXML
  private Button encryptButton;
  @FXML
  private Button decryptButton;

  private File selectedFile;

  @FXML
  public void initialize() {
    FileChooser fileChooser = new FileChooser();

    selectFile(fileChooser);
    encrypt();
    decrypt();
  }

  private void selectFile(FileChooser fileChooser) {
    fileButton.setOnAction(event -> {
      File file = fileChooser.showOpenDialog(new Stage());

      if (file != null) {
        selectedFile = file;
        fileLabel.setText(file.getAbsolutePath() + " selected.");
      }
    });
  }

  private void decrypt() {
    decryptButton.setOnAction(event -> {
      if (selectedFile != null && !passwordField.getText().isEmpty()) {
        String secretKey = passwordField.getText();

        FileEncryption decryption = new FileEncryption();
        try {
          File outputFile = new File(selectedFile.getParent(), "decrypted_" + selectedFile.getName());
          decryption.decrypt(secretKey, selectedFile, outputFile);
          fileLabel.setText("File decrypted successfully!");
        } catch (CryptoException e) {
          System.out.println(e.getMessage());
          fileLabel.setText("Error in decryption!");
        }
      } else {
        fileLabel.setText("Please select a file and enter password");
      }
    });
  }

  private void encrypt() {
    encryptButton.setOnAction(event -> {
      if (selectedFile != null && !passwordField.getText().isEmpty()) {
        String secretKey = passwordField.getText();

        FileEncryption encryption = new FileEncryption();
        try {
          File outputFile = new File(selectedFile.getParent(), "encrypted_" + selectedFile.getName());
          encryption.encrypt(secretKey, selectedFile, outputFile);
          fileLabel.setText("File encrypted successfully");
        } catch (CryptoException e) {
          System.out.println(e.getMessage());
          fileLabel.setText("Error in encryption!");
        }
      } else {
        fileLabel.setText("Please select a file and enter password");
      }
    });
  }
}
