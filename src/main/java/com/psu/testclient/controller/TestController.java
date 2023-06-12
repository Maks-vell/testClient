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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class TestController {
    private static final Logger log = Logger.getLogger(TestController.class);
    @Setter
    private String studentName;

    @FXML
    private Text questionField;

    @FXML
    private Text answerField;

    @FXML
    private Text questionNumeration;

    @FXML
    private Button nextButton;

    @FXML
    private Button viewAnswerButton;

    @FXML
    private Button prevButton;

    private List<QuestionModel> questionModels;

    private Client client;

    private QuestionModel currentQuestionModel;

    private int currentQuestion;

    @FXML
    void initialize() {
        clientInitialize();
        TestModel testModel = getTestModel();
        this.questionModels = testModel.questions;

        if (testModel.isWithAnswers) {
            this.viewAnswerButton.setVisible(true);
            this.viewAnswerButton.setOnAction(this::viewAnswerButtonClick);
        } else {
            this.viewAnswerButton.setVisible(false);
        }

        this.currentQuestionModel = questionModels.get(0);
        currentQuestion = 0;
        questionUpdate();


        this.nextButton.setOnAction(this::nextButtonClick);
        this.prevButton.setOnAction(this::prevButtonClick);
    }

    private void clientInitialize() {
        this.client = new Client();
        try {
            client.initConnection("127.0.0.1", 3384);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private TestModel getTestModel() {
        String testJson = "";
        try {
            testJson = this.client.requestWithResponse(String.format("GET/testService/getTest/%s", this.studentName));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.fromJson(testJson, TestModel.class);
    }

    private void viewAnswerButtonClick(ActionEvent actionEvent) {
        if (this.currentQuestion == this.questionModels.size()) {
            return;
        }

        this.answerField.setText(this.currentQuestionModel.answer);
    }

    private void prevButtonClick(ActionEvent actionEvent) {
        if (this.currentQuestion <= 0) {
            return;
        }

        this.currentQuestion--;
        this.currentQuestionModel = questionModels.get(currentQuestion);
        questionUpdate();
    }

    private void nextButtonClick(ActionEvent actionEvent) {
        this.currentQuestion++;
        if (this.currentQuestion == this.questionModels.size()) {
            viewCancelTest();
            return;
        } else if (this.currentQuestion > this.questionModels.size()) {
            try {
                client.request("GET/testService/cancelTest");
                viewWindowAuth();
                return;
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        this.currentQuestionModel = questionModels.get(currentQuestion);
        questionUpdate();
    }

    private void viewCancelTest() {
        this.answerField.setText("");
        this.questionField.setText("Вы прошли все вопросы, нажмите далее чтобы завершить тест.");
    }

    private void viewWindowAuth() throws IOException {
        Stage stage = (Stage) this.nextButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("auth-view.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Auth");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    private void questionUpdate() {
        this.answerField.setText("");
        this.questionNumeration.setText(String.format("Вопрос № %d из %d", this.currentQuestion + 1, this.questionModels.size()));
        this.questionField.setText(this.currentQuestionModel.question);
    }
}
