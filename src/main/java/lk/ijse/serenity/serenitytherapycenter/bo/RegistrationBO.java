package lk.ijse.serenity.serenitytherapycenter.bo;

import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDetailDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {

    boolean saveRegistration(RegistrationDTO r, PaymentDTO p, PaymentDetailDTO pd);
    String getNextId();
    List<RegistrationDTO> getAllRegistrations();
    RegistrationDTO getRegistrationByID(int id);
    List<RegistrationDTO> searchRegistration(String text, int num);
}
