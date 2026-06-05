package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.ProgramBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.ProgramDao;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramTherapistDTO;
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

public class ProgramBOImpl  implements ProgramBO {

    private final ProgramDao programDao = (ProgramDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public boolean saveProgram(ProgramDTO p) {
        Program program = new Program();
        program.setName(p.getName());
        program.setDuration(p.getDuration());
        program.setFee(p.getFee());
        return programDao.save(program);
    }

    @Override
    public boolean updateProgram(ProgramDTO p) {
        Program program = new Program();
        program.setId(Integer.parseInt(p.getId()));
        program.setName(p.getName());
        program.setDuration(p.getDuration());
        program.setFee(p.getFee());
        return programDao.update(program);
    }

    @Override
    public boolean deleteProgram(int id) {
        return programDao.delete(id);
    }

    @Override
    public List<ProgramDTO> searchProgram(String text) {
        List<Program> programs = programDao.search(text);
        List<ProgramDTO> programDTOS = new ArrayList<>();
        for(Program p : programs){
            ProgramDTO pDto = new ProgramDTO();
            pDto.setId("PR_"+p.getId());
            pDto.setName(p.getName());
            pDto.setDuration(p.getDuration());
            pDto.setFee(p.getFee());

            programDTOS.add(pDto);
        }
        return programDTOS;
    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        List<Program> programs = programDao.getAll();
        List<ProgramDTO> programDTOS = new ArrayList<>();
        for(Program p : programs){
            ProgramDTO pDto = new ProgramDTO();
            pDto.setId("PR_"+p.getId());
            pDto.setName(p.getName());
            pDto.setDuration(p.getDuration());
            pDto.setFee(p.getFee());

            programDTOS.add(pDto);
        }
        return programDTOS;
    }

    @Override
    public String getNextID() {
        return programDao.getNextID();
    }

    @Override
    public List<ProgramTherapistDTO> getAllProgramsWithTherapists() {
        List<Program> programs = programDao.getAllProgramsWithTherapists();
        List<ProgramTherapistDTO> ptList = new ArrayList<>();

        for(Program p : programs){
            for(Therapist t : p.getTherapists()){
                ProgramTherapistDTO pt = new ProgramTherapistDTO();
                pt.setPrID("PR_"+p.getId());
                pt.setPrName(p.getName());

                pt.setTrID("T_"+t.getId());
                pt.setTrName(t.getName());

                ptList.add(pt);
            }
        }
        return ptList;
    }

    @Override
    public List<TherapistDTO> getProgramWithTherapists(int id) {
        List<Therapist> therapists = programDao.getProgramWithTherapists(id);
        List<TherapistDTO> therapistDTOS = new ArrayList<>();
        for(Therapist t : therapists){
            TherapistDTO tDto = new TherapistDTO();
            tDto.setId("T_" + t.getId());
            tDto.setName(t.getName());
            therapistDTOS.add(tDto);
        }
        return therapistDTOS;
    }

    @Override
    public ProgramDTO getDataById(String id) {
        Program p = programDao.getDataByID(Integer.parseInt(id.substring(3)));
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setId("PR_"+ p.getId());
        programDTO.setName(p.getName());
        programDTO.setDuration(p.getDuration());
        programDTO.setFee(p.getFee());

        return programDTO;
    }

    @Override
    public void printData() {
        List<Program> programs = programDao.getAllProgramsWithTherapists();
        List<ProgramTherapistDTO> ptList = new ArrayList<>();

        for(Program p : programs){
            for(Therapist t : p.getTherapists()){
                ProgramTherapistDTO pt = new ProgramTherapistDTO();
                pt.setPrID("PR_"+p.getId());
                pt.setPrName(p.getName());

                pt.setTrID("T_"+t.getId());
                pt.setTrName(t.getName());

                ptList.add(pt);
            }
        }

        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/program_detail_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ptList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getAllProgramCount() {
        return programDao.getAll().size();
    }
}
