package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Patient;

public interface PatientDao extends CrudDao<Patient> {

    int checkDuplicateData(int id,String name, String contact, String type);
    String getNextID();
}
