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
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column(precision = 10, scale = 2)
    private BigDecimal dueAmount;

    private String status;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentDetail> paymentDetails = new ArrayList<>();
}