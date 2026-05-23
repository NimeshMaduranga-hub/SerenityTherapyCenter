package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PatientsController {

    @FXML
    private TextField p_id_field;

    @FXML
    private TextField p_name_field;

    @FXML
    private TextField p_contact_field;

    @FXML
    private ComboBox<?> p_gender_box;

    @FXML
    private TextArea p_address_field;

    @FXML
    private TableView<?> patient_table;

    @FXML
    private TableColumn<?, ?> col_p_id;

    @FXML
    private TableColumn<?, ?> col_p_name;

    @FXML
    private TableColumn<?, ?> col_p_gender;

    @FXML
    private TableColumn<?, ?> col_p_contact;

    @FXML
    private TableColumn<?, ?> col_p_address;

    @FXML
    private Button p_btn_save;

    @FXML
    private Button p_btn_update;

    @FXML
    private Button p_btn_delete;

    @FXML
    private TextField p_search_field;

    @FXML
    private Label p_search_text;

    @FXML
    void SearchPatientOnAction(ActionEvent event) {

    }

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void handelDeletePatient(ActionEvent event) {

    }

    @FXML
    void handelSavePatient(ActionEvent event) {

    }

    @FXML
    void handelUpdatePatient(ActionEvent event) {

    }

}
