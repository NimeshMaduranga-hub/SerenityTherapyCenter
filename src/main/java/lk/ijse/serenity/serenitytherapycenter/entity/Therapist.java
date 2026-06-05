package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "therapist")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Therapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "therapist_program",
            joinColumns = @JoinColumn(name = "therapist_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private Set<Program> programs = new HashSet<>();

    @OneToMany(mappedBy = "therapist",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Sessions> sessions = new HashSet<>();



    public void addProgram(Program program) {
        this.programs.add(program);
        program.getTherapists().add(this);
    }

    public void removeProgram(Program program) {
        this.programs.remove(program);
        program.getTherapists().remove(this);
    }
}