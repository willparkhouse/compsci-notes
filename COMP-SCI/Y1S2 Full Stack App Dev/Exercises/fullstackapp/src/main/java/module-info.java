module com.example.fullstackapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    opens com.example.fullstackapp to javafx.fxml;
    exports com.example.fullstackapp;
}