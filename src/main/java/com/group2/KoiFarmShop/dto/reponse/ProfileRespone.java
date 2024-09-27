package com.group2.KoiFarmShop.dto.reponse;

import com.group2.KoiFarmShop.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRespone {
    private int id;
    private String fullname;
    private String password;
    private String email;
    private String phone;
    private String address;
    private boolean isVerified=true;
}
