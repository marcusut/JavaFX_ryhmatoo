module org.example.javafx_ryhmatoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafx_ryhmatoo to javafx.fxml;
    exports org.example.javafx_ryhmatoo;
}