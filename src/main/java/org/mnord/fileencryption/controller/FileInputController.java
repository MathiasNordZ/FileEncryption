package org.mnord.fileencryption.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mnord.fileencryption.logic.CryptoException;
import org.mnord.fileencryption.logic.FileEncryption;
import org.mnord.fileencryption.logic.PasswordHash;

import javax.crypto.SecretKey;
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

    fileButton.setOnAction(event -> {
      File file = fileChooser.showOpenDialog(new Stage());

      if (file != null) {
        selectedFile = file;
        fileLabel.setText(file.getAbsolutePath() + " selected.");
      }
    });

    encryptButton.setOnAction(event -> {
      if (selectedFile != null && !passwordField.getText().isEmpty()) {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.stringToHash(passwordField.getText());
        SecretKey secretKey = passwordHash.getKey();

        FileEncryption encryption = new FileEncryption();
        try {
          encryption.encrypt(selectedFile, secretKey);
          fileLabel.setText("File encrypted successfully");
        } catch (CryptoException e) {
          fileLabel.setText("Error in encryption!");
        }
      } else {
        fileLabel.setText("Please select a file and enter password");
      }
    });

    decryptButton.setOnAction(event -> {
      if (selectedFile != null && !passwordField.getText().isEmpty()) {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.stringToHash(passwordField.getText());
        SecretKey secretKey = passwordHash.getKey();

        FileEncryption decryption = new FileEncryption();
        try {
          decryption.decrypt(selectedFile, secretKey, new File("output"));
          fileLabel.setText("File decrypted successfully!");
        } catch (CryptoException e) {
          fileLabel.setText("Error in decryption!");
        }
      } else {
        fileLabel.setText("Please select a file and enter password");
      }
    });
  }
}
