package lk.ijse.serenity.serenitytherapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "contact",nullable = false)
    private String contact;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Registration> registrations=new HashSet<>();
}
