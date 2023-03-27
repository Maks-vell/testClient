package com.psu.testclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class TestController {

    @FXML
    private Text QuestionField;

    @FXML
    private Text QuestionNumeration;

    @FXML
    private Button nextButton;

    @FXML
    private Button viewAnswerButton;

    @FXML
    void initialize() {

        nextButton.setOnAction(event -> {

        });

    }
}
