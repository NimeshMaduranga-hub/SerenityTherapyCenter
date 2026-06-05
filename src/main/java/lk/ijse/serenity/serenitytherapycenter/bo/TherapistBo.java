package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.ProgramDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.TherapistDTO;

import java.util.List;

public interface TherapistBo extends SuperBO{

    boolean saveTherapist(TherapistDTO t);
    boolean updateTherapist(TherapistDTO t);
    boolean deleteTherapist(int id);
    List<TherapistDTO> getAllTherapists();
    List<TherapistDTO> searchTherapists(String text);
    int checkDuplicateData(int id, String name, String contact, String email, String type);
    String getNextID();
    boolean linkProgram(TherapistDTO t, ProgramDTO p);
    boolean unlinkProgram(TherapistDTO t, ProgramDTO p);
    void printData();
    int getAllTherapistCount();
}
