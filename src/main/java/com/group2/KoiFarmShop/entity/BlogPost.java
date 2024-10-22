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
@Table(name = "BlogPost")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogPostID;

    private String title;
    private String subTitle;

    private String content;

    private String blogImg;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    private Boolean status;     //true = public, false = private

}
