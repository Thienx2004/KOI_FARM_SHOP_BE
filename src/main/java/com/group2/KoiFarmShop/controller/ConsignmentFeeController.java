package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.request.ConsignmentFeeDTO;
import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.service.ConsignmentFeeService;
import com.group2.KoiFarmShop.service.ConsignmentFeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consignmentFee")
public class ConsignmentFeeController {

    @Autowired
    private ConsignmentFeeServiceImp consignmentFeeService;

    @PostMapping("/create")
    public ApiReponse<ConsignmentFeeDTO> createConsignmentFee(@org.springframework.web.bind.annotation.RequestBody ConsignmentFeeDTO consignmentFeeDTO) {
        ApiReponse<ConsignmentFeeDTO> response = new ApiReponse<>();
        response.setData(consignmentFeeService.addConsignmentFee(consignmentFeeDTO));
        return response;
    }

    @PutMapping("/update")
    public ApiReponse<ConsignmentFeeDTO> updateConsignmentFeeDTOApiReponse(@RequestBody ConsignmentFeeDTO consignmentFeeDTO) {
        ApiReponse<ConsignmentFeeDTO> response = new ApiReponse<>();
        response.setData(consignmentFeeService.updateConsignmentFee(consignmentFeeDTO));
        return response;
    }

    @GetMapping("/getAll/{pageNo}/{pageSize}")
    public ApiReponse<PaginReponse<ConsignmentFeeDTO>> getAllConsignmentFees(@PathVariable int pageNo, @PathVariable int pageSize) {
        ApiReponse<PaginReponse<ConsignmentFeeDTO>> response = new ApiReponse<>();
        response.setData(consignmentFeeService.getAllConsignmentFees(pageNo, pageSize));
        return response;
    }

    @GetMapping("/getDetail/{id}")
    public ApiReponse<ConsignmentFeeDTO> getConsignmentFee(@PathVariable int id) {
        ApiReponse<ConsignmentFeeDTO> response = new ApiReponse<>();
        response.setData(consignmentFeeService.getConsignmentFee(id));
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ApiReponse<String> deleteConsignmentFee(@PathVariable int id) {
        ApiReponse<String> response = new ApiReponse<>();
        response.setData(consignmentFeeService.deleteConsignmentFee(id));
        return response;
    }

    @PutMapping("/changeStatus/{id}")
    public ApiReponse<String> changeStatus(@PathVariable int id, boolean status) {
        ApiReponse<String> response = new ApiReponse<>();
        response.setData(consignmentFeeService.changeStatus(id, status));
        return response;
    }

}
