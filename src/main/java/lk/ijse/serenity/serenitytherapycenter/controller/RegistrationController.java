package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.serenity.serenitytherapycenter.bo.*;
import lk.ijse.serenity.serenitytherapycenter.dto.*;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RegistrationController {


    // register Table -----------------
    @FXML private TableView<RegistrationDTO> registration_table;
    @FXML private TableColumn<RegistrationDTO, String> col_patient;
    @FXML private TableColumn<RegistrationDTO, String> col_program;
    @FXML private TableColumn<RegistrationDTO, String> col_r_id;
    @FXML private TableColumn<RegistrationDTO, String> col_register_date;
    @FXML private TableColumn<RegistrationDTO, String> col_s_id;
    @FXML private TableColumn<RegistrationDTO, String> col_therapist;

    // buttons ----------------
    @FXML private Button r_btn_save;

    @FXML private TextField r_id_field;

    // combo boxes --------------------
    @FXML private ComboBox<String> r_session_combobox;
    @FXML private ComboBox<String> r_patient_combobox;

    // program data fields ------------------
    @FXML private TextField r_p_name_field;
    @FXML private TextField r_p_duration;
    @FXML private TextField r_p_fee_field;
    @FXML private TextField r_p_day_field;
    @FXML private TextField r_p_time_field;
    @FXML private TextField r_patient_name_field;
    @FXML private TextField r_session_id_field;

    // therapist data fields -----------
    @FXML private TextField r_t_name_field;
    @FXML private TextField r_t_contact_field;
    @FXML private TextField r_t_email_field;

    // payment data fields ---------------
    @FXML private TextField r_pay_id_field;
    @FXML private TextField r_pay_grand_total_field;
    @FXML private TextField r_pay_due_amount_field;
    @FXML private TextField r_pay_total_field;
    @FXML private ComboBox<String> r_pay_type_combobox;

    // session search fields --------------
    @FXML private TextField r_search_field;
    @FXML private RadioButton r_id_radio;
    @FXML private RadioButton r_p_radio;

    // BO objects -------------------------------
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.REGISTRATION);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);
    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    // global variables ------------------------------
    SessionDTO selectedSession = null;
    private final Alerts alert = new Alerts("Patient Registration.");

    public void initialize(){
        // initialize session table ---------------
        col_r_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_s_id.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
        col_patient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        col_program.setCellValueFactory(new PropertyValueFactory<>("programName"));
        col_therapist.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        col_register_date.setCellValueFactory(new PropertyValueFactory<>("registerDate"));

        // initialize payment type combo box ----------------------------
        ObservableList<String> obPayTypes = FXCollections.observableArrayList();
        obPayTypes.add("Cash");
        obPayTypes.add("Card");
        obPayTypes.add("QR");
        r_pay_type_combobox.setItems(obPayTypes);

        // initialize patient combo box ---------------------------------------
        ObservableList<String> obPatients = FXCollections.observableArrayList();
        List<PatientDTO> patients = patientBO.getAllPatients();
        for(PatientDTO p : patients){
            obPatients.add(p.getId() + " - " + p.getName());
        }
        r_patient_combobox.setItems(obPatients);

        // initialize sessions combo box ---------------------------------------
        ObservableList<String> obSessions = FXCollections.observableArrayList();
        List<SessionTM> sessions = sessionBO.getAvailableSessions();
        for(SessionTM s : sessions){
            obSessions.add(s.getId() + "  -   " + s.getProgram().substring(6) + "    Dr." + s.getTherapist().substring(5));
        }
        r_session_combobox.setItems(obSessions);

        // initialize payment text field to input only price with two decimals
        r_pay_total_field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d*(\\.\\d{0,2})?")){
                return change;
            }
            else{
                return null;
            }
        }));

        // initialize payment text field to run calDueAmount method when an integer key is pressed
        r_pay_total_field.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().isDigitKey() || (event.getCode() == KeyCode.BACK_SPACE)) {  // check if the key is a digit number key
                calDueAmount();
            }
        });

        // load registration table --------------------------
        loadRegistrationTable();

        // get next id -----------------------------------
        getNextRegistrationID();
        getNextPaymentID();
    }

    // load registration table --------------------------
    void loadRegistrationTable(){
        ObservableList<RegistrationDTO> obList = FXCollections.observableArrayList();
        List<RegistrationDTO> registrationDTOS = registrationBO.getAllRegistrations();
        obList.addAll(registrationDTOS);
        registration_table.setItems(obList);
    }

    // get registration table data -------------------------------
    @FXML
    void getRegistrationTableData() {
        TableView.TableViewSelectionModel<RegistrationDTO> selectedR = registration_table.getSelectionModel();
        RegistrationDTO registrationDTO = selectedR.getSelectedItem();
        if(registrationDTO == null){
            return;
        }

        int registerId = Integer.parseInt(registrationDTO.getId().substring(2));
        RegistrationDTO r = registrationBO.getRegistrationByID(registerId);

        r_id_field.setText(r.getId());
        r_p_name_field.setText(r.getProgram().getName());
        r_p_fee_field.setText(r.getProgram().getFee() + "");
        r_p_duration.setText(r.getProgram().getDuration());
        r_p_day_field.setText(r.getSession().getDay());
        r_p_time_field.setText(r.getSession().getTime());
        r_t_name_field.setText(r.getTherapist().getName());
        r_t_contact_field.setText(r.getTherapist().getContact());
        r_t_email_field.setText(r.getTherapist().getEmail());

        r_patient_name_field.setText(r.getPatientName());
        r_session_id_field.setText(r.getSession().getId());

        r_patient_name_field.setVisible(true);
        r_session_id_field.setVisible(true);

        r_btn_save.setDisable(true);
        r_pay_due_amount_field.setDisable(true);
        r_pay_grand_total_field.setDisable(true);
        r_pay_type_combobox.setDisable(true);

    }

    // get next registration id ------------------------
    void getNextRegistrationID(){
        String nextRegID = registrationBO.getNextId();
        r_id_field.setText(nextRegID);
    }

    // get next payment id ------------------------
    void getNextPaymentID(){
        String nextPayID = paymentBO.getNextID();
        r_pay_id_field.setText(nextPayID);
    }

    @FXML
    void clearFields() {
        selectedSession = null;

        r_t_name_field.setText("");
        r_t_contact_field.setText("");
        r_t_email_field.setText("");
        r_p_name_field.setText("");
        r_p_duration.setText("");
        r_p_fee_field.setText("");
        r_p_day_field.setText("");
        r_p_time_field.setText("");
        r_pay_grand_total_field.setText("");
        r_pay_due_amount_field.setText("");
        r_pay_total_field.setText("");
        r_search_field.setText("");

        r_session_combobox.setValue(null);
        r_session_combobox.setOnShowing(null);
        r_patient_combobox.setValue(null);
        r_patient_combobox.setOnShowing(null);
        r_pay_type_combobox.setValue(null);

        r_patient_name_field.setVisible(false);
        r_session_id_field.setVisible(false);

        r_btn_save.setDisable(false);
        r_pay_total_field.setDisable(true);
        r_pay_due_amount_field.setDisable(false);
        r_pay_grand_total_field.setDisable(false);
        r_pay_type_combobox.setDisable(false);

        loadRegistrationTable();
        getNextRegistrationID();
        getNextPaymentID();
    }

    @FXML
    void fillSessionData() {
        String text = r_session_combobox.getValue();
        if(text == null){ return; }

        int num = text.indexOf("-");
        if(num<0){

        }else{
            int end = num -2; // skip two spaces
            selectedSession = new SessionDTO();
            selectedSession = sessionBO.searchSessionByID(text.substring(0, end));

            r_p_name_field.setText(selectedSession.getProgram().getName());
            r_p_duration.setText(selectedSession.getProgram().getDuration());
            r_p_fee_field.setText(selectedSession.getProgram().getFee() + "");

            r_t_name_field.setText(selectedSession.getTherapist().getName());
            r_t_contact_field.setText(selectedSession.getTherapist().getContact());
            r_t_email_field.setText(selectedSession.getTherapist().getEmail());

            r_p_day_field.setText(selectedSession.getDay());
            r_p_time_field.setText(selectedSession.getTime());

            r_pay_grand_total_field.setText(selectedSession.getProgram().getFee() + "");
            r_pay_due_amount_field.setText("-" + selectedSession.getProgram().getFee());

            r_pay_total_field.setDisable(false);
        }
    }

    @FXML
    void handelSaveRegistration() {
        String patient   = r_patient_combobox.getValue();
        String payType   = r_pay_type_combobox.getValue();
        String payment   = r_pay_total_field.getText();
        String dueAmountTxt = r_pay_due_amount_field.getText();

        if(selectedSession == null){
            alert.getInfoAlert("Select a Session!").show();
            return;
        }
        else if ((patient == null) || patient.isEmpty()) {
            alert.getInfoAlert("Select a Patient!").show();
            return;
        }
        else if((payType == null) || (payType.isEmpty())){
            alert.getInfoAlert("Select a Payment Type!").show();
            return;
        }
        else if (payment.isEmpty()) {
            alert.getInfoAlert("Enter Paying Amount!").show();
            return;
        }

        int end  = patient.indexOf("-") - 1;
        String patientID = patient.substring(0, end);
        String sessionID = selectedSession.getId();
        String registrationDate = String.valueOf(LocalDate.now());

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setPatientID(patientID);
        registrationDTO.setSessionID(sessionID);
        registrationDTO.setRegisterDate(registrationDate);


        BigDecimal total = BigDecimal.valueOf(Double.parseDouble(r_pay_grand_total_field.getText()));
        BigDecimal dueAmount = BigDecimal.valueOf(Double.parseDouble(dueAmountTxt));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setTotal(total);
        paymentDTO.setDueAmount(dueAmount);

        // check the payment is DONE or ONGOING ----------------
        if(Double.parseDouble(dueAmountTxt) < 0){
            paymentDTO.setStatus("Ongoing");
        }
        else {
            paymentDTO.setStatus("Done");
        }

        BigDecimal payAmount = BigDecimal.valueOf(Double.parseDouble(payment));

        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();
        paymentDetailDTO.setAmount(payAmount);
        paymentDetailDTO.setPayDate(String.valueOf(LocalDate.now()));
        paymentDetailDTO.setPayType(payType);

        boolean isSaved = registrationBO.saveRegistration(registrationDTO, paymentDTO, paymentDetailDTO);
        if(isSaved){
            alert.getSuccessAlert("Registration Saved Successfully!").show();
            paymentBO.print(r_pay_id_field.getText());
            clearFields();
        }
        else{
            alert.getErrorAlert("Something Went Wrong!").show();
        }

    }

    @FXML
    void handelSearchRegistrations() {
        String text = r_search_field.getText();
        int num = -1;
        if(r_id_radio.isSelected()){
            num = 0;
        }else if(r_p_radio.isSelected()){
            num = 1;
        }

        if(text.isEmpty()){
            alert.getInfoAlert("Enter ID or Patient To Search!").show();
            return;
        }
        else if(num == 0 && !text.startsWith("R_")){
            alert.getInfoAlert("Invalid ID!").show();
            return;
        }

        List<RegistrationDTO> list = registrationBO.searchRegistration(text, num);
        ObservableList<RegistrationDTO> obList = FXCollections.observableArrayList();

        if(list.isEmpty()){
            alert.getInfoAlert("Registration Not Found!").show();
        }
        else{
            obList.addAll(list);
            registration_table.setItems(obList);
        }
    }

    // calculate due amount --------------------------
    void calDueAmount(){
        String text = r_pay_total_field.getText();
        double payAmount;
        double grandTotal = Double.parseDouble(r_pay_grand_total_field.getText());

        if(text.isEmpty()){
            payAmount = 0;
        }else{
            payAmount = Double.parseDouble(text);
        }

        double dueAmount = payAmount - grandTotal;
        r_pay_due_amount_field.setText(String.format("%.2f", dueAmount));

    }

    // radio button behaviors -----------------------
    @FXML
    void uncheckOthers(ActionEvent event) {
        r_id_radio.setSelected(false);
        r_p_radio.setSelected(false);
        RadioButton radioButton = (RadioButton) event.getSource();
        radioButton.setSelected(true);
    }

    public void handelUpdateRegistration(ActionEvent event) {

    }
}
