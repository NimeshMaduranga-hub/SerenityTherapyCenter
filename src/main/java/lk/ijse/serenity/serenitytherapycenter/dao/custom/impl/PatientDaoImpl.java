package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.PatientDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {
    @Override
    public boolean save(Patient patient) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(Patient patient) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Patient oldPatient = session.get(Patient.class, patient.getId());

            oldPatient.setName(patient.getName());
            oldPatient.setGender(patient.getGender());
            oldPatient.setContact(patient.getContact());
            oldPatient.setAddress(patient.getAddress());

            //session.update(String.valueOf(id), customer);
            transaction.commit();
            return true;
        }catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally{
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Patient patient = session.get(Patient.class, id);
            session.delete(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }finally{
            session.close();
        }
    }

    @Override
    public List<Patient> search(String text) {
        List<Patient> patients= new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql;
        if(text.startsWith("P_")){
            String p_id = text.substring(2);
            hql = "FROM Patient WHERE id= " + p_id;
            patients = session.createQuery(hql, Patient.class).list();
        }
        else {
            hql = "FROM Patient WHERE name LIKE :name";
            patients = session.createQuery(hql, Patient.class).setParameter("name", text+"%").list();
        }
        session.close();
        return patients;
    }

    @Override
    public List<Patient> getAll() {

        Session session =
                FactoryConfiguration.getInstance().getSession();

        List<Patient> patients =
                session.createQuery("FROM Patient", Patient.class)
                        .getResultList();

        System.out.println(patients.size());

        session.close();

        return patients;
    }

    @Override
    public int checkDuplicateData(int p_id, String p_name, String p_contact, String type) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Patient> patientQuery = session.createQuery("from Patient where name='" + p_name + "'", Patient.class).list();

        if(patientQuery.stream().findAny().isPresent()){
            if((patientQuery.size() == 1) && type.equals("u")){
                if(!(patientQuery.getFirst().getId() == p_id)){
                    session.close();
                    return 0;
                }
            }else{
                session.close();
                return 0;
            }
        }

        patientQuery = session.createQuery("from Patient where contact='" + p_contact + "'", Patient.class).list();
        if(patientQuery.stream().findAny().isPresent()){
            if((patientQuery.size() == 1) && type.equals("u")){
                if(!(patientQuery.getFirst().getId() == p_id)){
                    session.close();
                    return 1;
                }
            }else{
                session.close();
                return 1;
            }
        }
        session.close();
        return -1;
    }

    @Override
    public String getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Patient> patientQuery = session.createQuery("FROM Patient ORDER BY id DESC LIMIT 1", Patient.class).list();
        int lastID ;
        if(patientQuery.isEmpty()){
            lastID = 1;
        }
        else{
            lastID = patientQuery.getFirst().getId() + 1;
        }
        session.close();
        return ("P_" + lastID);
    }
}
