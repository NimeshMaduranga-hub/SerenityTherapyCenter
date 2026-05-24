package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDTO {
    private String id;
    private SessionDTO session;
    private String sessionID;
    private String programName;
    private PatientDTO patient;
    private String patientID;
    private String patientName;
    private String patientContact;
    private String patientAddress;
    private RegistrationDTO registration;
    private String registrationID;
    private BigDecimal total;
    private BigDecimal dueAmount;
    private String status;
    private List<PaymentDetailDTO> paymentDetails = new ArrayList<>();
}