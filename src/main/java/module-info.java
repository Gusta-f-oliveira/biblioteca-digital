module br.edu.cruzeirodosul {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.edu.cruzeirodosul to javafx.fxml;
    exports br.edu.cruzeirodosul;

    opens br.edu.cruzeirodosul.view to javafx.fxml;
    exports br.edu.cruzeirodosul.view;
}
