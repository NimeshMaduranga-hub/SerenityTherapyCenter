package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProgramDTO {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
    private Set<TherapistDTO> therapists;
}