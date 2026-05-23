package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.serenity.serenitytherapycenter.App;

import java.io.IOException;

public class LoginControllerForm {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    void btnLoginOnAction(ActionEvent event) {

        try {
            App.stage.close();
            App.setRoot("dashbord");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
