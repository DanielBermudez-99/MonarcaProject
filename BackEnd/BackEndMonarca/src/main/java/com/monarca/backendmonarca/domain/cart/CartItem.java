package com.monarca.backendmonarca.domain.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    public CartItem(DataRegisterCartItem dataRegisterCartItem, User user, Product product) {
        this.quantity = dataRegisterCartItem.quantity();
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