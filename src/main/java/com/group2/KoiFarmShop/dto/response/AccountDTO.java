package com.group2.KoiFarmShop.dto.response;

import com.group2.KoiFarmShop.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private int id;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String password;
    private Role role;
    private String avatar;
    private boolean status;
}
