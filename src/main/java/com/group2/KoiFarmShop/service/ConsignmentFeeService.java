package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.ConsignmentFeeDTO;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.ConsignmentFee;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.ConsignmentFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConsignmentFeeService implements ConsignmentFeeServiceImp {

    @Autowired
    private ConsignmentFeeRepository repository;

    @Override
    public ConsignmentFeeDTO getConsignmentFee(int id) {
        ConsignmentFee consignmentFee = repository.findConsignmentFeeByConsignmentFeeId(id);
        if(consignmentFee == null) throw new AppException(ErrorCode.INVALIDNUMBER);
        ConsignmentFeeDTO consignmentFeeDTO = new ConsignmentFeeDTO();
        consignmentFeeDTO.setConsignmentFeeId(consignmentFee.getConsignmentFeeId());
        consignmentFeeDTO.setDuration(consignmentFee.getDuration());
        consignmentFeeDTO.setRate(consignmentFee.getRate());
        consignmentFeeDTO.setSale(consignmentFee.isSale());
        consignmentFeeDTO.setStatus(consignmentFee.isStatus());
        consignmentFeeDTO.setCreatedDate(consignmentFee.getCreatedDate());

        return consignmentFeeDTO;
    }

    @Override
    public ConsignmentFeeDTO addConsignmentFee(ConsignmentFeeDTO consignmentFeeDTO) {
        ConsignmentFee consignmentFee = new ConsignmentFee();
        consignmentFee.setDuration(consignmentFeeDTO.getDuration());
        consignmentFee.setRate(consignmentFeeDTO.getRate() / 100);
        consignmentFee.setCreatedDate(new Date());
        consignmentFee.setSale(consignmentFeeDTO.isSale());
        consignmentFee.setStatus(true);
        repository.save(consignmentFee);
        consignmentFeeDTO.setConsignmentFeeId(consignmentFee.getConsignmentFeeId());

        return consignmentFeeDTO;
    }

    @Override
    public ConsignmentFeeDTO updateConsignmentFee(ConsignmentFeeDTO consignmentFeeDTO) {
        ConsignmentFee consignmentFee = new ConsignmentFee();
        consignmentFee.setConsignmentFeeId(consignmentFeeDTO.getConsignmentFeeId());
        consignmentFee.setDuration(consignmentFeeDTO.getDuration());
        consignmentFee.setRate(consignmentFeeDTO.getRate() / 100);
        consignmentFee.setCreatedDate(new Date());
        consignmentFee.setStatus(consignmentFee.isStatus());
        repository.save(consignmentFee);

        return consignmentFeeDTO;
    }

    @Override
    public String deleteConsignmentFee(int id) {
        ConsignmentFee consignmentFee = repository.findConsignmentFeeByConsignmentFeeId(id);
        if(consignmentFee == null) throw new AppException(ErrorCode.INVALIDNUMBER);
        repository.delete(consignmentFee);
        return "Xoá thành công!";
    }

    @Override
    public String changeStatus(int id, boolean status) {
        ConsignmentFee consignmentFee = repository.findConsignmentFeeByConsignmentFeeId(id);
        if(consignmentFee == null) throw new AppException(ErrorCode.INVALIDNUMBER);
        consignmentFee.setStatus(consignmentFee.isStatus());
        repository.save(consignmentFee);
        return "Thay đổi status thành công!";
    }

    @Override
    public PaginReponse<ConsignmentFeeDTO> getAllConsignmentFees(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentFeeId").descending());
        Page<ConsignmentFee> consignmentFeePage = repository.findAll(pageable);
        List<ConsignmentFeeDTO> list = new ArrayList<>();
        for (ConsignmentFee consignmentFee : consignmentFeePage.getContent()) {
            ConsignmentFeeDTO consignmentFeeDTO = new ConsignmentFeeDTO();
            consignmentFeeDTO.setConsignmentFeeId(consignmentFee.getConsignmentFeeId());
            consignmentFeeDTO.setDuration(consignmentFee.getDuration());
            consignmentFeeDTO.setRate(consignmentFee.getRate());
            consignmentFeeDTO.setSale(consignmentFee.isSale());
            consignmentFeeDTO.setStatus(consignmentFee.isStatus());
            consignmentFeeDTO.setCreatedDate(consignmentFee.getCreatedDate());
            list.add(consignmentFeeDTO);
        }
        PaginReponse<ConsignmentFeeDTO> consignmentFeeDTOPaginReponse = new PaginReponse<>();
        consignmentFeeDTOPaginReponse.setContent(list);
        consignmentFeeDTOPaginReponse.setPageNum(pageNo);
        consignmentFeeDTOPaginReponse.setPageSize(pageSize);
        consignmentFeeDTOPaginReponse.setTotalElements(consignmentFeePage.getTotalElements());
        consignmentFeeDTOPaginReponse.setTotalPages(consignmentFeePage.getTotalPages());

        return consignmentFeeDTOPaginReponse;
    }
}
