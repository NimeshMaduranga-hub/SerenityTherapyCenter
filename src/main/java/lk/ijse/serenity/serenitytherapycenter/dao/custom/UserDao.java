package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.dto.UserDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.User;

public interface UserDao extends CrudDao<User> {
    User getUserByName(UserDTO user);
    boolean isUserExist(String name);
    boolean isUserExist(User u);
    String getNextID();
    User checkUserByID(int id);
}
