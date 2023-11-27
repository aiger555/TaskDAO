module com.example.taskdao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.taskdao to javafx.fxml;
    exports com.example.taskdao;
}