package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.CertificationReponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;

public interface CertificateServiceImp {

    public PaginReponse<KoiFishReponse> getAllKoiFishReponse(int pageNo, int pageSize, String accountID);
    public CertificationReponse getCertificationReponse(int koiId);
}
