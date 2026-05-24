package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;


import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.PaymentDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Payment;
import lk.ijse.serenity.serenitytherapycenter.entity.PaymentDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public boolean save(Payment entity) {
        return false;
    }

    @Override
    public boolean update(Payment payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            Payment oldPayment = session.get(Payment.class, payment.getId());
            oldPayment.setDueAmount(payment.getDueAmount());
            oldPayment.setStatus(payment.getStatus());

            payment.getPaymentDetails().getFirst().setPayment(oldPayment);

            oldPayment.getPaymentDetails().add(payment.getPaymentDetails().getFirst());

            session.merge(oldPayment);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if(transaction != null) { transaction.rollback();}
            e.printStackTrace();
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
    public List<Payment> search(String text) {
        return List.of();
    }

    @Override
    public List<Payment> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<Payment> payments = new ArrayList<>();

        try{
            transaction = session.beginTransaction();
            payments = session.createQuery("FROM Payment", Payment.class).list();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) { transaction.rollback();}
        }
        finally {
            session.close();
        }

        return payments;
    }

    @Override
    public int getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Payment> paymentList = session.createQuery("FROM Payment ORDER BY id DESC", Payment.class).list();
        int lastId ;
        if(paymentList.isEmpty()){
            lastId = 1;
        }
        else{
            lastId = paymentList.getFirst().getId()+1;
        }
        session.close();
        return (lastId);
    }

    @Override
    public Payment getPaymentByID(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Payment payment = null;

        try{
            transaction = session.beginTransaction();
            payment = session.get(Payment.class, id);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) { transaction.rollback();}
        }
        finally {
            session.close();
        }

        return payment;
    }

    @Override
    public boolean savePaymentDetail(PaymentDetail pd) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.save(pd);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if(transaction != null) { transaction.rollback();}
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<Payment> search(String text, int num) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<Payment> payments = new ArrayList<>();
        String hql ;
        try{
            transaction = session.beginTransaction();
            if(num == 0){
                int id = Integer.parseInt(text.substring(4));
                hql = "FROM Payment WHERE id = " + id;
            }
            else{
                hql = "FROM Payment WHERE registration.patient.name LIKE '" + text + "%'";
            }
            payments = session.createQuery(hql, Payment.class).list();
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
        finally {
            session.close();
        }

        return payments;
    }
}

