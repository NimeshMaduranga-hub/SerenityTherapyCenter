package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.serenity.serenitytherapycenter.bo.BOFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.PaymentBO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDetailDTO;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    // buttons ----------------------------------
    @FXML private Button p_btn_reset;
    @FXML private Button p_btn_save;

    // paya table -------------------------
    @FXML private TableView<PaymentDTO> payment_table;
    @FXML private TableColumn<PaymentDTO, String> p_id_col;
    @FXML private TableColumn<PaymentDTO, String> p_reg_id_col;
    @FXML private TableColumn<PaymentDTO, String> p_s_id_col;
    @FXML private TableColumn<PaymentDTO, String> p_patient_col;
    @FXML private TableColumn<PaymentDTO, String> p_total_col;
    @FXML private TableColumn<PaymentDTO, String> p_status_col;

    // paya detail table -------------------------
    @FXML private TableView<PaymentDetailDTO> p_detail_table;
    @FXML private TableColumn<PaymentDetailDTO, String> p_detail_amount_col;
    @FXML private TableColumn<PaymentDetailDTO, String> p_detail_date_col;
    @FXML private TableColumn<PaymentDetailDTO, String> pay_detail_type_col;

    // pay input fields ------------------------
    @FXML private TextField p_due_field;
    @FXML private TextField p_total_field;
    @FXML private TextField p_pay_amount_field;
    @FXML private TextField p_pay_status_field;

    @FXML private RadioButton r_id_radio;
    @FXML private RadioButton r_p_radio;

    @FXML private TextField p_search_field;
    @FXML private TextField p_registration_id_field;

    // combo boxes ---------------------------------
    @FXML private ComboBox<String> p_type_combobox;
    @FXML private ComboBox<String> p_id_combo_box;

    // patient input fields ------------------------
    @FXML private TextField p_patient_address_field;
    @FXML private TextField p_patient_contact_field;
    @FXML private TextField p_patient_name_field;

    // BO objects --------------------------------------
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    // global variables --------------------------------
    private final Alerts alert = new Alerts("Payment Management.");
    PaymentDTO paymentDTO = null;

    // initialize page --------------------------------
    public void initialize(){
        // initialize pay detail table
        p_detail_amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        p_detail_date_col.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        pay_detail_type_col.setCellValueFactory(new PropertyValueFactory<>("payType"));

        // initialize payment table
        p_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        p_reg_id_col.setCellValueFactory(new PropertyValueFactory<>("registrationID"));
        p_s_id_col.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
        p_patient_col.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        p_total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        p_status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

        // initialize payment type combo box ----------------------------
        ObservableList<String> obPayTypes = FXCollections.observableArrayList();
        obPayTypes.add("Cash");
        obPayTypes.add("Card");
        obPayTypes.add("QR");
        p_type_combobox.setItems(obPayTypes);

        // initialize payment text field to input only price with two decimals
        p_pay_amount_field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d*(\\.\\d{0,2})?")){
                return change;
            }
            else{
                return null;
            }
        }));

        // initialize payment text field to run calDueAmount method when an integer key is pressed
        p_pay_amount_field.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().isDigitKey() || (event.getCode() == KeyCode.BACK_SPACE)) {  // check if the key is a digit number key
                calDueAmount();
            }
        });

        // initialize payment ID combo box ------------------
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> payments = paymentBO.getAllOngoingPaymentID();
        obList.addAll(payments);
        p_id_combo_box.setItems(obList);

        // load payment table --------------------------
        loadPaymentTable();
    }
    void calDueAmount(){
        String text = p_pay_amount_field.getText();
        double payAmount;

        if(text.isEmpty()){
            payAmount = 0;
        }else{
            payAmount = Double.parseDouble(text);
        }

        double currentDue = Double.parseDouble(String.valueOf(paymentDTO.getDueAmount()));
        double dueAmount = currentDue + payAmount;
        p_due_field.setText(String.format("%.2f", dueAmount));

    }

    void loadPaymentTable(){
        List<PaymentDTO> paymentDTOList = paymentBO.getAllPayments();
        ObservableList<PaymentDTO> obList = FXCollections.observableArrayList();
        obList.addAll(paymentDTOList);
        payment_table.setItems(obList);
    }


    @FXML
    void loadTableData(){
        TableView.TableViewSelectionModel<PaymentDTO> selectedPayment = payment_table.getSelectionModel();
        PaymentDTO payment = selectedPayment.getSelectedItem();

        if(payment != null){
            p_id_combo_box.setValue(payment.getId());
        }
    }
    @FXML
    void fillPaymentData() {

        String payId = p_id_combo_box.getValue();
        if(payId == null){
            return;
        }

        int id = Integer.parseInt(payId.substring(4));
        paymentDTO = paymentBO.getPaymentDataByID(id);

        p_registration_id_field.setText(paymentDTO.getRegistration().getId());
        p_patient_name_field.setText(paymentDTO.getPatient().getName());
        p_patient_contact_field.setText(paymentDTO.getPatient().getContact());
        p_patient_address_field.setText(paymentDTO.getPatient().getAddress());

        List<PaymentDetailDTO> paymentDetailDTOList = paymentDTO.getPaymentDetails();
        ObservableList<PaymentDetailDTO> obList = FXCollections.observableArrayList();
        obList.addAll(paymentDetailDTOList);
        p_detail_table.setItems(obList);

        double total = Double.parseDouble(String.valueOf(paymentDTO.getTotal()));
        p_total_field.setText(total + "");
        double currentAmount = 0;

        for(PaymentDetailDTO p : paymentDetailDTOList){
            currentAmount += Double.parseDouble(String.valueOf(p.getAmount()));
        }

        double dueAmount = currentAmount - total;
        paymentDTO.setDueAmount(BigDecimal.valueOf(dueAmount));

        p_due_field.setText(dueAmount + "");
        p_pay_amount_field.setDisable(false);

        p_pay_status_field.setText(paymentDTO.getStatus());

    }

    @FXML
    void handelResetPayment() {
        p_id_combo_box.setValue(null);
        p_type_combobox.setValue(null);

        p_due_field.setText("");
        p_total_field.setText("");
        p_pay_amount_field.setText("");
        p_registration_id_field.setText("");

        p_patient_address_field.setText("");
        p_patient_contact_field.setText("");
        p_patient_name_field.setText("");
        p_search_field.setText("");

        p_btn_save.setDisable(false);
        p_pay_amount_field.setDisable(true);

        paymentDTO = null;
        loadPaymentTable();
    }

    @FXML
    void handelSavePayment() {

        String payType = p_type_combobox.getValue();
        String amountText = p_pay_amount_field.getText();
        String payDate = String.valueOf(LocalDate.now());

        if(paymentDTO == null){
            alert.getInfoAlert("Select a Payment!").show();
        }
        else if(amountText.isEmpty()){
            alert.getInfoAlert("Enter Paying Amount!").show();
        }
        else if((payType == null) || payType.isEmpty()){
            alert.getInfoAlert("Select a Payment Type!").show();
        }
        else{
            PaymentDetailDTO pd = new PaymentDetailDTO();
            pd.setPayType(payType);
            pd.setAmount(BigDecimal.valueOf(Integer.parseInt(amountText)));
            pd.setPayDate(payDate);
            pd.setPayment(paymentDTO);

            double dueAmount = Double.parseDouble(p_due_field.getText());
            String status = null;

            // check payment status ----------------------
            if(dueAmount < 0){
                status = "Ongoing";
            }
            else{
                status = "Done";
            }

            PaymentDTO p = new PaymentDTO();
            p.setId(p_id_combo_box.getValue());
            p.setDueAmount(BigDecimal.valueOf(dueAmount));
            p.setStatus(status);
            p.getPaymentDetails().add(pd);

            boolean isSaved = paymentBO.updatePayment(p);
            if(isSaved){
                alert.getSuccessAlert("Payment Saved Successfully!").show();
                fillPaymentData();
                p_pay_amount_field.setText("");
                p_type_combobox.setValue("");
                printInvoice();
                loadPaymentTable();
            }
            else{
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelSearchPayment(){
        String text = p_search_field.getText();
        if(text.isEmpty()){
            alert.getInfoAlert("Enter ID or Patient To Search!").show();
            return;
        }

        int num = -1;
        if(r_id_radio.isSelected()){
            num = 0;
        }
        else{
            num = 1;
        }

        if(num == 0 && !text.startsWith("PAY_")){
            alert.getInfoAlert("Invalid ID!").show();
            return;
        }

        List<PaymentDTO> paymentDTOList = paymentBO.searchPayment(text, num);
        if(paymentDTOList.isEmpty()){
            alert.getInfoAlert("Payment Not Found!").show();
            return;
        }
        ObservableList<PaymentDTO> obList = FXCollections.observableArrayList();
        obList.addAll(paymentDTOList);
        payment_table.setItems(obList);

    }

    @FXML
    void uncheckOthers(ActionEvent event){
        r_id_radio.setSelected(false);
        r_p_radio.setSelected(false);
        RadioButton radioButton = (RadioButton) event.getSource();
        radioButton.setSelected(true);
    }


    @FXML
    void printInvoice(){
        if(paymentDTO == null){
            alert.getInfoAlert("Select A Payment!").show();
            return;
        }
        paymentBO.print(paymentDTO.getId());
    }

    @FXML
    void p_search_field(ActionEvent event) {

    }



}
