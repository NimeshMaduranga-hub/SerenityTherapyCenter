package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.BOFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.ProgramBO;
import lk.ijse.serenity.serenitytherapycenter.bo.SessionBO;
import lk.ijse.serenity.serenitytherapycenter.bo.TherapistBo;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.SessionDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.SessionTM;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.util.List;

public class SessionController {


    // session table ------------------------------
    @FXML private TableView<SessionTM> session_table;
    @FXML private TableColumn<SessionTM, String> col_id;
    @FXML private TableColumn<SessionTM, String> col_program;
    @FXML private TableColumn<SessionTM, String> col_therapist;
    @FXML private TableColumn<SessionTM, String> col_day;
    @FXML private TableColumn<SessionTM, String> col_time;
    @FXML private TableColumn<SessionTM, String> col_status;

    // buttons ---------------------------
    @FXML private Button s_btn_save;
    @FXML private Button s_btn_update;
    @FXML private Button s_btn_print;

    // therapist input fields -----------------
    @FXML private ComboBox<String> s_t_combo_box;
    @FXML private TextField s_t_contact_field;
    @FXML private TextField s_t_email_field;

    @FXML private ComboBox<String> s_status_combo_box;

    // program input fields -----------------
    @FXML private TextField s_id_field;
    @FXML private ComboBox<String> s_program_combo_box;
    @FXML private TextField s_duration_field;
    @FXML private TextField s_fee_field;
    @FXML private ComboBox<String> s_day_combo_box;
    @FXML private ComboBox<String> s_time_combo_box;
    @FXML private TextField s_search_field;

    // radio buttons --------------------
    @FXML private RadioButton s_id_radio;
    @FXML private RadioButton s_p_radio;
    @FXML private RadioButton s_t_radio;

    // variables ------------------------------------------
    String[] timeArray = {"8:00 AM", "10:00 AM", "12:00 AM", "14:00 PM", "16:00 PM", "18:00 PM"};
    String[] days      = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] status   = {"Ongoing", "Canceled"};
    List<TherapistDTO> therapistList ;

    private final Alerts alert = new Alerts("Session Management.");
    TherapistDTO selectedTherapist ;
    ProgramDTO selectedProgram;
    String selectedSessionID = null;

    // BOs ----------------------------------------------
    private final SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SESSION);
    private final TherapistBo therapistBo = (TherapistBo) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);
    private final ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAM);

    public void initialize(){

        // initialize table columns
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_program.setCellValueFactory(new PropertyValueFactory<>("program"));
        col_therapist.setCellValueFactory(new PropertyValueFactory<>("therapist"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // initialize day combobox
        ObservableList<String> obDays = FXCollections.observableArrayList(days);
        s_day_combo_box.setItems(obDays);

        // initialize programs combobox
        ObservableList<String> obProgramList = FXCollections.observableArrayList();
        List<ProgramDTO> programs = programBO.getAllPrograms();
        for(ProgramDTO p : programs){
            obProgramList.add(p.getId() + " - " + p.getName());
        }
        s_program_combo_box.setItems(obProgramList);

        // initialize status combobox
        ObservableList<String> obStatusList = FXCollections.observableArrayList(status);
        s_status_combo_box.setItems(obStatusList);
        s_status_combo_box.setValue(status[0]);
        s_status_combo_box.setDisable(true);

        loadSessionTable();
        getNextID();
    }

    // load session data to table
    @FXML
    void loadSessionTable(){
        List<SessionTM> sessions = sessionBO.getAllSessions();
        ObservableList<SessionTM> obList = FXCollections.observableArrayList();
        obList.addAll(sessions);
        session_table.setItems(obList);
    }

    // get session table data
    @FXML
    void getSessionTableData(){
        TableView.TableViewSelectionModel<SessionTM> selectedS = session_table.getSelectionModel();
        SessionTM sessionTM = selectedS.getSelectedItem();

        if(sessionTM == null){
            return;
        }

        s_id_field.setText(sessionTM.getId());
        selectedSessionID = sessionTM.getId();
        s_program_combo_box.setValue(sessionTM.getProgram());
        s_t_combo_box.setValue(sessionTM.getTherapist());
        s_day_combo_box.setValue(sessionTM.getDay());
        s_time_combo_box.setValue(sessionTM.getTime());
        s_status_combo_box.setValue(sessionTM.getStatus());

        // enable fields
        s_t_combo_box.setDisable(false);
        s_btn_save.setDisable(true);
        s_btn_update.setDisable(false);
        s_status_combo_box.setDisable(false);
        s_btn_print.setDisable(false);

    }

    // initialize combo box to fill program data when program is selected
    @FXML
    void fillProgramData(){
        String text = s_program_combo_box.getValue();
        if(text == null){ return; }
        int num = text.indexOf("-");
        if(num > -1){
            int end = num - 1;
            selectedProgram = programBO.searchProgram(text.substring(0, end)).getFirst();
        }
        s_duration_field.setText(selectedProgram.getDuration());
        s_fee_field.setText(selectedProgram.getFee()+"");

        int programID = Integer.parseInt(selectedProgram.getId().substring(3));
        therapistList = programBO.getProgramWithTherapists(programID);

        initializeTherapistCombobox();
        s_t_combo_box.setDisable(false);
    }

    // initialize Therapist combobox
    void initializeTherapistCombobox(){
        ObservableList<String> obTherapistList = FXCollections.observableArrayList();
        for (TherapistDTO t : therapistList){
            obTherapistList.add(t.getId() + " - " + t.getName());
        }
        s_t_combo_box.setItems(obTherapistList);
    }

    // initialize combo box to fill therapist data when therapist is selected
    @FXML
    void fillTherapistData(){
        String text = s_t_combo_box.getValue();
        if(text == null){ return;}
        int num = text.indexOf("-");
        if(num < 0){
            return;
        }
        else{
            int end = num -1;
            selectedTherapist = therapistBo.searchTherapists(text.substring(0, end)).getFirst();
        }
        s_t_contact_field.setText(selectedTherapist.getContact());
        s_t_email_field.setText(selectedTherapist.getEmail());

        s_day_combo_box.setDisable(false);
        s_day_combo_box.setValue(null);
    }

    // initialize Time combobox
    @FXML
    void initializeTimeCombobox(){
        if(selectedTherapist == null){ return; }
        if(s_day_combo_box.getValue() == null){
            s_time_combo_box.setValue(null);
            return;
        }
        s_time_combo_box.setDisable(false);
//        s_time_combo_box.setValue("");
        String chosenDay = s_day_combo_box.getValue();

        List<String> chosenTimes = sessionBO.getChosenTimes(selectedTherapist.getId(), chosenDay);
        ObservableList<String> obTime = FXCollections.observableArrayList();

        L1:for(int y=0; y<timeArray.length; y++){
            for(int i=0; i<chosenTimes.size(); i++){
                if(chosenTimes.get(i).equals(timeArray[y])){
                    continue L1;
                }
            }
            obTime.add(timeArray[y]);
        }
        s_time_combo_box.setItems(obTime);
    }

    // Get next patient id ---------------------------
    void getNextID(){
        String nextId = sessionBO.getNextID();
        s_id_field.setText(nextId);
    }

    @FXML
    void clearFields() {
        s_t_combo_box.setValue(null);
        s_program_combo_box.setValue(null);
        s_duration_field.setText("");
        s_fee_field.setText("");
        s_t_contact_field.setText("");
        s_t_email_field.setText("");
        s_search_field.setText("");
        selectedProgram = null;
        selectedTherapist = null;
        selectedSessionID = null;
        s_day_combo_box.setValue(null);
        s_time_combo_box.setValue(null);
        getNextID();
        loadSessionTable();
        s_btn_save.setDisable(false);
        s_btn_update.setDisable(true);
        s_t_combo_box.setDisable(true);
        s_status_combo_box.setValue(status[0]);
        s_status_combo_box.setDisable(true);
        s_time_combo_box.setDisable(true);
        s_day_combo_box.setDisable(true);
        s_btn_print.setDisable(true);
    }

    @FXML
    void handelSaveSession() {
        String day = s_day_combo_box.getValue();
        String time = s_time_combo_box.getValue();
        String status = s_status_combo_box.getValue();

        if(selectedProgram == null){
            alert.getInfoAlert("Select a Program").show();
        }
        else if (selectedTherapist == null) {
            alert.getInfoAlert("Select a Therapist").show();
        }
        else if (day==null) {
            alert.getInfoAlert("Select a Day").show();
        }
        else if (time==null) {
            alert.getInfoAlert("Select a Time").show();
        }
        else{
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setDay(day);
            sessionDTO.setTime(time);
            sessionDTO.setStatus(status);
            sessionDTO.setProgram(selectedProgram);
            sessionDTO.setTherapist(selectedTherapist);

            boolean isSaved = sessionBO.saveSession(sessionDTO);
            if(isSaved){
                alert.getSuccessAlert("Session Saved Successfully!").show();
                clearFields();
            }else{
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelUpdateSession() {
        String day = s_day_combo_box.getValue();
        String time = s_time_combo_box.getValue();
        String status = s_status_combo_box.getValue();

        if(selectedProgram == null){
            alert.getInfoAlert("Select a Program").show();
        }
        else if (selectedTherapist == null) {
            alert.getInfoAlert("Select a Therapist").show();
        }
        else if (day==null) {
            alert.getInfoAlert("Select a Day").show();
        }
        else if (time==null) {
            alert.getInfoAlert("Select a Time").show();
        }
        else{
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setId(s_id_field.getText());
            sessionDTO.setDay(day);
            sessionDTO.setTime(time);
            sessionDTO.setStatus(status);
            sessionDTO.setProgram(selectedProgram);
            sessionDTO.setTherapist(selectedTherapist);

            boolean isUpdated = sessionBO.updateSession(sessionDTO);
            if(isUpdated){
                alert.getSuccessAlert("Session Updated Successfully!").show();
                clearFields();
            }else{
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelSearchSession() {
        String text = s_search_field.getText();
        int searchNum = -1;

        if(s_id_radio.isSelected()){
            searchNum = 0;
        }
        else if(s_t_radio.isSelected()){
            searchNum = 1;
        }
        else if(s_p_radio.isSelected()){
            searchNum = 2;
        }

        if(text.isEmpty() && (searchNum == 0)){
            alert.getInfoAlert("Please Enter an ID").show();
        }
        else if(text.isEmpty() && (searchNum == 1)){
            alert.getInfoAlert("Please Enter Therapist Name").show();
        }
        else if(text.isEmpty() && (searchNum == 2)){
            alert.getInfoAlert("Please Enter Program Name").show();
        }
        else if((searchNum == 0) && !(text.startsWith("S_"))){
            alert.getErrorAlert("Invalid ID!").show();
        }
        else{
            List<SessionTM> sessionTM = sessionBO.searchSession(text, searchNum); //  0 => ID,  1 => Therapist,  2 => Program
            if(sessionTM.isEmpty()){
                alert.getInfoAlert("Session Not Found!").show();
                return;
            }

            ObservableList<SessionTM> obList  = FXCollections.observableArrayList();
            obList.addAll(sessionTM);
            session_table.setItems(obList);
        }

    }


    // radio button behaviors
    @FXML
    void uncheckOthers(ActionEvent actionEvent){
        s_id_radio.setSelected(false);
        s_t_radio.setSelected(false);
        s_p_radio.setSelected(false);
        RadioButton source = (RadioButton) actionEvent.getSource();
        source.setSelected(true);
    }

    @FXML
    void printSession(){
        if(selectedSessionID == null){
            alert.getInfoAlert("Select a Session!").show();
            return;
        }
        sessionBO.printDetails(selectedSessionID);
    }

    @FXML
    void printAllSession(){
        sessionBO.printAllDetails();
    }

}
