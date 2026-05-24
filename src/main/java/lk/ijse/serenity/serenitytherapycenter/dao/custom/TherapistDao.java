package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Program;
import lk.ijse.serenity.serenitytherapycenter.entity.Therapist;

public interface TherapistDao extends CrudDao<Therapist> {
    int checkDuplicateData(int id, String name, String contact, String email, String type);
    String getNextID();
    boolean addProgram(Therapist t, Program p);
    boolean removeProgram(int t_id, int p_id);
}
