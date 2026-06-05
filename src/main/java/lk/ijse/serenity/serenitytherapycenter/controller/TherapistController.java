package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.BOFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.TherapistBo;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.util.List;

public class TherapistController {

    // therapist table --------------------------
    @FXML private TableView<TherapistDTO> therapist_table;
    @FXML private TableColumn<TherapistDTO, String> col_t_contact;
    @FXML private TableColumn<TherapistDTO, String> col_t_email_address;
    @FXML private TableColumn<TherapistDTO, String> col_t_id;
    @FXML private TableColumn<TherapistDTO, String> col_t_name;

    // input fields -------------------
    @FXML private TextField t_id_field;
    @FXML private TextField t_name_field;
    @FXML private TextField t_contact_field;
    @FXML private TextField t_email_field;
    @FXML private TextField t_search_field;

    // buttons ---------------------------
    @FXML private Button t_btn_save;
    @FXML private Button t_btn_update;
    @FXML private Button t_btn_delete;

    @FXML private Label p_search_text;

    private final Alerts alert = new Alerts("Therapist Management.");
    private final TherapistBo therapistBo = (TherapistBo) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);

    private final String THERAPIST_NAME_REGEX    = "^[A-Za-z\\s]{3,}$";
    private final String THERAPIST_CONTACT_REGEX = "^[0-9]{10}$";
    private final String THERAPIST_EMAIL_REGEX = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

    public void initialize(){

        // initialize therapist table
        col_t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_t_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_t_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_t_email_address.setCellValueFactory(new PropertyValueFactory<>("email"));

        therapist_table.setOnMouseClicked(event -> {
            getTherapistTableData();
        });

        loadTherapistTable();
        getNextPID();

        p_search_text.setVisible(false);
        t_btn_update.setDisable(true);
        t_btn_delete.setDisable(true);
    }

    void loadTherapistTable(){
        try{
            List<TherapistDTO> therapists = therapistBo.getAllTherapists();
            ObservableList<TherapistDTO> obList = FXCollections.observableArrayList();
            obList.addAll(therapists);
            therapist_table.setItems(obList);
        }catch (Exception e){
            e.printStackTrace();
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    // Get next therapist id ---------------------------
    void getNextPID(){
        String nextId = therapistBo.getNextID();
        t_id_field.setText(nextId);
    }

    @FXML
    void handelSaveTherapist() {
        String name     = t_name_field.getText();
        String contact  = t_contact_field.getText();
        String email    = t_email_field.getText();

        if(!name.matches(THERAPIST_NAME_REGEX)){
            alert.getErrorAlert("Invalid Name!").show();
        }
        else if(!contact.matches(THERAPIST_CONTACT_REGEX)){
            alert.getErrorAlert("Invalid Contact!").show();
        }
        else if(!email.matches(THERAPIST_EMAIL_REGEX)){
            alert.getErrorAlert("Invalid Email!").show();
        }
        else{
            // check if is there duplicate values
            int duplicateVal = checkDataIsDuplicate(0, name, contact, email, "s");
            if(duplicateVal == 0){
                alert.getErrorAlert("Therapist name is already exist!").show();
                return;
            }
            else if(duplicateVal == 1){
                alert.getErrorAlert("Therapist contact is already exist!").show();
                return;
            } else if (duplicateVal == 2) {
                alert.getErrorAlert("Therapist email is already exist!").show();
                return;
            }

            // save therapist data
            TherapistDTO therapist = new TherapistDTO();
            therapist.setName(name.trim());
            therapist.setContact(contact);
            therapist.setEmail(email.trim());

            boolean isSaved = therapistBo.saveTherapist(therapist);
            if(isSaved){
                alert.getSuccessAlert("Therapist Saved Successfully!").show();
                clearFields();
            }else {
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }

    }

    // check if is there duplicate values return 0=> name , 1=> contact , 2=> email , -1=> no duplicate
    // type=> save->s update->u
    private int checkDataIsDuplicate(int id, String name, String contact, String email, String type){
        return therapistBo.checkDuplicateData(id, name, contact, email, type);
    }

    @FXML
    void handelUpdateTherapist() {
        String id       = t_id_field.getText();
        String name     = t_name_field.getText();
        String contact  = t_contact_field.getText();
        String email    = t_email_field.getText();

        if(!name.matches(THERAPIST_NAME_REGEX)){
            alert.getErrorAlert("Invalid Name!").show();
        }
        else if(!contact.matches(THERAPIST_CONTACT_REGEX)){
            alert.getErrorAlert("Invalid Contact!").show();
        }
        else if(!email.matches(THERAPIST_EMAIL_REGEX)){
            alert.getErrorAlert("Invalid Email!").show();
        }
        else {// check if is there duplicate values
            int t_id = Integer.parseInt(id.substring(2));
            int duplicateVal = checkDataIsDuplicate(t_id, name, contact, email, "u");
            if (duplicateVal == 0) {
                alert.getErrorAlert("Therapist name is already exist!").show();
                return;
            } else if (duplicateVal == 1) {
                alert.getErrorAlert("Therapist contact is already exist!").show();
                return;
            } else if (duplicateVal == 2) {
                alert.getErrorAlert("Therapist email is already exist!").show();
                return;
            }

            TherapistDTO therapist = new TherapistDTO();
            therapist.setId(t_id+"");
            therapist.setName(name.trim());
            therapist.setContact(contact);
            therapist.setEmail(email.trim());

            boolean isUpdated = therapistBo.updateTherapist(therapist);
            if(isUpdated){
                alert.getSuccessAlert("Therapist Updated Successfully!").show();
                clearFields();
            }else {
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelDeleteTherapist() {
        String id = t_id_field.getText().trim();
        if(id.isEmpty()){
            alert.getErrorAlert("Please Select a Therapist").show();
            return;
        }

        int t_id = Integer.parseInt(id.substring(2));
        boolean isDeleted = therapistBo.deleteTherapist(t_id);
        if(isDeleted){
            alert.getSuccessAlert("Therapist Deleted Successfully!").show();
            clearFields();
        }else {
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    @FXML
    void handelSearchTherapist() {
        String text = t_search_field.getText().trim();
        List<TherapistDTO> therapists = therapistBo.searchTherapists(text);

        if(therapists.isEmpty()){
            alert.getInfoAlert("Therapist Not Found!").show();
            return;
        }

        ObservableList<TherapistDTO> obList = FXCollections.observableArrayList();
        obList.addAll(therapists);
        therapist_table.setItems(obList);
    }

    @FXML
    void getTherapistTableData() {
        TableView.TableViewSelectionModel<TherapistDTO> selectedTherapist = therapist_table.getSelectionModel();
        TherapistDTO therapist = selectedTherapist.getSelectedItem();

        if(therapist != null){
            t_id_field.setText((therapist.getId()));
            t_name_field.setText((therapist.getName()));
            t_contact_field.setText(therapist.getContact());
            t_email_field.setText(therapist.getEmail());
            t_btn_save.setDisable(true);
            t_btn_update.setDisable(false);
            t_btn_delete.setDisable(false);
        }
    }

    @FXML
    void clearFields() {
        t_id_field.setText("");
        t_name_field.setText("");
        t_contact_field.setText("");
        t_email_field.setText("");
        t_search_field.setText("");
        t_btn_save.setDisable(false);
        t_btn_update.setDisable(true);
        t_btn_delete.setDisable(true);
        loadTherapistTable();
        getNextPID();
    }

    @FXML
    void makeNotVisible() {
        p_search_text.setVisible(false);
    }

    @FXML
    void makeVisible() {
        p_search_text.setVisible(true);
    }

    @FXML
    void printTherapistData() {
        therapistBo.printData();
    }

}
