package com.monarca.backendmonarca.domain.product;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column (name = "image")
    @Lob
    private  byte[] image;
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

    public Product(DataRegisterProduct dataRegisterProduct){
        this.name = dataRegisterProduct.name();
        this.description = dataRegisterProduct.description();
        this.stock = dataRegisterProduct.stock();
        this.price = dataRegisterProduct.price();
        this.image = dataRegisterProduct.image();
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
        if (dataUpdateProduct.image() != null){
            this.image = dataUpdateProduct.image();
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
