package com.monarca.backendmonarca.domain.user;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "location")
    private String location;
    @Column(name = "complement")
    private String complement;
    @Column(name = "address")
    private String address;
    @Column (name = "career")
    private String career;
    @Column (name = "street")
    private String street;
    @Column (name = "number")
    private String number;
    @Column(name = "date_register")
    private LocalDate date_register;
    @Column(name = "role_user")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User ( DataRegisterUser dataRegisterUser){
        this.name = dataRegisterUser.name();
        this.lastname = dataRegisterUser.lastname();
        this.username = dataRegisterUser.username();
        this.password = dataRegisterUser.password();
        this.email = dataRegisterUser.email();
        this.phone = dataRegisterUser.phone();
        this.location = dataRegisterUser.location();
        this.complement = dataRegisterUser.complement();
        this.address = dataRegisterUser.address();
        this.career = dataRegisterUser.career();
        this.street = dataRegisterUser.street();
        this.number = dataRegisterUser.number();
        this.date_register = LocalDate.now();
        this.role = Role.USER;
    }

    public void dataUpdate(DataUpdateUser dataUpdateUser){

        if (dataUpdateUser.name() != null) {
            this.name = dataUpdateUser.name();
        }
        if (dataUpdateUser .lastName() != null) {
            this.lastname = dataUpdateUser.lastName();
        }
        if (dataUpdateUser.username() != null) {
            this.username = dataUpdateUser.username();
        }
        if (dataUpdateUser.password() != null) {
            this.password = dataUpdateUser.password();
        }
        if (dataUpdateUser.email() != null) {
            this.email = dataUpdateUser.email();
        }
        if (dataUpdateUser.phone() != null) {
            this.phone = dataUpdateUser.phone();
        }
        if (dataUpdateUser.location() != null) {
            this.location = dataUpdateUser.location();
        }
        if (dataUpdateUser.complement() != null) {
            this.complement = dataUpdateUser.complement();
        }
        if (dataUpdateUser.address() != null) {
            this.address = dataUpdateUser.address();
        }
        if (dataUpdateUser.career() != null) {
            this.career = dataUpdateUser.career();
        }
        if (dataUpdateUser.street() != null) {
            this.street = dataUpdateUser.street();
        }
        if (dataUpdateUser.number() != null) {
            this.number = dataUpdateUser.number();
        }
    }
}
