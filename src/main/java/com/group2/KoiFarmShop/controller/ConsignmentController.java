package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.service.ConsignmentService;
import com.group2.KoiFarmShop.service.ConsignmentServiceImp;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/consignment")
public class ConsignmentController {

    @Autowired
    private ConsignmentServiceImp consignmentService;

    @PostMapping("/createConsignment")
    public ApiReponse<String> createConsignment(@RequestParam int accountId, @RequestParam MultipartFile koiImg,
                                                @RequestParam String origin,
                                                @RequestParam boolean gender,
                                                @RequestParam int age,
                                                @RequestParam double size,
                                                @RequestParam String personality,
                                                @RequestParam double price,
                                                @RequestParam int categoryId,
                                                @RequestParam String name,
                                                @RequestParam MultipartFile certImg,
                                                @RequestParam String notes,
                                                @RequestParam boolean consignmentType,
                                                @RequestParam boolean online
                                                ){

        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.createConsignment(accountId, koiImg, origin, gender, age, size, personality, price, categoryId, name, certImg ,notes, consignmentType, online));

        return apiReponse;
    }

    @PutMapping("/approve/{consignmentID}")
    public ApiReponse<String> approveConsignment(@PathVariable int consignmentID){

        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.approveConsignment(consignmentID));

        return apiReponse;
    }

    @PutMapping("/reject/{consignmentID}")
    public ApiReponse<String> rejectConsignment(@PathVariable int consignmentID, @RequestParam String rejectionReason){
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.rejectConsignment(consignmentID, rejectionReason));

        return apiReponse;
    }
}
