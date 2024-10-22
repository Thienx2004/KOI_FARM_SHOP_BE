package com.group2.KoiFarmShop.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {

    private int blogId;
    private String title;
    private String subTitle;
    private String content;
    private String blogImg;
    private Date postDate;
    private int accountId;
    private String accountName;
    private Boolean status;
}
