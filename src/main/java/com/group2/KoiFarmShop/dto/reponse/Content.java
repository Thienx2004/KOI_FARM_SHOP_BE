package com.group2.KoiFarmShop.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group2.KoiFarmShop.entity.Role;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {
    private String email;
    private String fullName;
    private String role;
    private String phone;
    private String accessToken;

}
