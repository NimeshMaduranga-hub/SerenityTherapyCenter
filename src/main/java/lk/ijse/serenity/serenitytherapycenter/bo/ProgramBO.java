package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.ProgramTherapistDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;

import java.util.List;

public interface ProgramBO extends SuperBO{

    boolean saveProgram(ProgramDTO p);
    boolean updateProgram(ProgramDTO p);
    boolean deleteProgram(int id);
    List<ProgramDTO> searchProgram(String text);
    List<ProgramDTO> getAllPrograms();
    String getNextID();
    List<ProgramTherapistDTO> getAllProgramsWithTherapists();
    List<TherapistDTO> getProgramWithTherapists(int id);
    ProgramDTO getDataById(String id);
    void printData();
    int getAllProgramCount();
}
