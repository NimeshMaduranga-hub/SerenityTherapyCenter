module lk.ijse.serenity.serenitytherapycenter {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    requires static lombok;
    requires javafx.base;

    requires net.sf.jasperreports.core;
    requires java.desktop;
    requires jbcrypt;

    // JavaFX
    opens lk.ijse.serenity.serenitytherapycenter.controller to javafx.fxml;

    // Hibernate needs reflection
    opens lk.ijse.serenity.serenitytherapycenter.entity to org.hibernate.orm.core, javafx.base;

    // DTO for TableView
    opens lk.ijse.serenity.serenitytherapycenter.dto to javafx.base;

    // IMPORTANT EXPORTS
    exports lk.ijse.serenity.serenitytherapycenter;
    exports lk.ijse.serenity.serenitytherapycenter.entity;
    exports lk.ijse.serenity.serenitytherapycenter.dto;
}