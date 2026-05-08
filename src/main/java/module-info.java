module lk.ijse.serenity.serenitytherapycenter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens lk.ijse.serenity.serenitytherapycenter to javafx.fxml;
    exports lk.ijse.serenity.serenitytherapycenter;
}