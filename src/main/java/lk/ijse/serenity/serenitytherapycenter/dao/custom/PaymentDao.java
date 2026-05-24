package lk.ijse.serenity.serenitytherapycenter.dao.custom;

import lk.ijse.serenity.serenitytherapycenter.dao.CrudDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Payment;
import lk.ijse.serenity.serenitytherapycenter.entity.PaymentDetail;

import java.util.List;

public interface PaymentDao extends CrudDao<Payment> {
    int getNextID();
    Payment getPaymentByID(int id);
    boolean savePaymentDetail(PaymentDetail pd);
    List<Payment> search(String text, int num);
}
