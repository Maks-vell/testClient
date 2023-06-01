package com.psu.testclient.controller;

import com.psu.testclient.Launcher;
import com.psu.testclient.client.Client;
import com.psu.testclient.model.QuestionModel;
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

import java.io.IOException;
import java.util.List;

public class TestController {
    @Setter
    private List<QuestionModel> questionModels;

    private QuestionModel currentQuestionModel;

    private int currentQuestion;

    @FXML
    private Text questionField;

    @FXML
    private Text questionNumeration;

    @FXML
    private Button nextButton;

    @FXML
    private Button viewAnswerButton;

    @FXML
    private Button prevButton;

    @FXML
    void initialize() {

        nextButton.setOnAction(this::nextButtonClick);
        prevButton.setOnAction(this::prevButtonClick);

        this.questionModels.add(new QuestionModel("Вы закончили тест, нажмите далее чтобы выйти", "", this.questionModels.size()));
        this.currentQuestionModel = questionModels.get(0);
        currentQuestion = 0;
        questionUpdate();
    }

    private void prevButtonClick(ActionEvent actionEvent) {
        if (currentQuestion > 0) {
            currentQuestion--;
        }

        this.currentQuestionModel = questionModels.get(currentQuestion);
        questionUpdate();
    }

    private void nextButtonClick(ActionEvent actionEvent) {
        currentQuestion++;
        if (currentQuestion >= this.questionModels.size()) {
            try {
                viewWindowAuth();
                return;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        this.currentQuestionModel = questionModels.get(currentQuestion);
        questionUpdate();
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
        this.questionNumeration.setText(String.format("Вопрос № %d из %d", this.currentQuestion + 1, this.questionModels.size()));
        this.questionField.setText(this.currentQuestionModel.question);
    }
}
