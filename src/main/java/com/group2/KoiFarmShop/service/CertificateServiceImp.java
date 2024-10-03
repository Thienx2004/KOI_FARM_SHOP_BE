package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.CertificationReponse;
import com.group2.KoiFarmShop.dto.response.KoiFishReponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;

public interface CertificateServiceImp {

    public PaginReponse<KoiFishReponse> getAllKoiFishReponse(int pageNo, int pageSize, String accountID);
    public CertificationReponse getCertificationReponse(int koiId);
}
