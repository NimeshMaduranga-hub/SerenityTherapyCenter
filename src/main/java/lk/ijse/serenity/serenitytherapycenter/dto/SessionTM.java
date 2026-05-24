package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SessionTM {
    private String id;
    private String program;
    private String therapist;
    private String day;
    private String time;
    private String status;
}