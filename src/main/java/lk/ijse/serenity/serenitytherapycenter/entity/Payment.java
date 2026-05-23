package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_id",nullable = false)
    private Registration registration;

    @Column(name = "total",nullable = false,precision = 10,scale = 2)
    private BigDecimal total;

    @Column(name = "due_amount",nullable = false,precision = 10,scale = 2)
    private BigDecimal dueAmount;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PaymentDetail> paymentDetails=new ArrayList<>();

}
