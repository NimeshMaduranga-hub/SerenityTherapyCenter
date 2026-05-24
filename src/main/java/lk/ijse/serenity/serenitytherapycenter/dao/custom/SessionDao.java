package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Sessions;

import java.util.List;

public interface SessionDao extends CrudDao<Sessions> {
    String getNextID();
    List<Sessions> getChosenTimes(int id, String day);
    List<Sessions> search(String text ,int searchNum);
    Sessions getSessionByID(int id);
}
