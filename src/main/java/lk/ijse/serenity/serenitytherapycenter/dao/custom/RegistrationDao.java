package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Registration;

import java.util.List;

public interface RegistrationDao extends CrudDao<Registration> {
    int getNextID();
    Registration getRegistrationByID(int id);
    List<Registration> search(String text, int num);
}
