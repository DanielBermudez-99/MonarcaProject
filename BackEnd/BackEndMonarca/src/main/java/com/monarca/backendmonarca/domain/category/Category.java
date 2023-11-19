package com.monarca.backendmonarca.domain.category;
import jakarta.persistence.*;
import lombok.*;


@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public Category ( DataRegisterCategory dataRegisterCategory){
        this.name = dataRegisterCategory.name();
    }
}

