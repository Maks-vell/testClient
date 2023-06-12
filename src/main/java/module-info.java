module com.psu.testclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.gson;
    requires log4j;

    opens com.psu.testclient to javafx.fxml;
    exports com.psu.testclient;
    exports com.psu.testclient.controller;
    opens com.psu.testclient.controller to javafx.fxml;
    exports com.psu.testclient.client;
    opens com.psu.testclient.client to javafx.fxml;
    exports com.psu.testclient.model;
    opens com.psu.testclient.model to com.google.gson, lombok;
}