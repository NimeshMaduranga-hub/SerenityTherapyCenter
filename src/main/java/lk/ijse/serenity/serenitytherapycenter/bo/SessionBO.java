package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.SessionDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.SessionTM;

import java.util.List;

public interface SessionBO extends SuperBO{

    boolean saveSession(SessionDTO s);
    boolean updateSession(SessionDTO s);
    String getNextID();
    List<String> getChosenTimes(String id, String day);
    List<SessionTM> getAllSessions();
    List<SessionTM> searchSession(String text ,int searchNum);
    SessionDTO searchSessionByID(String id);
    void printDetails(String id);
    void printAllDetails();
    List<SessionTM> getAvailableSessions();
    int getAllSessionCount();
}
