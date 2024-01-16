package com.monarca.backendmonarca.domain.payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monarca.backendmonarca.domain.order.Orders;
import com.monarca.backendmonarca.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "card_holder_name")
    private String cardHolderName;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @Column(name = "cvv")
    private String cvv;
    @Column(name = "balance")
    private Double balance;

    // En la entidad Payment
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Orders> orders = new ArrayList<>();

    // src/main/java/com/monarca/backendmonarca/domain/payment/Payment.java

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    //Son constructores que se usan para registrar el objeto Payment
    public Payment (DataRegisterPayment dataRegisterPayment) {
        this.cardNumber = dataRegisterPayment.cardNumber();
        this.cardHolderName = dataRegisterPayment.cardHolderName();
        this.expiryDate = dataRegisterPayment.expiryDate();
        this.cvv = dataRegisterPayment.cvv();
        this.balance = 10000.0;
    }
    //Son constructores que se usan para actualizar el objeto Payment
    public Payment (DataUpdatePayment dataUpdatePayment) {
        this.cardNumber = dataUpdatePayment.cardNumber();
        this.cardHolderName = dataUpdatePayment.cardHolderName();
        this.expiryDate = dataUpdatePayment.expiryDate();
        this.cvv = dataUpdatePayment.cvv();
        this.balance = dataUpdatePayment.balance();
    }
    public boolean validatePayment(Double amount) {
        return this.balance >= amount;
    }


    public void deductFromBalance(Double amount) {
        this.balance -= amount;
    }
}