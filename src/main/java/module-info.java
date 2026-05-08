module br.edu.cruzeirodosul {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.edu.cruzeirodosul to javafx.fxml;
    exports br.edu.cruzeirodosul;
}
