package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

// lombok ////////////////
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String userName;
    private String userID;
    private String userPosition;
    private String userPassword;

}
