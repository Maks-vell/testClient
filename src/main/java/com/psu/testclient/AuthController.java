package com.psu.testclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;

public class AuthController {

    @FXML
    private Button getTestButton;

    @FXML
    private TextField nameField;

    @FXML
    private Text errorMessage;

    @FXML
    void initialize() {

        getTestButton.setOnAction(event -> {
            try {
                GetTest(event);
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());

                errorMessage.setText("Внутренняя ошибка");
                errorMessage.setVisible(true);
            }
        });

    }

    void GetTest(ActionEvent event) throws IOException {
        if (nameField.getCharacters().isEmpty()) {
            errorMessage.setText("Заполните поле!");
            errorMessage.setVisible(true);

        } else {
            Stage stage = (Stage) getTestButton.getScene().getWindow();

            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("test-view.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Test");
            stage.setScene(new Scene(root1));
            stage.show();
        }
    }
}
