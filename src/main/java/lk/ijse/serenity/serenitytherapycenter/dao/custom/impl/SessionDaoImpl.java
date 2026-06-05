package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.SessionDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Sessions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SessionDaoImpl  implements SessionDao {
    @Override
    public boolean save(Sessions s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.save(s);
            transaction.commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            if(transaction != null) {transaction.rollback();}
            return false;
        }
        finally {
            session.close();
        }

    }

    @Override
    public boolean update(Sessions s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            Sessions oldSession = session.get(Sessions.class, s.getId());
            oldSession.setTherapist(s.getTherapist());
            oldSession.setProgram((s.getProgram()));
            oldSession.setDay(s.getDay());
            oldSession.setTime(s.getTime());
            oldSession.setStatus(s.getStatus());

            transaction.commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            if(transaction != null) {transaction.rollback();}
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
    public List<Sessions> search(String text) {
        return List.of();
    }

    @Override
    public List<Sessions> getAll() {
        List<Sessions> sessionsList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            sessionsList = session.createQuery("FROM Sessions", Sessions.class).list();
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null) {transaction.rollback();}
            e.printStackTrace();
        }
        finally{
            session.close();
        }

        return sessionsList;
    }

    @Override
    public String getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Sessions> sessionQuery = session.createQuery("FROM Sessions ORDER BY id DESC", Sessions.class).list();
        int lastID ;
        if(sessionQuery.isEmpty()){
            lastID = 1;
        }
        else{
            lastID = sessionQuery.getFirst().getId() + 1;
        }
        session.close();
        return ("S_" + lastID);
    }

    @Override
    public List<Sessions> getChosenTimes(int id, String day) {
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "FROM Sessions WHERE therapist.id = " + id + "AND day= '" + day + "'";
        List<Sessions> times = session.createQuery(hql, Sessions.class).list();
        session.close();
        return times;
    }

    @Override
    public List<Sessions> search(String text, int searchNum) {
        List<Sessions> sessions= new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String hql ;

        if(searchNum == 0){
            hql = "FROM Sessions WHERE id =" + text.substring(2);
        }
        else if (searchNum == 1) {
            hql = "FROM Sessions WHERE therapist.name LIKE '" + text + "%'";
        }
        else{  // searchNum == 2
            hql = "FROM Sessions WHERE program.name LIKE '" + text + "%'";
        }

        try{
            transaction = session.beginTransaction();
            sessions = session.createQuery(hql, Sessions.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if(transaction != null){ transaction.rollback(); }
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return sessions;
    }

    @Override
    public Sessions getSessionByID(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Sessions s = new Sessions();
        try{
            transaction = session.beginTransaction();
            s = session.get(Sessions.class, id);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null){ transaction.rollback(); }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return s;
    }

}
