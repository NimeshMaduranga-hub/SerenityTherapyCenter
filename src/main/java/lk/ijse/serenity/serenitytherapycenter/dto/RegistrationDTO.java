package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegistrationDTO {
    private String id;
    private PatientDTO patient;
    private String registerDate;
    private SessionDTO session;
    private String sessionID;
    private String patientName;
    private String patientID;
    private TherapistDTO therapist;
    private String therapistName;
    private ProgramDTO program;
    private String programName;
}
