package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TherapistController {

    @FXML
    private TextField t_id_field;

    @FXML
    private TextField t_name_field;

    @FXML
    private TextField t_contact_field;

    @FXML
    private TextField t_email_field;

    @FXML
    private Button t_btn_save;

    @FXML
    private Button t_btn_update;

    @FXML
    private Button t_btn_delete;

    @FXML
    private TextField t_search_field;

    @FXML
    private Label p_search_text;

    @FXML
    private TableView<?> therapist_table;

    @FXML
    private TableColumn<?, ?> col_t_id;

    @FXML
    private TableColumn<?, ?> col_t_name;

    @FXML
    private TableColumn<?, ?> col_t_contact;

    @FXML
    private TableColumn<?, ?> col_t_email_address;

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void handelDeleteTherapist(ActionEvent event) {

    }

    @FXML
    void handelSaveTherapist(ActionEvent event) {

    }

    @FXML
    void handelSearchTherapist(ActionEvent event) {

    }

    @FXML
    void handelUpdateTherapist(ActionEvent event) {

    }

}
