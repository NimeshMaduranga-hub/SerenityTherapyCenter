package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.SessionBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.SessionDao;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.SessionDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.SessionTM;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.Program;
import lk.ijse.serenity.serenitytherapycenter.entity.Sessions;
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

public class SessionBOImpl implements SessionBO {
    SessionDao sessionDao = (SessionDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.SESSION);

    @Override
    public boolean saveSession(SessionDTO s) {
        Sessions session = new Sessions();
        Program program = new Program();
        Therapist therapist = new Therapist();
        therapist.setId(Integer.parseInt(s.getTherapist().getId().substring(2)));
        program.setId(Integer.parseInt(s.getProgram().getId().substring(3)));

        session.setDay(s.getDay());
        session.setTime(s.getTime());
        session.setStatus(s.getStatus());
        session.setProgram(program);
        session.setTherapist(therapist);

        return sessionDao.save(session);
    }

    @Override
    public boolean updateSession(SessionDTO s) {
        Sessions session = new Sessions();
        Program program = new Program();
        Therapist therapist = new Therapist();
        therapist.setId(Integer.parseInt(s.getTherapist().getId().substring(2)));
        program.setId(Integer.parseInt(s.getProgram().getId().substring(3)));

        session.setId(Integer.parseInt(s.getId().substring(2)));
        session.setDay(s.getDay());
        session.setTime(s.getTime());
        session.setStatus(s.getStatus());
        session.setProgram(program);
        session.setTherapist(therapist);

        return sessionDao.update(session);
    }

    @Override
    public String getNextID() {
        return sessionDao.getNextID();
    }

    @Override
    public List<String> getChosenTimes(String id, String day) {
        int therapistId = Integer.parseInt(id.substring(2));
        List<Sessions> sessions = sessionDao.getChosenTimes(therapistId, day);
        List<String> times = new ArrayList<>();
        for(Sessions s : sessions){
            times.add(s.getTime());
        }
        return  times;
    }

    @Override
    public List<SessionTM> getAllSessions() {
        List<Sessions> sessions = sessionDao.getAll();
        List<SessionTM> sessionTMS = new ArrayList<>();
        for(Sessions s : sessions){
            SessionTM sessionTM = new SessionTM();
            sessionTM.setId("S_" + s.getId());
            sessionTM.setTherapist("T_" + s.getTherapist().getId() + " - " + s.getTherapist().getName());
            sessionTM.setProgram("PR_" + s.getProgram().getId() + " - " + s.getProgram().getName());
            sessionTM.setDay(s.getDay());
            sessionTM.setTime(s.getTime());
            sessionTM.setStatus(s.getStatus());

            sessionTMS.add(sessionTM);
        }
        return sessionTMS;
    }

    @Override
    public List<SessionTM> searchSession(String text ,int searchNum) {
        List<Sessions> sessionsList = sessionDao.search(text ,searchNum);
        List<SessionTM> sessionTMS = new ArrayList<>();
        for(Sessions s : sessionsList){
            SessionTM sessionTM = new SessionTM();
            sessionTM.setId("S_" + s.getId());
            sessionTM.setTherapist("T_" + s.getTherapist().getId() + " - " + s.getTherapist().getName());
            sessionTM.setProgram("PR_" + s.getProgram().getId() + " - " + s.getProgram().getName());
            sessionTM.setDay(s.getDay());
            sessionTM.setTime(s.getTime());
            sessionTM.setStatus(s.getStatus());

            sessionTMS.add(sessionTM);
        }
        return  sessionTMS;
    }

    @Override
    public SessionDTO searchSessionByID(String id) {
        return getSessionData(Integer.parseInt(id.substring(2)));
    }

    SessionDTO getSessionData(int id){
        Sessions s = sessionDao.getSessionByID(id);
        SessionDTO sessionDTO = new SessionDTO();
        ProgramDTO programDTO = new ProgramDTO();
        TherapistDTO therapistDTO = new TherapistDTO();

        programDTO.setName(s.getProgram().getName());
        programDTO.setDuration(s.getProgram().getDuration());
        programDTO.setFee(s.getProgram().getFee());

        therapistDTO.setName(s.getTherapist().getName());
        therapistDTO.setContact(s.getTherapist().getContact());
        therapistDTO.setEmail(s.getTherapist().getEmail());

        sessionDTO.setId("S_" + s.getId());
        sessionDTO.setProgram(programDTO);
        sessionDTO.setProgramID("PR_"+ s.getProgram().getId());
        sessionDTO.setProgramName(s.getProgram().getName());
        sessionDTO.setProgramDuration(s.getProgram().getDuration());
        sessionDTO.setProgramFee("Rs." + s.getProgram().getFee());
        sessionDTO.setTherapist(therapistDTO);
        sessionDTO.setTherapistID("T_" + s.getTherapist().getId());
        sessionDTO.setTherapistName(s.getTherapist().getName());
        sessionDTO.setTherapistContact(s.getTherapist().getContact());
        sessionDTO.setTherapistEmail(s.getTherapist().getEmail());
        sessionDTO.setDay(s.getDay());
        sessionDTO.setTime(s.getTime());
        sessionDTO.setStatus(s.getStatus());

        return sessionDTO;
    }

    @Override
    public void printDetails(String id) {
        SessionDTO session = getSessionData(Integer.parseInt(id.substring(2)));
        List<SessionDTO> sessionDTOS = new ArrayList<>();
        sessionDTOS.add(session);
        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/session_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sessionDTOS);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void printAllDetails() {
        List<Sessions> sessions = sessionDao.getAll();
        List<SessionDTO> sessionDTOList = new ArrayList<>();
        for(Sessions s : sessions){
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setId("S_" + s.getId());
            sessionDTO.setProgramID("PR_"+ s.getProgram().getId());
            sessionDTO.setProgramName(s.getProgram().getName());
            sessionDTO.setProgramDuration(s.getProgram().getDuration());
            sessionDTO.setProgramFee("Rs." + s.getProgram().getFee());
            sessionDTO.setTherapistID("T_" + s.getTherapist().getId());
            sessionDTO.setTherapistName(s.getTherapist().getName());
            sessionDTO.setTherapistContact(s.getTherapist().getContact());
            sessionDTO.setTherapistEmail(s.getTherapist().getEmail());
            sessionDTO.setDay(s.getDay());
            sessionDTO.setTime(s.getTime());
            sessionDTO.setStatus(s.getStatus());

            sessionDTOList.add(sessionDTO);
        }

        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/all_sessions_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sessionDTOList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<SessionTM> getAvailableSessions() {
        List<Sessions> sessions = sessionDao.getAll();
        List<SessionTM> sessionTMS = new ArrayList<>();
        for(Sessions s : sessions){
            if(s.getStatus().equals("Ongoing")){
                SessionTM sessionTM = new SessionTM();
                sessionTM.setId("S_" + s.getId());
                sessionTM.setTherapist("T_" + s.getTherapist().getId() + " - " + s.getTherapist().getName());
                sessionTM.setProgram("PR_" + s.getProgram().getId() + " - " + s.getProgram().getName());

                sessionTMS.add(sessionTM);
            }
        }
        return sessionTMS;
    }

    @Override
    public int getAllSessionCount() {
        return sessionDao.getAll().size();
    }
}
