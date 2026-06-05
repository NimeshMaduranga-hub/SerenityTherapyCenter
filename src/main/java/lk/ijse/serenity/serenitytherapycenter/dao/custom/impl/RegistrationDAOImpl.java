package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.RegistrationDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDao {
    @Override
    public boolean save(Registration registration) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(registration);
            transaction.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) { transaction.rollback();}
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean update(Registration entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Registration> search(String text) {
        return List.of();
    }

    @Override
    public List<Registration> getAll() {
        List<Registration> registrations = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            registrations = session.createQuery("FROM Registration", Registration.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if(transaction != null){ transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return registrations;
    }

    @Override
    public int getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Registration> registrationList = session.createQuery("FROM Registration ORDER BY id DESC", Registration.class).list();
        int lastId ;
        if(registrationList.isEmpty()){
            lastId = 1;
        }
        else{
            lastId = registrationList.getFirst().getId()+1;
        }
        session.close();
        return (lastId);
    }

    @Override
    public Registration getRegistrationByID(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Registration registration = null;
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            registration = session.get(Registration.class, id);
            transaction.commit();
        }
        catch (Exception e) {
            if(transaction != null) { transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return registration;
    }

    @Override
    public List<Registration> search(String text, int num) {
        List<Registration> registrations = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String hql ;
        try{
            transaction = session.beginTransaction();
            if(num == 0){
                int id = Integer.parseInt(text.substring(2));
                hql = "FROM Registration WHERE id = " + id;
            }
            else{
                hql = "FROM Registration WHERE patient.name LIKE '" + text + "%'";
            }
            registrations = session.createQuery(hql, Registration.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if(transaction != null){ transaction.rollback(); }
        }
        finally {
            session.close();
        }

        return registrations;
    }

}
