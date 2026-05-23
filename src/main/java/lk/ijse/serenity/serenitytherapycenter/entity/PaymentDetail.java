package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_detail")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id",nullable = false)
    private Payment payment;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "pay_date")
    private String payType;


}
