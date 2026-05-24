package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TherapistDTO {
    private String id;
    private String name;
    private String contact;
    private String email;
    private HashSet<ProgramDTO> programs;
}
