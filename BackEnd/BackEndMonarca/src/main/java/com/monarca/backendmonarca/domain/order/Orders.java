package com.monarca.backendmonarca.domain.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monarca.backendmonarca.domain.product.Product;
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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_purchase")
    private LocalDate date_purchase;
    @Column(name = "date_payment")
    private LocalDate date_payment;
    @Column(name = "date_delivery")
    private LocalDate date_delivery;
    @Column(name = "total_price")
    private Double total_price;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment_method;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Orders(DataRegisterOrder dataRegisterOrder) {
        this.date_purchase = dataRegisterOrder.date_purchase();
        this.date_payment = dataRegisterOrder.date_payment();
        this.date_delivery = dataRegisterOrder.date_delivery();
        this.total_price = dataRegisterOrder.total_price();
        this.status = dataRegisterOrder.status();
        this.payment_method = dataRegisterOrder.payment_method();
    }

    public void dataUpdate(DataUpdateOrder dataUpdateOrder) {
        if (dataUpdateOrder.date_purchase() != null) {
            this.date_purchase = dataUpdateOrder.date_purchase();
        }
        if (dataUpdateOrder.date_payment() != null) {
            this.date_payment = dataUpdateOrder.date_payment();
        }
        if (dataUpdateOrder.date_delivery() != null) {
            this.date_delivery = dataUpdateOrder.date_delivery();
        }
        if (dataUpdateOrder.total_price() != null) {
            this.total_price = dataUpdateOrder.total_price();
        }
        if (dataUpdateOrder.status() != null) {
            this.status = dataUpdateOrder.status();
        }
        if (dataUpdateOrder.payment_method() != null) {
            this.payment_method = dataUpdateOrder.payment_method();
        }
    }
}
