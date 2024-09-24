package com.group2.KoiFarmShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Set<KoiFish> koiFish;

    @OneToMany(mappedBy = "category")
    private Set<Batch> batches;

    private String description;
    private String categoryImage;
    private boolean status=true;

}
