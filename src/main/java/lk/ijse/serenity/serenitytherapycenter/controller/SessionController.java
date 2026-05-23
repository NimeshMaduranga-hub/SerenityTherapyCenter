package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SessionController {

    @FXML
    private TextField s_id_field;

    @FXML
    private ComboBox<?> s_program_combo_box;

    @FXML
    private TextField s_duration_field;

    @FXML
    private TextField s_t_contact_field;

    @FXML
    private TextField s_t_email_field;

    @FXML
    private TextField s_fee_field;

    @FXML
    private ComboBox<?> s_day_combo_box;

    @FXML
    private ComboBox<?> s_status_combo_box;

    @FXML
    private ComboBox<?> s_time_combo_box;

    @FXML
    private ComboBox<?> s_t_combo_box;

    @FXML
    private Button s_btn_save;

    @FXML
    private Button s_btn_update;

    @FXML
    private TextField s_search_field;

    @FXML
    private RadioButton s_id_radio;

    @FXML
    private RadioButton s_t_radio;

    @FXML
    private RadioButton s_p_radio;

    @FXML
    private TableView<?> session_table;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_program;

    @FXML
    private TableColumn<?, ?> col_therapist;

    @FXML
    private TableColumn<?, ?> col_day;

    @FXML
    private TableColumn<?, ?> col_time;

    @FXML
    private TableColumn<?, ?> col_status;

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void fillProgramData(ActionEvent event) {

    }

    @FXML
    void fillTherapistData(ActionEvent event) {

    }

    @FXML
    void handelSaveSession(ActionEvent event) {

    }

    @FXML
    void handelSearchSession(ActionEvent event) {

    }

    @FXML
    void handelUpdateSession(ActionEvent event) {

    }

    @FXML
    void initializeTimeCombobox(ActionEvent event) {

    }

    @FXML
    void uncheckOthers(ActionEvent event) {

    }

}
