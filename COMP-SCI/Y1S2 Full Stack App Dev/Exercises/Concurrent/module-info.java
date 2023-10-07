module com.example.romeo_juliet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.romeo_juliet to javafx.fxml;
    exports com.example.romeo_juliet;
}