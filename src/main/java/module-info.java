module com.psu.testclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.psu.testclient to javafx.fxml;
    exports com.psu.testclient;
}