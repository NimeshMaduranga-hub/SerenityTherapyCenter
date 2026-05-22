package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id",nullable = false)
    private  Patient patient;

    @Column(name = "register_date")
    private String registerDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id",nullable = false)
    private Sessions sessions;

    @OneToMany(mappedBy = "registration",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Payment payment;



}
