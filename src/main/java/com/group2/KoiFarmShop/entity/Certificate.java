package com.group2.KoiFarmShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Certificate")
public class Certificate {

    @Id

    @Column(name = "koi_ID")
    private int id;

    @Column(nullable = false)
    private String name;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "koi_ID")
    private KoiFish koiFish;

}
