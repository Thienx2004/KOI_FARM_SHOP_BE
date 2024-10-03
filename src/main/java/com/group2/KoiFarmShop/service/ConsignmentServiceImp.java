package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.ConsignmentDetailResponse;
import com.group2.KoiFarmShop.dto.response.ConsignmentResponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.Consignment;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ConsignmentServiceImp {

    public String createConsignment(int accountId, MultipartFile koiImg,
                                    String origin,
                                    boolean gender,
                                    int age,
                                    double size,
                                    String personality,
                                    double price,
                                    int categoryId,
                                    String name,
                                    MultipartFile certImg,
                                    String notes,
                                    String phoneNumber,
                                    boolean consignmentType,
                                    boolean online);

    public String approveConsignment(int consignmentId);

    public String rejectConsignment(int consignmentId, String rejectionReason);

    public PaginReponse<ConsignmentResponse> getAllConsignmentForCustomer(int pageNo, int pageSize, int accountId);
    public PaginReponse<ConsignmentResponse> getAllConsignmentForStaff(int pageNo, int pageSize);

    public ConsignmentDetailResponse getConsignmentDetail(int consignmentId);
}
