package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.CertificationReponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
import com.group2.KoiFarmShop.service.CertificateServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificate")
public class CertifitcateController {

    @Autowired
    private CertificateServiceImp certificateService;

    @GetMapping("/getAllKoi")
    @Operation(summary = "Lấy danh sách Koi account đã mua", description = "API này sẽ trả về danh sách tất cả các Koi mà user đã mua trong hệ thống.-Trương Thiên Lộc")
    public ApiReponse<PaginReponse<KoiFishReponse>> getAllKois(int pageNo, int pageSize,@RequestParam String accountId) {
        ApiReponse apiReponse = new ApiReponse();
        PaginReponse<KoiFishReponse> koiFishReponsePaginReponse = certificateService.getAllKoiFishReponse(pageNo, pageSize, accountId);
        apiReponse.setData(koiFishReponsePaginReponse);
        return apiReponse;
    }

    @GetMapping("/getCertifate")
    @Operation(summary = "Lấy certificate Koi theo koiId", description = "API này sẽ trả về certificate của koi trong hệ thống.-Trương Thiên Lộc")
    public ApiReponse getCertifate(@RequestParam int koiId) {
        ApiReponse apiReponse = new ApiReponse();
        CertificationReponse certificationReponse = certificateService.getCertificationReponse(koiId);
        apiReponse.setData(certificationReponse);
        return apiReponse;
    }
}
