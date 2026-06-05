package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String duration;

    @Column(precision = 10, scale = 2)
    private BigDecimal fee;

    @ManyToMany(mappedBy = "programs")
    private Set<Therapist> therapists = new HashSet<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sessions> sessions = new HashSet<>();
}