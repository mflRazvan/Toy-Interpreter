module com.example.a2_gui {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.gui.interpreter to javafx.fxml;
    exports com.gui.interpreter;
    exports hardcoded;
    opens hardcoded to javafx.fxml;
    //exports gui;
    //opens gui to javafx.fxml;
}