package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.BOFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.PatientBO;
import lk.ijse.serenity.serenitytherapycenter.dto.PatientDTO;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.util.List;

public class PatientsController {

    // input fields -------------------
    @FXML private TextField p_id_field;
    @FXML private TextField p_name_field;
    @FXML private TextField p_contact_field;
    @FXML private TextArea p_address_field;
    @FXML private ComboBox<String> p_gender_box;
    @FXML private TextField p_search_field;
    @FXML private Label p_search_text;

    // check box ---------------
    @FXML
    private CheckBox p_check_box;
    // table ---------------------------------
    @FXML private TableView<PatientDTO> patient_table;
    @FXML private TableColumn<PatientDTO, String> col_p_address;
    @FXML private TableColumn<PatientDTO, String> col_p_contact;
    @FXML private TableColumn<PatientDTO, String> col_p_gender;
    @FXML private TableColumn<PatientDTO, String> col_p_id;
    @FXML private TableColumn<PatientDTO, String> col_p_name;

    // buttons ---------------------------------
    @FXML private Button p_btn_save;
    @FXML private Button p_btn_update;
    @FXML private Button p_btn_delete;

    // regex ---------------------------------------------
    private final String PATIENT_NAME_REGEX    = "^[A-Za-z\\s]{3,}$";
    private final String PATIENT_CONTACT_REGEX = "^[0-9]{10}$";
    private final String PATIENT_ADDRESS_REGEX = "^.{4,}$";

    private final Alerts alert = new Alerts("Patient Management.");
    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);



    public void initialize() {
        // initialize combo box
        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female", "Other");
        p_gender_box.setItems(genders);

        // initialize patient table
        col_p_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_p_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_p_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_p_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_p_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        patient_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, patient) -> {
                    if (patient != null) {
                        p_id_field.setText(patient.getId());
                        p_name_field.setText(patient.getName());
                        p_contact_field.setText(patient.getContact());
                        p_address_field.setText(patient.getAddress());
                        p_gender_box.setValue(patient.getGender());

                        p_btn_delete.setDisable(false);
                        p_btn_update.setDisable(false);
                        p_btn_save.setDisable(true);
                    }
                });


        loadPatients();
        getNextPID();

        p_search_text.setVisible(false);
        p_btn_update.setDisable(true);
        p_btn_delete.setDisable(true);
    }

    // load patient data to table ---------------------------
    @FXML
    private void loadPatients(){
        try{
            List<PatientDTO> patients = patientBO.getAllPatients();
            ObservableList<PatientDTO> obList = FXCollections.observableArrayList();
            obList.addAll(patients);
            patient_table.setItems(obList);

        }catch (Exception e){
            e.printStackTrace();
            alert.getErrorAlert("Something Went Wrong!").show();
        }

    }

    // Save patient ---------------------------
    @FXML
    private void handelSavePatient(){

        String name     = p_name_field.getText();
        String contact  = p_contact_field.getText();
        String address  = p_address_field.getText();
        String gender   = p_gender_box.getValue();

        // data validation
        if(!name.matches(PATIENT_NAME_REGEX)){
            alert.getErrorAlert("Invalid Name!").show();
        } else if(!contact.matches(PATIENT_CONTACT_REGEX)){
            alert.getErrorAlert("Invalid Contact Number!").show();
        }else if(!address.matches(PATIENT_ADDRESS_REGEX)){
            alert.getErrorAlert("Invalid Address!").show();
        }else if(gender.isEmpty()){
            alert.getErrorAlert("Select a Gender!").show();
        }
        else{
            // check if is there duplicate values
            int duplicateVal = checkDataIsDuplicate(0, name, contact, "s");
            if(duplicateVal == 0){
                alert.getErrorAlert("Patient name is already exist!").show();
                return;
            }
            else if(duplicateVal == 1){
                alert.getErrorAlert("Patient contact is already exist!").show();
                return;
            }

            // save patient data
            PatientDTO patient = new PatientDTO();
            patient.setName(name.trim());
            patient.setGender(gender);
            patient.setContact(contact);
            patient.setAddress(address.trim());

            boolean isSaved = patientBO.savePatient(patient);
            if(isSaved){
                alert.getSuccessAlert("Patient Saved Successfully!").show();
                clearFields();
            }else {
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }


    // Update patient ---------------------------
    @FXML
    public void handelUpdatePatient(){

        String id       = p_id_field.getText().trim();
        String name     = p_name_field.getText().trim();
        String contact  = p_contact_field.getText().trim();
        String address  = p_address_field.getText().trim();
        String gender   = p_gender_box.getValue().trim();

        // data validation
        if(!name.matches(PATIENT_NAME_REGEX)){
            alert.getErrorAlert("Invalid Name!").show();
        } else if(!contact.matches(PATIENT_CONTACT_REGEX)){
            alert.getErrorAlert("Invalid Contact Number!").show();
        }else if(!address.matches(PATIENT_ADDRESS_REGEX)){
            alert.getErrorAlert("Invalid Address!").show();
        }else if(gender.isEmpty()){
            alert.getErrorAlert("Select a Gender!").show();
        }
        else{
            // check if is there duplicate values
            int p_id = Integer.parseInt(id.substring(2));
            int duplicateVal = checkDataIsDuplicate(p_id, name, contact, "u");
            if(duplicateVal == 0){
                alert.getErrorAlert("Patient name is already exist!").show();
                return;
            }
            else if(duplicateVal == 1){
                alert.getErrorAlert("Patient contact is already exist!").show();
                return;
            }

            // update patient data
            PatientDTO patient = new PatientDTO();
            patient.setId(p_id+"");
            patient.setName(name.trim());
            patient.setGender(gender);
            patient.setContact(contact);
            patient.setAddress(address.trim());

            boolean isUpdated = patientBO.updatePatient(patient);
            if(isUpdated){
                alert.getSuccessAlert("Patient Updated Successfully!").show();
                clearFields();
            }else {
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }


    // Delete patient ---------------------------
    @FXML
    public void handelDeletePatient(){
        String id = p_id_field.getText().trim();

        if(id.isEmpty()){
            alert.getErrorAlert("Please Select a Patient").show();
            return;
        }

        int p_id = Integer.parseInt(id.substring(2));
        boolean isDeleted = patientBO.deletePatient(p_id);
        if(isDeleted){
            alert.getSuccessAlert("Patient Deleted Successfully!").show();
            clearFields();
        }else {
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    // check if is there duplicate values return 0=> name , 1=> contact -1=> no duplicate   type=> save->s update->u
    private int checkDataIsDuplicate(int id, String name, String contact, String type){
        return patientBO.checkDuplicateData(id, name, contact, type);
    }

    // load table data to the fields
    @FXML
    public void getPatientTableData(){
        TableView.TableViewSelectionModel<PatientDTO> selectedPatient = patient_table.getSelectionModel();
        PatientDTO patient = selectedPatient.getSelectedItem();

        if(patient != null){
            p_id_field.setText(patient.getId());
            p_name_field.setText(patient.getName());
            p_contact_field.setText(patient.getContact());
            p_address_field.setText(patient.getAddress());
            p_gender_box.setValue(patient.getGender());
            p_btn_delete.setDisable(false);
            p_btn_update.setDisable(false);
            p_btn_save.setDisable(true);
        }
    }


    // Get next patient id ---------------------------
    void getNextPID(){
        String nextId = patientBO.getNextID();
        p_id_field.setText(nextId);
    }


    // Clear patient form ---------------------------
    @FXML
    private void clearFields() {
        p_name_field.setText("");
        p_contact_field.setText("");
        p_address_field.setText("");
        p_gender_box.setValue("");
        p_gender_box.setPromptText("Select a gender");
        p_search_field.setText("");
        p_btn_save.setDisable(false);
        p_btn_update.setDisable(true);
        p_btn_delete.setDisable(true);
        loadPatients();
        getNextPID();
        p_check_box.setSelected(false);
    }

    // Search patient ---------------------------
    @FXML
    private void handelSearchPatient(){
        String text = p_search_field.getText().trim();
        List<PatientDTO> patients = patientBO.searchPatient(text);

        if(patients.isEmpty()){
            alert.getInfoAlert("Patient Not Found!").show();
            return;
        }

        ObservableList<PatientDTO> obList = FXCollections.observableArrayList();
        obList.addAll(patients);
        patient_table.setItems(obList);
    }

    @FXML
    private void makeVisible(){
        p_search_text.setVisible(true);
    }

    @FXML
    private void makeNotVisible(){
        p_search_text.setVisible(false);
    }

    @FXML
    void printAll(){
        if(p_check_box.isSelected()){
            patientBO.printAllAttends();
        }
        else{
            patientBO.printPatients();
        }
    }

    public void SearchPatientOnAction(ActionEvent event) {

    }
}
