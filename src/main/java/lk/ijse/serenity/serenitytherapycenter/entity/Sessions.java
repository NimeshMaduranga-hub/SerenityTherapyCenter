package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "session",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Registration> registration = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(name = "session_day")
    private String day;

    @Column(name = "session_time")
    private String time;

    @Column(name = "status")
    private String status;

}
