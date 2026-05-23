package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private TextField r_id_field;

    @FXML
    private ComboBox<?> r_session_combobox;

    @FXML
    private ComboBox<?> r_patient_combobox;

    @FXML
    private TextField r_p_name_field;

    @FXML
    private TextField r_p_duration;

    @FXML
    private TextField r_p_fee_field;

    @FXML
    private TextField r_p_day_field;

    @FXML
    private TextField r_p_time_field;

    @FXML
    private TextField r_t_name_field;

    @FXML
    private TextField r_t_contact_field;

    @FXML
    private TextField r_t_email_field;

    @FXML
    private TextField r_pay_id_field;

    @FXML
    private TextField r_pay_grand_total_field;

    @FXML
    private TextField r_pay_due_amount_field;

    @FXML
    private ComboBox<?> r_pay_type_combobox;

    @FXML
    private Button r_btn_save;

    @FXML
    private Button r_btn_update;

    @FXML
    private TextField r_pay_total_field;

    @FXML
    private RadioButton r_id_radio;

    @FXML
    private RadioButton r_p_radio;

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void fillSessionData(ActionEvent event) {

    }

    @FXML
    void handelSaveRegistration(ActionEvent event) {

    }

    @FXML
    void handelUpdateRegistration(ActionEvent event) {

    }

    @FXML
    void uncheckOthers(ActionEvent event) {

    }

}
