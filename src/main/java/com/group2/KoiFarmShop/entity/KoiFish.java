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
@Table(name = "KoiFish")
public class KoiFish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int koiID;

    private String origin;
    private String gender;
    private int age;
    private double size;
    private String personality;
    private String situation;
    private double price;
    private String koiImage;
    private int status;
    /*
    1_Còn Hàng
    2_Đâ bán
    3_Ký gửi
    */


    @OneToOne(mappedBy = "koiFish",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Certificate certificate;

    @OneToMany(mappedBy = "koiFish")
    private Set<Consignment> consignment;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @OneToMany(mappedBy = "koiFish")
    private Set<OrderDetail> orderDetail;


}

