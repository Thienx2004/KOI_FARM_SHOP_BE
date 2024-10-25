package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.ConsignmentKoiCare;
import com.group2.KoiFarmShop.dto.request.ConsignmentKoiRequest;
import com.group2.KoiFarmShop.dto.request.ConsignmentRequest;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.Consignment;
import com.group2.KoiFarmShop.entity.KoiFish;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ConsignmentServiceImp {

    public int createConsignment(int accountId, MultipartFile koiImg,
                                    String koiImgURL,
                                    String origin,
                                    boolean gender,
                                    int age,
                                    double size,
                                    String personality,
                                    double price,
                                    String food,
                                    String health,
                                    String ph,
                                    String temperature,
                                    String water,
                                    int pureBred,
                                    int categoryId,
                                    String name,
                                    MultipartFile certImg,
                                    String certImgURL,
                                    String notes,
                                    String phoneNumber,
                                    boolean consignmentType,
                                    int duration,
                                    double serviceFee,
                                    boolean online);

    public String approveConsignment(int consignmentId) throws MessagingException;

    public String rejectConsignment(int consignmentId, String rejectionReason) throws MessagingException;

    public PaginReponse<ConsignmentResponse> getAllConsignmentForCustomer(int pageNo, int pageSize, int accountId);
    public PaginReponse<ConsignmentResponse> getAllConsignmentForStaff(int pageNo, int pageSize);

    public ConsignmentDetailResponse getConsignmentDetail(int consignmentId);

    public Consignment processPayment(int consignmentId, boolean isPay);

    public String deleteConsignment(int consignmentId);
    public ConsignmentDetailResponse updateConsignment(ConsignmentKoiRequest consignmentKoiRequest, int consignmentId, int koiId) throws IOException;
    public HealthcareResponse updateHealth( ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException;
    public HealthcareResponse addHealth( ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException;
    public PaginReponse<ConsignmentResponse> getAllConsignmentForCare(int pageNo, int pageSize);
    public KoiFishPageResponse getFishCare(int page, int pageSize);
    public KoiFishPageResponse getAllFishCareForCustomer(int pageNo, int pageSize, int accountId);
}
