package com.psu.testclient.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.psu.testclient.Launcher;
import com.psu.testclient.client.Client;
import com.psu.testclient.model.QuestionModel;
import com.psu.testclient.model.TestModel;
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

import java.io.IOException;
import java.util.List;


public class AuthController {

    @FXML
    private Button getTestButton;

    @FXML
    private TextField nameField;

    @FXML
    private Text errorMessage;

    private Client client;

    @FXML
    void initialize() {
        this.client = new Client();
        try{
            client.initConnection("127.0.0.1", 3384);
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        this.getTestButton.setOnAction(this::getTestButtonClick);

    }

    private void getTestButtonClick(ActionEvent actionEvent) {
        try {
            getTest();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());

            errorMessage.setText("Внутренняя ошибка");
            errorMessage.setVisible(true);
        }
    }

    void getTest() throws IOException {
        if (nameField.getCharacters().isEmpty()) {
            errorMessage.setText("Заполните поле!");
            errorMessage.setVisible(true);

        } else {
            String testJson = this.client.requestWithResponse("GET/testService/getTest");

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            TestModel testModel = gson.fromJson(testJson, TestModel.class);

            TestController testController = new TestController();
            testController.setQuestionModels( testModel.questions);

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
}
