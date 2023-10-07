module com.example.bookassessment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.sql.rowset;
    requires postgresql;

    opens com.example.fsdbooks to javafx.fxml;
    exports com.example.fsdbooks;
}