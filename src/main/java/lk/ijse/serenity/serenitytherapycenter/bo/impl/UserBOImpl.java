package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.UserBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.UserDao;
import lk.ijse.serenity.serenitytherapycenter.dto.UserDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import static lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory.DAOTypes.USER;

public class UserBOImpl implements UserBO {

    private final UserDao userDao = (UserDao) DAOFactory.getInstance().getDao(USER);

    @Override
    public boolean checkUser(UserDTO u) {
        User user = userDao.getUserByName(u);
        if(user == null){
            return false;
        }
        else{
            return BCrypt.checkpw(u.getUserPassword(), user.getPassword());
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDao.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User u : users){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserID("U_" + u.getId());
            userDTO.setUserName(u.getName());
            userDTO.setUserPosition(u.getPosition());

            userDTOList.add(userDTO);
        }

        return userDTOList;

    }

    @Override
    public boolean saveUser(UserDTO u) {

        String hashedPassword = BCrypt.hashpw(u.getUserPassword(), BCrypt.gensalt(12));

        User user = new User();
        user.setName(u.getUserName());
        user.setPosition(u.getUserPosition());
        user.setPassword(hashedPassword);

        return userDao.save(user);
    }

    @Override
    public boolean isUserExist(String name) {
        return userDao.isUserExist(name);
    }

    @Override
    public boolean isUserExist(UserDTO u) {
        User user = new User();
        user.setId(Integer.parseInt(u.getUserID().substring(2)));
        user.setName(u.getUserName());
        return userDao.isUserExist(user);
    }

    @Override
    public String getNextID() {
        return userDao.getNextID();
    }

    @Override
    public boolean checkUserByID(UserDTO u) {
        int id = (Integer.parseInt(u.getUserID().substring(2)));
        User user = userDao.checkUserByID(id);
        if(user == null){
            return false;
        }
        else {
            return BCrypt.checkpw(u.getUserPassword(), user.getPassword());
        }
    }

    @Override
    public boolean updateUser(UserDTO u) {

        String hashedPassword = BCrypt.hashpw(u.getUserPassword(), BCrypt.gensalt(12));

        User user = new User();
        user.setId(Integer.parseInt(u.getUserID().substring(2)));
        user.setName(u.getUserName());
        user.setPosition(u.getUserPosition());
        user.setPassword(hashedPassword);

        return userDao.update(user);

    }
}
