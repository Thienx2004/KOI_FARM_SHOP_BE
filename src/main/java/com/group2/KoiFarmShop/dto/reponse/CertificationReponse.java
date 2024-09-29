package com.group2.KoiFarmShop.dto.reponse;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationReponse {

    private int id;
    private String name;
    private String image;

    private Date createdDate;
}
