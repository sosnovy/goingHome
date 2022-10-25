module com.example.cominghome {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cominghome to javafx.fxml;
    exports com.example.cominghome;
}