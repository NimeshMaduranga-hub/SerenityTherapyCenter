package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.QueryDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class QueryDaoImpl implements QueryDao {
    @Override
    public List<Patient> getAllAttends() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<Patient> patients = new ArrayList<>();

        String hql = "SELECT p FROM Patient p " +
                "JOIN p.registrations r " +
                "JOIN r.session s " +
                "JOIN s.program prog " +
                "GROUP BY p " +
                "HAVING COUNT(DISTINCT prog.id) = (SELECT COUNT(pr) FROM Program pr)";

        try{
            transaction = session.beginTransaction();
            patients = session.createQuery(hql, Patient.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null){ transaction.rollback(); }
        }finally {
            session.close();
        }

        return patients;
    }
}
