package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.ConsignmentFeeDTO;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.ConsignmentFee;

public interface ConsignmentFeeServiceImp {

    ConsignmentFeeDTO getConsignmentFee(int id);
    ConsignmentFeeDTO addConsignmentFee(ConsignmentFeeDTO consignmentFeeDTO);
    ConsignmentFeeDTO updateConsignmentFee(ConsignmentFeeDTO consignmentFeeDTO);
    String deleteConsignmentFee(int id);
    String changeStatus(int id, boolean status);
    PaginReponse<ConsignmentFeeDTO> getAllConsignmentFees(int pageNo, int pageSize);
}
