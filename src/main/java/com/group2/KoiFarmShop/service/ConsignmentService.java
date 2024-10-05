package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.CertificateRequest;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.*;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.CertificateRepository;
import com.group2.KoiFarmShop.repository.ConsignmentRepository;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConsignmentService implements ConsignmentServiceImp{

    @Autowired
    private ConsignmentRepository consignmentRepository;
    @Autowired
    private KoiFishRepository koiFishRepository;
    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public String createConsignment(int accountId, MultipartFile koiImg, String origin, boolean gender, int age, double size, String personality, double price, int categoryId, String name, MultipartFile certImg,String notes,
                                    String phoneNumber,
                                    boolean consignmentType,
                                    boolean online) {
        try {
            Consignment consignment = new Consignment();
            Account account = new Account();
            account.setAccountID(accountId);

            consignment.setAccount(account);

            Category category = new Category();
            category.setCategoryID(categoryId);

            KoiFish koiFish = new KoiFish();
            koiFish.setOrigin(origin);
            koiFish.setGender(gender);
            koiFish.setAge(age);
            koiFish.setSize(size);
            koiFish.setPersonality(personality);
            koiFish.setPrice(price);
            koiFish.setKoiImage(firebaseService.uploadImage(koiImg));
            koiFish.setCategory(category);
            koiFish.setStatus(4);  // set status pending dang cho dc duyet

            Certificate certificate = new Certificate();
            certificate.setName(name);
            certificate.setImage(firebaseService.uploadImage(certImg));
            certificate.setCreatedDate(new Date());

            koiFish.setCertificate(certificate);
            certificate.setKoiFish(koiFish);

            koiFishRepository.save(koiFish);
            certificateRepository.save(certificate);

            consignment.setKoiFish(koiFish);
            consignment.setAgreedPrice(price);
            consignment.setConsignmentType(consignmentType);
            consignment.setOnline(online);
            consignment.setNotes(notes);
            consignment.setPhoneNumber(phoneNumber);
            consignment.setStatus(1);
            consignment.setConsignmentDate(new Date());
            consignmentRepository.save(consignment);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SAVE_FAILED);
        }

        return "Nộp đơn kí gửi thành công";
    }

    @Override
    public String approveConsignment(int consignmentId) {

        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        if(consignment.getStatus() == 1){

            KoiFish koiFish = koiFishRepository.findByKoiID(consignment.getKoiFish().getKoiID());
            koiFish.setStatus(3); //set cho cá này sang status đã ký gửi
            koiFishRepository.save(koiFish);

            consignment.setStatus(2);//set cho đơn này đã đc duyệt cho ky gui
            consignmentRepository.save(consignment);

            return "Đơn đã được duyệt!";
        } else {
            throw new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND);
        }


    }

    @Override
    public String rejectConsignment(int consignmentId, String rejectionReason) {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        if(consignment.getStatus() == 1){

            KoiFish koiFish = koiFishRepository.findByKoiID(consignment.getKoiFish().getKoiID());

            consignment.setStatus(3); //status don bi tu choi
            consignment.setNotes(rejectionReason);
            consignment.setKoiFish(null);
            consignmentRepository.save(consignment);

            if(koiFish != null){

                Certificate certificate = koiFish.getCertificate();
                if(certificate != null){
                    certificateRepository.delete(certificate);
                }

                koiFishRepository.delete(koiFish);
            }

            return "Đã từ chốt đơn ký gửi!";
        } else {
            throw new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND);
        }

    }

    @Override
    public PaginReponse<ConsignmentResponse> getAllConsignmentForCustomer(int pageNo, int pageSize, int accountId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentDate").descending());
        Page<Consignment> consignmentPage = consignmentRepository.findConsignmentsByAccount_AccountID(accountId, pageable);
        List<ConsignmentResponse> consignmentResponses = new ArrayList<>();
        for(Consignment consignment : consignmentPage.getContent()){
            ConsignmentResponse consignmentResponse = new ConsignmentResponse();
            consignmentResponse.setConsignmentID(consignment.getConsignmentID());
            consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
            consignmentResponse.setConsignmentType(consignment.isConsignmentType());
            consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
            consignmentResponse.setNotes(consignment.getNotes());
            consignmentResponse.setEmail(consignment.getAccount().getEmail());
            consignmentResponse.setFullname(consignment.getAccount().getFullName());
            consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
            consignmentResponse.setStatus(consignment.getStatus());
            consignmentResponse.setOnline(consignment.isOnline());
            consignmentResponses.add(consignmentResponse);
        }
        PaginReponse<ConsignmentResponse> consignmentResponsePaginReponse = new PaginReponse<>();
        consignmentResponsePaginReponse.setContent(consignmentResponses);
        consignmentResponsePaginReponse.setPageSize(pageSize);
        consignmentResponsePaginReponse.setPageNum(pageNo);
        consignmentResponsePaginReponse.setTotalElements(consignmentPage.getContent().size());
        consignmentResponsePaginReponse.setTotalPages(consignmentPage.getTotalPages());

        return consignmentResponsePaginReponse;
    }

    @Override
    public PaginReponse<ConsignmentResponse> getAllConsignmentForStaff(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentDate").descending());
        Page<Consignment> consignmentPage = consignmentRepository.findAll(pageable);
        List<ConsignmentResponse> consignmentResponses = new ArrayList<>();
        for(Consignment consignment : consignmentPage.getContent()){
            ConsignmentResponse consignmentResponse = new ConsignmentResponse();
            consignmentResponse.setConsignmentID(consignment.getConsignmentID());
            consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
            consignmentResponse.setConsignmentType(consignment.isConsignmentType());
            consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
            consignmentResponse.setNotes(consignment.getNotes());
            consignmentResponse.setEmail(consignment.getAccount().getEmail());
            consignmentResponse.setFullname(consignment.getAccount().getFullName());
            consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
            consignmentResponse.setStatus(consignment.getStatus());
            consignmentResponse.setOnline(consignment.isOnline());
            consignmentResponses.add(consignmentResponse);
        }
        PaginReponse<ConsignmentResponse> consignmentResponsePaginReponse = new PaginReponse<>();
        consignmentResponsePaginReponse.setContent(consignmentResponses);
        consignmentResponsePaginReponse.setPageSize(pageSize);
        consignmentResponsePaginReponse.setPageNum(pageNo);
        consignmentResponsePaginReponse.setTotalElements(consignmentPage.getContent().size());
        consignmentResponsePaginReponse.setTotalPages(consignmentPage.getTotalPages());

        return consignmentResponsePaginReponse;
    }

    @Override
    public ConsignmentDetailResponse getConsignmentDetail(int consignmentId) {
        Consignment consignment = consignmentRepository.findConsignmentByConsignmentID(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        ConsignmentDetailResponse detailResponse = new ConsignmentDetailResponse();
        detailResponse.setConsignmentID(consignment.getConsignmentID());
        detailResponse.setConsignmentDate(consignment.getConsignmentDate());
        detailResponse.setConsignmentType(consignment.isConsignmentType());
        detailResponse.setAgreedPrice(consignment.getAgreedPrice());
        detailResponse.setNotes(consignment.getNotes());
        detailResponse.setEmail(consignment.getAccount().getEmail());
        detailResponse.setFullname(consignment.getAccount().getFullName());
        detailResponse.setPhoneNumber(consignment.getPhoneNumber());
        detailResponse.setStatus(consignment.getStatus());
        detailResponse.setOnline(consignment.isOnline());

        KoiFish koiFish = consignment.getKoiFish();
        if (koiFish != null) {
            KoiFishReponse koiFishResponse = new KoiFishReponse();
            koiFishResponse.setId(koiFish.getKoiID());
            koiFishResponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishResponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishResponse.setOrigin(koiFish.getOrigin());
            koiFishResponse.setGender(koiFish.isGender());
            koiFishResponse.setAge(koiFish.getAge());
            koiFishResponse.setSize(koiFish.getSize());
            koiFishResponse.setPersonality(koiFish.getPersonality());
            koiFishResponse.setPrice(koiFish.getPrice());
            koiFishResponse.setKoiImage(koiFish.getKoiImage());
            koiFishResponse.setStatus(koiFish.getStatus());

            Certificate certificate = koiFish.getCertificate();
            if (certificate != null) {
                CertificateRequest certificateResponse = new CertificateRequest();
                certificateResponse.setName(certificate.getName());
                certificateResponse.setImage(certificate.getImage());
                certificateResponse.setCreatedDate(certificate.getCreatedDate());
                koiFishResponse.setCertificate(certificateResponse);
            }

            detailResponse.setKoiFish(koiFishResponse);
        }

        return detailResponse;
    }
}


