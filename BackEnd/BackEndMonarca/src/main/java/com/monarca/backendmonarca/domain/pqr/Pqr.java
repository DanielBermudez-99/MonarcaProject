package com.monarca.backendmonarca.domain.pqr;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.monarca.backendmonarca.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pqr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String descripcion;
    @Column(name = "date_register")
    private LocalDate date_register;
    @Column(name = "date_solution")
    private LocalDate date_solution;
    @Column(name = "deadline")
    private LocalDate deadline;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public Pqr(DataRegisterPqr dataRegisterPqr) {
        this.name = dataRegisterPqr.name();
        this.descripcion = dataRegisterPqr.descripcion();
        this.date_register = dataRegisterPqr.date_register();
        this.date_solution = dataRegisterPqr.date_solution();
        this.deadline = dataRegisterPqr.deadline();
        this.status = dataRegisterPqr.status();
        this.priority = dataRegisterPqr.priority();
    }

    public void dataUpdate(DataUpdatePqr dataUpdatePqr) {
        if (dataUpdatePqr.name() != null) {
            this.name = dataUpdatePqr.name();
        }
        if (dataUpdatePqr.descripcion() != null) {
            this.descripcion = dataUpdatePqr.descripcion();
        }
        if (dataUpdatePqr.date_register() != null) {
            this.date_register = dataUpdatePqr.date_register();
        }
        if (dataUpdatePqr.date_solution() != null) {
            this.date_solution = dataUpdatePqr.date_solution();
        }
        if (dataUpdatePqr.deadline() != null) {
            this.deadline = dataUpdatePqr.deadline();
        }
        if (dataUpdatePqr.status() != null) {
            this.status = dataUpdatePqr.status();
        }
        if (dataUpdatePqr.priority() != null) {
            this.priority = dataUpdatePqr.priority();
        }
    }
}