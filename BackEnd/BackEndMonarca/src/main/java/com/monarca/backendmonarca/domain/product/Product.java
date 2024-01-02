package com.monarca.backendmonarca.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monarca.backendmonarca.domain.category.Category;
import com.monarca.backendmonarca.domain.order.Orders;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column (name = "stock")
    private Integer stock;
    @Column (name = "price")
    private Double price;
    @Column(name = "image_url")
    private String image_url;
    @Column (name = "warranty")
    private String warranty;
    @Column (name = "brand")
    private String brand;
    @Column (name = "size")
    private String size;
    @Column (name = "color")
    private String color;
    @Column (name = "type")
    private String type;
    @Column(name = "disabled")
    private boolean disabled;
    @Column(name = "locked")
    private boolean locked;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private List<Orders> orders = new ArrayList<>();

    public Product(DataRegisterProduct dataRegisterProduct){
        this.name = dataRegisterProduct.name();
        this.description = dataRegisterProduct.description();
        this.stock = dataRegisterProduct.stock();
        this.price = dataRegisterProduct.price();
        this.image_url = dataRegisterProduct.image_url();
        this.warranty = dataRegisterProduct.warranty();
        this.brand = dataRegisterProduct.brand();
        this.size = dataRegisterProduct.size();
        this.color = dataRegisterProduct.color();
        this.type = dataRegisterProduct.type();
        this.disabled = false;
        this.locked = false;
    }

    public void dataUpdate(DataUpdateProduct dataUpdateProduct){
        if (dataUpdateProduct.name() != null){
            this.name = dataUpdateProduct.name();
        }
        if (dataUpdateProduct.description() != null){
            this.description = dataUpdateProduct.description();
        }
        if (dataUpdateProduct.stock() != null){
            this.stock = dataUpdateProduct.stock();
        }
        if (dataUpdateProduct.price() != null){
            this.price = dataUpdateProduct.price();
        }
        if (dataUpdateProduct.image_url() != null){
            this.image_url = dataUpdateProduct.image_url();
        }
        if (dataUpdateProduct.warranty() != null){
            this.warranty = dataUpdateProduct.warranty();
        }
        if (dataUpdateProduct.brand() != null){
            this.brand = dataUpdateProduct.brand();
        }
        if (dataUpdateProduct.size() != null){
            this.size = dataUpdateProduct.size();
        }
        if (dataUpdateProduct.color() != null){
            this.color = dataUpdateProduct.color();
        }
        if (dataUpdateProduct.type() != null){
            this.type = dataUpdateProduct.type();
        }
    }

}
