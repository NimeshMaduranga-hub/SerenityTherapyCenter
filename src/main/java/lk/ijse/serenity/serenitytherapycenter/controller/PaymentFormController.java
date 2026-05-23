package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaymentFormController {

    @FXML
    private TextField p_total_field;

    @FXML
    private TextField p_due_field;

    @FXML
    private TextField p_pay_amount_field;

    @FXML
    private TextField p_pay_status_field;

    @FXML
    private ComboBox<?> p_type_combobox;

    @FXML
    private Button p_btn_save;

    @FXML
    private Button p_btn_reset;

    @FXML
    private TableView<?> p_detail_table;

    @FXML
    private TableColumn<?, ?> p_detail_amount_col;

    @FXML
    private TableColumn<?, ?> p_detail_date_col;

    @FXML
    private TableColumn<?, ?> pay_detail_type_col;

    @FXML
    private ComboBox<?> p_id_combo_box;

    @FXML
    private TextField p_registration_id_field;

    @FXML
    private TextField p_patient_name_field;

    @FXML
    private TextField p_patient_contact_field;

    @FXML
    private TextField p_patient_address_field;

    @FXML
    private RadioButton r_id_radio;

    @FXML
    private RadioButton r_p_radio;

    @FXML
    private TableView<?> payment_table;

    @FXML
    private TableColumn<?, ?> p_id_col;

    @FXML
    private TableColumn<?, ?> p_reg_id_col;

    @FXML
    private TableColumn<?, ?> p_s_id_col;

    @FXML
    private TableColumn<?, ?> p_patient_col;

    @FXML
    private TableColumn<?, ?> p_total_col;

    @FXML
    private TableColumn<?, ?> p_status_col;

    @FXML
    void fillPaymentData(ActionEvent event) {

    }

    @FXML
    void handelResetPayment(ActionEvent event) {

    }

    @FXML
    void handelSavePayment(ActionEvent event) {

    }

    @FXML
    void handelSearchPayment(ActionEvent event) {

    }

    @FXML
    void p_search_field(ActionEvent event) {

    }

    @FXML
    void printInvoice(ActionEvent event) {

    }

    @FXML
    void uncheckOthers(ActionEvent event) {

    }

}
