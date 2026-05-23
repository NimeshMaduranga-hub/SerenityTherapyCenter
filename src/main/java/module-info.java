module lk.ijse.serenity.serenitytherapycenter {

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires java.naming;
    requires java.sql;
    requires java.desktop;

    requires static lombok;

    // MySQL Connector
    requires mysql.connector.j;

    opens lk.ijse.serenity.serenitytherapycenter to javafx.fxml;
    opens lk.ijse.serenity.serenitytherapycenter.controller to javafx.fxml;

    // Hibernate entity package open karanna one
    opens lk.ijse.serenity.serenitytherapycenter.entity to org.hibernate.orm.core;

    exports lk.ijse.serenity.serenitytherapycenter;
}