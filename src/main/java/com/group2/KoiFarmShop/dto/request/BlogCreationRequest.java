package com.group2.KoiFarmShop.dto.request;

import com.google.firebase.internal.Nullable;
import com.group2.KoiFarmShop.entity.Account;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCreationRequest {

    private String title;
    private String subTitle;
    private String content;

    @Nullable
    private MultipartFile blogImg;

    private int accountId;
    private Boolean status;

}
