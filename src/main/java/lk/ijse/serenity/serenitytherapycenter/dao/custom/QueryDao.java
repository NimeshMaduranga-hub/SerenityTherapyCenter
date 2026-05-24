package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.SuperDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Patient;

import java.util.List;

public interface QueryDao extends SuperDao {
    List<Patient> getAllAttends();

}
