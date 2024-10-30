package com.group2.KoiFarmShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MailBody {
    String to;
    String subject;
    String text;
}
