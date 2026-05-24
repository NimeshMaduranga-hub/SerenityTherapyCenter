package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.UserDao;
import lk.ijse.serenity.serenitytherapycenter.dto.UserDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean update(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            User u = session.get(User.class, user.getId());
            u.setName(user.getName());
            u.setPassword(user.getPassword());
            u.setPosition(user.getPosition());
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<User> search(String text) {
        return List.of();
    }

    @Override
    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<User> users = new ArrayList<>();

        try{
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return  users;
    }

    @Override
    public User getUserByName(UserDTO u) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        User user = null;
        String hql = "FROM User WHERE name ='" + u.getUserName() +"' AND position ='" + u.getUserPosition() +"'";

        try{
            transaction = session.beginTransaction();
            List<User> list = session.createQuery(hql, User.class).list();
            if(!list.isEmpty()){
                user = list.getFirst();
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return user;
    }

    @Override
    public boolean isUserExist(String name) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String hql = "FROM User WHERE name = '" + name + "'";
        try{
            transaction = session.beginTransaction();
            List<User> list = session.createQuery(hql, User.class).list();
            transaction.commit();

            return !(list.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean isUserExist(User u) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String hql = "FROM User WHERE name = '" + u.getName() + "' AND id != " + u.getId();
        try{
            transaction = session.beginTransaction();
            List<User> list = session.createQuery(hql, User.class).list();
            transaction.commit();

            return !(list.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback(); }
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public String getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        String nextID = "U_";

        try{
            transaction = session.beginTransaction();
            User user = session.createQuery("FROM User ORDER BY id DESC", User.class).setMaxResults(1).getSingleResult();

            nextID += (user.getId() + 1);
            transaction.commit();
        }
        catch(Exception e){
            nextID += 1;
            if(transaction != null){ transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return nextID;
    }

    @Override
    public User checkUserByID(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        User user = null;

        try{
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            if(transaction != null){ transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return user;
    }
}
