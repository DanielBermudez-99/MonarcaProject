package com.monarca.backendmonarca.domain.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.monarca.backendmonarca.domain.order.Orders;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "quantity")
    private int quantity;
    @Column(name = "is_active")
    private Boolean isActive = true;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToMany(mappedBy = "cartItems")
    @JsonManagedReference
    private List<Orders> orders = new ArrayList<>();

    public CartItem(DataRegisterCartItem dataRegisterCartItem, User user, Product product) {
        this.quantity = dataRegisterCartItem.quantity();
        this.isActive = true;
        this.user = user;
        this.product = product;
    }

    public void dataUpdate(DataUpdateCartItem dataUpdateCartItem) {
        this.quantity = dataUpdateCartItem.quantity();
    }

    @Transient
    public BigDecimal getTotal() {
        return BigDecimal.valueOf(this.quantity).multiply(BigDecimal.valueOf(this.product.getPrice()));
    }
}