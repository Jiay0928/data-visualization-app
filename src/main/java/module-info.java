module com.example.a1basic {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.a1basic to javafx.fxml;
    exports com.example.a1basic;
}