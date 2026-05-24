package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SessionDTO {
    private String id;
    private RegistrationDTO registration;
    private TherapistDTO therapist;
    private String therapistID;
    private String therapistName;
    private String therapistContact;
    private String therapistEmail;
    private ProgramDTO program;
    private String programID;
    private String programName;
    private String programDuration;
    private String programFee;
    private String day;
    private String time;
    private String status;
    private PaymentDTO payment;
}
