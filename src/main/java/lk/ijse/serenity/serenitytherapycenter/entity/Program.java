package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

//hibernate
@Entity
@Table(name="programs")
//lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private String duration;
    @Column(name = "fee",precision = 10,scale = 2)
    private BigDecimal fee;

    @ManyToMany(mappedBy = "programs")
    private Set<Therapist> therapist=new HashSet<>();
    @OneToMany(mappedBy = "program",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Sessions> sessions=new HashSet<>();
}
