module lk.ijse.serenity.serenitytherapycenter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires static lombok;

    opens lk.ijse.serenity.serenitytherapycenter to javafx.fxml;
    opens lk.ijse.serenity.serenitytherapycenter.controller to javafx.fxml;
    exports lk.ijse.serenity.serenitytherapycenter;
}