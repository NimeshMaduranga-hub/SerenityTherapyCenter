package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.dao.custom.PaymentDetailDao;
import lk.ijse.serenity.serenitytherapycenter.entity.PaymentDetail;

import java.util.List;

public class PaymentDetailDaoImpl implements PaymentDetailDao {
    @Override
    public boolean save(PaymentDetail paymentDetail) {
        return false;
    }

    @Override
    public boolean update(PaymentDetail entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<PaymentDetail> search(String text) {
        return List.of();
    }

    @Override
    public List<PaymentDetail> getAll() {
        return List.of();
    }
}
