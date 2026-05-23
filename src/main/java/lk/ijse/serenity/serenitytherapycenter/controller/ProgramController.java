package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class ProgramController {

    @FXML
    private TextField pr_id_field;

    @FXML
    private TextField pr_name_field;

    @FXML
    private TextField pr_duration_field;

    @FXML
    private TextField pr_fee_field;

    @FXML
    private Button pr_btn_save;

    @FXML
    private Button pr_btn_update;

    @FXML
    private Button pr_btn_delete;

    @FXML
    private ComboBox<?> pr_t_name_combo_box;

    @FXML
    private TextField pr_t_id_field;

    @FXML
    private TextField pr_t_contact_field;

    @FXML
    private TextField pr_t_email_field;

    @FXML
    private Button pr_btn_link;

    @FXML
    private Button pr_btn_unlink;

    @FXML
    private Label p_search_text;

    @FXML
    private TextField pr_search_field;

    @FXML
    private TableColumn<?, ?> col_pr_id_2;

    @FXML
    private TableColumn<?, ?> col_pr_name_2;

    @FXML
    private TableColumn<?, ?> col_t_id;

    @FXML
    private TableColumn<?, ?> col_t_name;

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void fillTherapistData(ActionEvent event) {

    }

    @FXML
    void handelDeleteProgram(ActionEvent event) {

    }

    @FXML
    void handelLinkProgram(ActionEvent event) {

    }

    @FXML
    void handelSaveProgram(ActionEvent event) {

    }

    @FXML
    void handelSearchProgram(ActionEvent event) {

    }

    @FXML
    void handelUnlinkProgram(ActionEvent event) {

    }

    @FXML
    void handelUpdateProgram(ActionEvent event) {

    }

    @FXML
    void viewProgramDetailTable(ActionEvent event) {

    }

}
