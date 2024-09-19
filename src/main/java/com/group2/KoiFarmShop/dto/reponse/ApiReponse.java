package com.group2.KoiFarmShop.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group2.KoiFarmShop.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiReponse <T> {
    private int statusCode = 200;
    private String message = "Xử lý thành công!";
    private T data;
    private Date dateTime = new Date();
    private String email;
    private String fullName;
    private Role role;
    private String accessToken;
}
