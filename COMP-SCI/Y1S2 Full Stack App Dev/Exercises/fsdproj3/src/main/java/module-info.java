module com.example.fsdproj3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.sql.rowset;

    opens com.example.fsdproj3 to javafx.fxml;
    exports com.example.fsdproj3;
}