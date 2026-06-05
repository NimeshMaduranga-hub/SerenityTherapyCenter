package lk.ijse.serenity.serenitytherapycenter.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.BOFactory;
import lk.ijse.serenity.serenitytherapycenter.bo.ProgramBO;
import lk.ijse.serenity.serenitytherapycenter.bo.TherapistBo;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramTherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.util.Alerts;

import java.math.BigDecimal;
import java.util.List;

public class ProgramController {

    // table data ------------------
    @FXML private TableView<ProgramDTO> program_table;
    @FXML private TableColumn<ProgramDTO, String> col_pr_id;
    @FXML private TableColumn<ProgramDTO, String> col_pr_name;
    @FXML private TableColumn<ProgramDTO, String> col_pr_duration;
    @FXML private TableColumn<ProgramDTO, String> col_pr_fee;

    // program therapist table data ------------------
    @FXML private TableView<ProgramTherapistDTO> program_therapist_table;
    @FXML private TableColumn<ProgramTherapistDTO, String> col_pr_id_2;
    @FXML private TableColumn<ProgramTherapistDTO, String> col_pr_name_2;
    @FXML private TableColumn<ProgramTherapistDTO, String> col_t_id;
    @FXML private TableColumn<ProgramTherapistDTO, String> col_t_name;

    // input fields Program------------------
    @FXML private TextField pr_id_field;
    @FXML private TextField pr_name_field;
    @FXML private TextField pr_duration_field;
    @FXML private TextField pr_fee_field;
    @FXML private TextField pr_search_field;
    @FXML private Label p_search_text;

    // input fields Therapist------------------
    @FXML private ComboBox<String> pr_t_name_combo_box;
    @FXML private TextField pr_t_id_field;
    @FXML private TextField pr_t_contact_field;
    @FXML private TextField pr_t_email_field;

    // Buttons------------------
    @FXML Button pr_btn_save;
    @FXML Button pr_btn_update;
    @FXML Button pr_btn_delete;
    @FXML Button pr_btn_link;
    @FXML Button pr_btn_unlink;

    // final variables --------------------------
    private final Alerts alert = new Alerts("Program Management.");
    private final ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAM);
    private final TherapistBo therapistBo = (TherapistBo) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);

    // variables --------------------------
    TherapistDTO selectedTherapist ;
    ProgramDTO selectedProgram ;

    public void initialize(){
        // initialize therapist combo box -------------
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<TherapistDTO> therapistDTOList = therapistBo.getAllTherapists();
        for(TherapistDTO dto : therapistDTOList){
            observableList.add(dto.getId() + " - " + dto.getName());
        }
        pr_t_name_combo_box.setItems(observableList);

        // initialize duration text field to input only numbers
        pr_duration_field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d*")){
                return change;
            }
            else{
                return null;
            }
        }));

        // initialize fee text field to input only price with two decimals
        pr_fee_field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d*(\\.\\d{0,2})?")){
                return change;
            }
            else{
                return null;
            }
        }));

        // make buttons and combo box disable true and false
        p_search_text.setVisible(false);
        pr_btn_update.setDisable(true);
        pr_btn_delete.setDisable(true);
        pr_btn_link.setDisable(true);
        pr_btn_unlink.setDisable(true);
        pr_t_name_combo_box.setDisable(true);

        // initialize program table
        col_pr_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_pr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_pr_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col_pr_fee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        // initialize program therapist table
        col_pr_id_2.setCellValueFactory(new PropertyValueFactory<>("prID"));
        col_pr_name_2.setCellValueFactory(new PropertyValueFactory<>("prName"));
        col_t_id.setCellValueFactory(new PropertyValueFactory<>("trID"));
        col_t_name.setCellValueFactory(new PropertyValueFactory<>("trName"));

        loadProgramTable();
        loadProgramTherapistTable();
        program_therapist_table.setVisible(false);
        getNextProgramID();
    }

    // get next program ID ----------------------
    void getNextProgramID(){
        String nextID = programBO.getNextID();
        pr_id_field.setText(nextID);
    }

    // load programs table ----------------------------------
    void loadProgramTable(){
        try{
            List<ProgramDTO> programs = programBO.getAllPrograms();
            ObservableList<ProgramDTO> obList = FXCollections.observableArrayList();
            obList.addAll(programs);
            program_table.setItems(obList);
        }catch (Exception e){
            e.printStackTrace();
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    // initialize combo box to fill therapist data when therapist is selected
    @FXML
    void fillTherapistData(){
        String text = pr_t_name_combo_box.getValue();
        if(text == null){ return; }  // return if text is null -----------

        int num = text.indexOf("-");
        if(num < 0){
            if(text.contains("T_")){
                selectedTherapist = new TherapistDTO();
                selectedTherapist = therapistBo.searchTherapists(text).getFirst();
            }
        }
        else{
            int end = num -1;
            selectedTherapist = therapistBo.searchTherapists(text.substring(0, end)).getFirst();
        }
        pr_t_id_field.setText(selectedTherapist.getId());
        pr_t_contact_field.setText(selectedTherapist.getContact());
        pr_t_email_field.setText(selectedTherapist.getEmail());
    }

    // change table view ------------------------------------------
    @FXML
    void viewProgramDetailTable(){
        program_table.setVisible(false);
        program_therapist_table.setVisible(true);
    }

    // load program therapist table ----------------------------------
    void loadProgramTherapistTable(){
        List<ProgramTherapistDTO> programDetails = programBO.getAllProgramsWithTherapists();
        ObservableList<ProgramTherapistDTO> obList = FXCollections.observableArrayList();
        obList.addAll(programDetails);
        program_therapist_table.setItems(obList);
    }

    @FXML
    void getProgramTableData() {
        TableView.TableViewSelectionModel<ProgramDTO> selectedPr = program_table.getSelectionModel();
        ProgramDTO program = selectedPr.getSelectedItem();

        if(program != null){
            selectedProgram = program;
            pr_id_field.setText(program.getId());
            pr_name_field.setText(program.getName());
            pr_duration_field.setText(program.getDuration());
            pr_fee_field.setText(program.getFee()+"");

            // make buttons disable false and true
            pr_btn_save.setDisable(true);
            pr_btn_update.setDisable(false);
            pr_btn_delete.setDisable(false);
            pr_t_name_combo_box.setDisable(false);
            pr_btn_link.setDisable(false);
        }

    }

    @FXML
    void getProgramTherapistTableData(){
        TableView.TableViewSelectionModel<ProgramTherapistDTO> selectedPT = program_therapist_table.getSelectionModel();
        ProgramTherapistDTO pt = selectedPT.getSelectedItem();

        if(pt != null){
            if(selectedProgram == null) {selectedProgram = new ProgramDTO();}
            selectedProgram.setId(pt.getPrID());

            ProgramDTO programDTO = programBO.getDataById(pt.getPrID());
            pr_id_field.setText(programDTO.getId());
            pr_name_field.setText(programDTO.getName());
            pr_duration_field.setText(programDTO.getDuration());
            pr_fee_field.setText(programDTO.getFee()+"");

            pr_t_name_combo_box.setDisable(false);
            pr_t_name_combo_box.setValue(pt.getTrID() + " - " + pt.getTrName());
            pr_btn_save.setDisable(true);
            pr_btn_link.setDisable(false);
            pr_btn_unlink.setDisable(false);
        }
    }

    // clear all ------------------------------------------
    @FXML
    void clearFields() {
        pr_name_field.setText("");
        pr_duration_field.setText("");
        pr_fee_field.setText("");
        pr_search_field.setText("");
        pr_t_name_combo_box.setValue(null);
        pr_t_id_field.setText("");
        pr_t_contact_field.setText("");
        pr_t_email_field.setText("");
        p_search_text.setVisible(false);
        pr_btn_update.setDisable(true);
        pr_btn_delete.setDisable(true);
        pr_btn_save.setDisable(false);
        pr_t_name_combo_box.setDisable(true);
        program_table.setVisible(true);
        program_therapist_table.setVisible(false);
        pr_btn_link.setDisable(true);
        pr_btn_unlink.setDisable(true);
        loadProgramTable();
        loadProgramTherapistTable();
        getNextProgramID();
    }

    @FXML
    void handelSaveProgram() {
        String name     = pr_name_field.getText();
        String duration = pr_duration_field.getText();
        String fee      = pr_fee_field.getText();

        if(name.isEmpty()){
            alert.getErrorAlert("Invalid Name").show();
        }
        else if(duration.isEmpty()){
            alert.getErrorAlert("Invalid duration").show();
        }
        else if (fee.isEmpty()) {
            alert.getErrorAlert("Invalid Fee").show();
        }
        else{
            BigDecimal pr_fee = BigDecimal.valueOf(Integer.parseInt(fee));
            ProgramDTO programDTO = new ProgramDTO();
            programDTO.setName(name.trim());
            programDTO.setDuration(duration);
            programDTO.setFee(pr_fee);

            boolean isSaved = programBO.saveProgram(programDTO);

            if(isSaved){
                alert.getSuccessAlert("Program Saved Successfully!").show();
                clearFields();
            }else{
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelUpdateProgram() {
        String id       = pr_id_field.getText();
        String name     = pr_name_field.getText();
        String duration = pr_duration_field.getText();
        String fee      = pr_fee_field.getText();

        if(name.isEmpty()){
            alert.getErrorAlert("Invalid Name").show();
        }
        else if(duration.isEmpty()){
            alert.getErrorAlert("Invalid duration").show();
        }
        else if (fee.isEmpty()) {
            alert.getErrorAlert("Invalid Fee").show();
        }
        else{
            BigDecimal pr_fee = BigDecimal.valueOf(Double.parseDouble(fee));
            ProgramDTO programDTO = new ProgramDTO();
            programDTO.setId(id.substring(3));
            programDTO.setName(name.trim());
            programDTO.setDuration(duration);
            programDTO.setFee(pr_fee);

            boolean isUpdated = programBO.updateProgram(programDTO);

            if(isUpdated){
                alert.getSuccessAlert("Program Updated Successfully!").show();
                clearFields();
            }else{
                alert.getErrorAlert("Something Went Wrong!").show();
            }
        }
    }

    @FXML
    void handelDeleteProgram() {
        String id = pr_id_field.getText().trim();
        int pr_id = Integer.parseInt(id.substring(3));
        boolean isDeleted = programBO.deleteProgram(pr_id);

        if(isDeleted){
            alert.getSuccessAlert("Program Deleted Successfully!").show();
            clearFields();
        }else{
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    @FXML
    void handelSearchProgram() {
        String text = pr_search_field.getText().trim();

        if(text.isEmpty()){
            alert.getInfoAlert("Enter ID or Name.").show();
            return;
        }

        List<ProgramDTO> programs = programBO.searchProgram(text);
        if(programs.isEmpty()){
            alert.getInfoAlert("Program Not Found!").show();
            return;
        }
        ObservableList<ProgramDTO> obList = FXCollections.observableArrayList();
        obList.addAll(programs);
        program_table.setItems(obList);
    }

    @FXML
    void handelLinkProgram() {
        boolean isLinked =  therapistBo.linkProgram(selectedTherapist, selectedProgram);
        if(isLinked){
            alert.getSuccessAlert("Program Linked Successfully!").show();
            clearFields();
        }
        else{
            alert.getErrorAlert("Something Went Wrong!").show();
        }
    }

    @FXML
    void handelUnlinkProgram() {
        boolean isLinked =  therapistBo.unlinkProgram(selectedTherapist, selectedProgram);
        if(isLinked){
            alert.getSuccessAlert("Program UnLinked Successfully!").show();
            clearFields();
        }
        else{
            alert.getErrorAlert("Something Went Wrong!").show();
        }
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
    void printProgramDetails(){
        programBO.printData();
    }

}
