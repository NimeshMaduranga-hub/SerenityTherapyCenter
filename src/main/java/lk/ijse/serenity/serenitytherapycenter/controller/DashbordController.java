package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import lk.ijse.serenity.serenitytherapycenter.App;

import java.io.IOException;

public class DashbordController {

    @FXML
    private Pane homePane;

    @FXML
    public void initialize() {
        System.out.println("Dashboard initialized ✔");
        System.out.println(homePane);
    }

    @FXML
    public void dashboardBtnOnAction(ActionEvent event) throws Exception {
        App.setRoot("dashbord");
    }

    @FXML
    public void patientsBtnOnAction(ActionEvent event) throws IOException {

        loadView("patients_form");

    }

    @FXML
    public void therapistsBtnOnAction(ActionEvent event) throws IOException {
        loadView("therapists_form");
    }

    @FXML
    public void programsBtnOnAction(ActionEvent event) throws IOException {
        loadView("programs_form");
    }

    @FXML
    public void sessionsBtnOnAction(ActionEvent event) throws IOException {
        loadView("sessions_form");
    }

    @FXML
    public void registrationBtnOnAction(ActionEvent event) throws IOException {
        loadView("registration_form");
    }

    @FXML
    public void paymentBtnOnAction(ActionEvent event) throws IOException {
        loadView("payment_form");
    }

    @FXML
    public void logOutBtnOnAction(ActionEvent event) throws Exception {
        App.setRoot("login_form");
    }

    private void loadView(String fxml) throws IOException {

        Parent view = FXMLLoader.load(
                App.class.getResource("/lk/ijse/serenity/serenitytherapycenter/view/" + fxml + ".fxml")
        );

        homePane.getChildren().setAll(view);
    }
}