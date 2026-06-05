package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDetailDTO;

import java.util.List;

public interface PaymentBO extends SuperBO{
    String getNextID();
    List<String> getAllPaymentID();
    PaymentDTO getPaymentDataByID(int id);
    boolean savePaymentDetail(PaymentDetailDTO pd);
    boolean updatePayment(PaymentDTO p);
    List<PaymentDTO> getAllPayments();
    List<String> getAllOngoingPaymentID();
    List<PaymentDTO> searchPayment(String text, int num);
    void print(String id);
}
