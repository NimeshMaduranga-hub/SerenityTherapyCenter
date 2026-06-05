package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.PatientBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.PatientDao;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.QueryDao;
import lk.ijse.serenity.serenitytherapycenter.dto.PatientDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.Patient;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    private final PatientDao patientDao = (PatientDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.PATIENT);
    private final QueryDao queryDao = (QueryDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean savePatient(PatientDTO p){
        Patient patient = new Patient();
        patient.setName(p.getName());
        patient.setGender(p.getGender());
        patient.setContact(p.getContact());
        patient.setAddress(p.getAddress());

        return patientDao.save(patient);
    }

    @Override
    public boolean updatePatient(PatientDTO p) {
        Patient patient = new Patient();
        patient.setId(Integer.parseInt(p.getId()));
        patient.setName(p.getName());
        patient.setGender(p.getGender());
        patient.setContact(p.getContact());
        patient.setAddress(p.getAddress());

        return patientDao.update(patient);
    }

    @Override
    public boolean deletePatient(int id) {
        return patientDao.delete(id);
    }

    @Override
    public List<PatientDTO> searchPatient(String text) {
        List<Patient> pList = patientDao.search(text);
        List<PatientDTO> patientDTOS = new ArrayList<>();

        for(Patient p : pList){
            PatientDTO pDTO = new PatientDTO();
            pDTO.setId("P_" + p.getId());
            pDTO.setName(p.getName());
            pDTO.setGender(p.getGender());
            pDTO.setContact(p.getContact());
            pDTO.setAddress(p.getAddress());

            patientDTOS.add(pDTO);
        }
        return patientDTOS;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> pList = patientDao.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(Patient p : pList){
            PatientDTO pDTO = new PatientDTO();
            pDTO.setId("P_" + p.getId());
            pDTO.setName(p.getName());
            pDTO.setGender(p.getGender());
            pDTO.setContact(p.getContact());
            pDTO.setAddress(p.getAddress());

            patientDTOS.add(pDTO);
        }
        return patientDTOS;
    }

    @Override
    public int checkDuplicateData(int id, String name, String contact, String type) {
        return patientDao.checkDuplicateData(id, name, contact, type);
    }

    @Override
    public String getNextID() {
        return patientDao.getNextID();
    }

    @Override
    public void printPatients() {
        List<Patient> patients = patientDao.getAll();
        print(patients);
    }

    @Override
    public int getAllPatientCount() {
        return patientDao.getAll().size();
    }

    @Override
    public void printAllAttends() {
        List<Patient> patients = queryDao.getAllAttends();
        print(patients);
    }

    void print(List<Patient> patients){

        List<PatientDTO> patientDTOS = new ArrayList<>();

        for(Patient patient : patients){
            PatientDTO pDTO = new PatientDTO();
            pDTO.setId("P_" + patient.getId());
            pDTO.setName(patient.getName());
            pDTO.setGender(patient.getGender());
            pDTO.setContact(patient.getContact());
            pDTO.setAddress(patient.getAddress());

            patientDTOS.add(pDTO);
        }

        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/patient_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patientDTOS);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
