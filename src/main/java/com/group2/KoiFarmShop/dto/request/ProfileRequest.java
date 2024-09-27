package com.group2.KoiFarmShop.dto.request;

import com.group2.KoiFarmShop.entity.Account;
import lombok.Data;

@Data
public class ProfileRequest  {

    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;

}
