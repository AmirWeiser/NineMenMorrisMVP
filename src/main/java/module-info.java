module com.example.ninemenmorrismvp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ninemenmorrismvp to javafx.fxml;
    exports com.example.ninemenmorrismvp;
}