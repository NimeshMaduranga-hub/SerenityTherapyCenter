package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.TherapistBo;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.TherapistDao;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.Program;
import lk.ijse.serenity.serenitytherapycenter.entity.Therapist;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBo {

    TherapistDao therapistDao = (TherapistDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public boolean saveTherapist(TherapistDTO t) {
        Therapist therapist = new Therapist();
        therapist.setName(t.getName());
        therapist.setContact(t.getContact());
        therapist.setEmail(t.getEmail());

        return therapistDao.save(therapist);
    }

    @Override
    public boolean updateTherapist(TherapistDTO t) {
        Therapist therapist = new Therapist();
        therapist.setId(Integer.parseInt(t.getId()));
        therapist.setName(t.getName());
        therapist.setContact(t.getContact());
        therapist.setEmail(t.getEmail());

        return therapistDao.update(therapist);
    }

    @Override
    public boolean deleteTherapist(int id) {
        return therapistDao.delete(id);
    }

    @Override
    public List<TherapistDTO> getAllTherapists() {
        List<Therapist> therapistList = therapistDao.getAll();
        List<TherapistDTO> therapistDTOS = new ArrayList<>();
        for(Therapist t : therapistList){
            TherapistDTO tDto = new TherapistDTO();
            tDto.setId("T_"+t.getId());
            tDto.setName(t.getName());
            tDto.setContact(t.getContact());
            tDto.setEmail(t.getEmail());

            therapistDTOS.add(tDto);
        }
        return therapistDTOS;
    }

    @Override
    public List<TherapistDTO> searchTherapists(String text) {
        List<Therapist> therapists = therapistDao.search(text);
        List<TherapistDTO> therapistDTOS = new ArrayList<>();
        for(Therapist t : therapists){
            TherapistDTO tDto = new TherapistDTO();
            tDto.setId("T_"+t.getId());
            tDto.setName(t.getName());
            tDto.setContact(t.getContact());
            tDto.setEmail(t.getEmail());

            therapistDTOS.add(tDto);
        }
        return  therapistDTOS;
    }

    @Override
    public int checkDuplicateData(int id, String name, String contact, String email, String type) {
        return therapistDao.checkDuplicateData(id, name, contact, email, type);
    }

    @Override
    public String getNextID() {
        return therapistDao.getNextID();
    }

    @Override
    public boolean linkProgram(TherapistDTO t, ProgramDTO p) {
        Therapist therapist = new Therapist();
        therapist.setId(Integer.parseInt(t.getId().substring(2)));
        therapist.setName(t.getName());
        therapist.setContact(t.getContact());
        therapist.setEmail(t.getEmail());

        Program program = new Program();
        program.setId(Integer.parseInt(p.getId().substring(3)));
        program.setName(p.getName());
        program.setDuration(p.getDuration());
        program.setFee(p.getFee());

        return therapistDao.addProgram(therapist, program);
    }

    @Override
    public boolean unlinkProgram(TherapistDTO t, ProgramDTO p) {
        int therapistID = Integer.parseInt(t.getId().substring(2));
        int programID = Integer.parseInt(p.getId().substring(3));
        return therapistDao.removeProgram(therapistID, programID);
    }

    @Override
    public void printData() {
        List<Therapist> therapistList = therapistDao.getAll();
        List<TherapistDTO> therapistDTOS = new ArrayList<>();
        for(Therapist t : therapistList){
            TherapistDTO tDto = new TherapistDTO();
            tDto.setId("T_"+t.getId());
            tDto.setName(t.getName());
            tDto.setContact(t.getContact());
            tDto.setEmail(t.getEmail());

            therapistDTOS.add(tDto);
        }

        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/Therapist_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(therapistDTOS);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getAllTherapistCount() {
        return therapistDao.getAll().size();
    }
}
