package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.UserDTO;

import java.util.List;

public interface UserBO  extends SuperBO{

    boolean checkUser(UserDTO u);
    List<UserDTO> getAllUsers();
    boolean saveUser(UserDTO u);
    boolean isUserExist(String name);
    boolean isUserExist(UserDTO u);
    String getNextID();
    boolean checkUserByID(UserDTO u);
    boolean updateUser(UserDTO u);
}
