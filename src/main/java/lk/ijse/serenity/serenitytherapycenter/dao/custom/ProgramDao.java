package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Program;
import lk.ijse.serenity.serenitytherapycenter.entity.Therapist;

import java.util.List;

public interface ProgramDao extends CrudDao<Program> {
    String getNextID();
    List<Program> getAllProgramsWithTherapists();
    List<Therapist> getProgramWithTherapists(int id);
    Program getDataByID(int id);
}
