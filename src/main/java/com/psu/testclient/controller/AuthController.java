package com.psu.testclient.controller;

import com.psu.testclient.Launcher;
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
import org.apache.log4j.Logger;

import java.io.IOException;


public class AuthController {
    private static final Logger log = Logger.getLogger(AuthController.class);
    @FXML
    private Button getTestButton;

    @FXML
    private TextField nameField;

    @FXML
    private Text errorMessage;

    @FXML
    void initialize() {
        this.getTestButton.setOnAction(this::getTestButtonClick);
    }

    private void getTestButtonClick(ActionEvent actionEvent) {
        try {
            tryGetTest();
        } catch (IOException ex) {
            log.error(ex.getMessage());

            errorMessage.setText("Внутренняя ошибка");
            errorMessage.setVisible(true);
        }
    }

    private void tryGetTest() throws IOException {
        if (nameField.getCharacters().isEmpty()) {
            errorMessage.setText("Заполните поле!");
            errorMessage.setVisible(true);
            return;
        }

        viewTestWindow(this.nameField.getText());
    }

    private void viewTestWindow(String name) throws IOException {
        TestController testController = new TestController();
        testController.setStudentName(name);

        Stage stage = (Stage) getTestButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("test-view.fxml"));
        fxmlLoader.setController(testController);
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Test");
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
