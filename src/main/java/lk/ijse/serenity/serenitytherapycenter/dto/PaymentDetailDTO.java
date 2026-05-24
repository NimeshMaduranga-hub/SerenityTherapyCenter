package lk.ijse.serenity.serenitytherapycenter.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDetailDTO {
    private String id;
    private PaymentDTO payment;
    private BigDecimal amount;
    private String payDate;
    private String payType;
}
