package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.CertificateRequest;
import com.group2.KoiFarmShop.dto.KoiFishSpecification;
import com.group2.KoiFarmShop.dto.request.ConsignmentKoiCare;
import com.group2.KoiFarmShop.dto.request.ConsignmentKoiRequest;
import com.group2.KoiFarmShop.dto.request.ConsignmentRequest;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.*;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.*;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ConsignmentService implements ConsignmentServiceImp {

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
    @Autowired
    private EmailService emailService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private KoiFishService koiFishService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private HealthcareRepository healthcareRepository;


    @Override
    public int createConsignment(int accountId, MultipartFile koiImg, String koiImgURL, String origin, boolean gender, int age, double size, String personality, double price, String food,
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
            koiFish.setFood(food);
            koiFish.setHealth(health);
            koiFish.setPH(ph);
            koiFish.setTemperature(temperature);
            koiFish.setWater(water);
            koiFish.setPurebred(pureBred);
            if (koiImg != null && !koiImg.isEmpty()) {
                koiFish.setKoiImage(firebaseService.uploadImage(koiImg));
            } else {
                koiFish.setKoiImage(koiImgURL);
            }
            koiFish.setCategory(category);
            koiFish.setStatus(4);  // set status pending dang cho dc duyet

            Certificate certificate = new Certificate();
            certificate.setName(name);
            if (certImg != null && !certImg.isEmpty()) {
                certificate.setImage(firebaseService.uploadImage(certImg));
            } else {
                certificate.setImage(certImgURL);
            }
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
            consignment.setDuration(duration);
            consignment.setServiceFee(serviceFee);
            consignmentRepository.save(consignment);
            return consignment.getConsignmentID();
        } catch (Exception e) {
            throw new AppException(ErrorCode.SAVE_FAILED);
        }
    }

    @Override
    public String approveConsignment(int consignmentId) throws MessagingException {

        Consignment consignment = consignmentRepository.findConsignmentByConsignmentID(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        if (consignment.getStatus() == 1) { // Nếu đơn đang ở trạng thái Pending
            // Cập nhật trạng thái chờ thanh toán
            consignment.setStatus(4); // Pending Payment
            consignment.setStartDate(new Date());

            // Tính ngày hết hạn thanh toán (3 ngày sau khi phê duyệt)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            consignment.setEndDate(calendar.getTime());

            consignmentRepository.save(consignment);

            // Gửi mail cho customer thông báo về đơn đã phê duyệt và hướng dẫn thanh toán
            emailService.sendEmailApproveToCustomer(consignment.getAccount().getEmail(), consignment.getConsignmentID());

            return "Đơn ký gửi đã được phê duyệt và chờ thanh toán!";
        } else {
            throw new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND);
        }


    }

    @Override
    public String rejectConsignment(int consignmentId, String rejectionReason) throws MessagingException {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        if (consignment.getStatus() == 1 || consignment.getStatus() == 2) {

            //KoiFish koiFish = koiFishRepository.findByKoiID(consignment.getKoiFish().getKoiID());

            consignment.setStatus(3); //status don bi tu choi
            consignment.setNotes(rejectionReason);
            //consignment.setKoiFish(null);
            consignmentRepository.save(consignment);

//            if (koiFish != null) {
//
//                Certificate certificate = koiFish.getCertificate();
//                if (certificate != null) {
//                    certificateRepository.delete(certificate);
//                }
//
//                koiFishRepository.delete(koiFish);
//            }
            emailService.sendEmailRejectToCustomer(consignment.getAccount().getEmail(), consignmentId, rejectionReason);
            return "Đã từ chốt đơn ký gửi!";
        } else {
            throw new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND);
        }
    }

    @Override
    public PaginReponse<ConsignmentResponse> getAllConsignmentForCustomer(int pageNo, int pageSize, int accountId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentDate", "consignmentID").descending());
        Page<Consignment> consignmentPage = consignmentRepository.findConsignmentsByAccount_AccountID(accountId, pageable);
        List<ConsignmentResponse> consignmentResponses = new ArrayList<>();
        for (Consignment consignment : consignmentPage.getContent()) {
            ConsignmentResponse consignmentResponse = new ConsignmentResponse();
            consignmentResponse.setConsignmentID(consignment.getConsignmentID());
            consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
            consignmentResponse.setConsignmentType(consignment.isConsignmentType());
            consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
            consignmentResponse.setNotes(consignment.getNotes());
            consignmentResponse.setEmail(consignment.getAccount().getEmail());
            consignmentResponse.setFullname(consignment.getAccount().getFullName());
            consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
            consignmentResponse.setDuration(consignment.getDuration());
            consignmentResponse.setServiceFee(consignment.getServiceFee());
            consignmentResponse.setStartDate(consignment.getStartDate());
            consignmentResponse.setEndDate(consignment.getEndDate());
            consignmentResponse.setStatus(consignment.getStatus());
            consignmentResponse.setOnline(consignment.isOnline());
            consignmentResponses.add(consignmentResponse);
        }
        PaginReponse<ConsignmentResponse> consignmentResponsePaginReponse = new PaginReponse<>();
        consignmentResponsePaginReponse.setContent(consignmentResponses);
        consignmentResponsePaginReponse.setPageSize(pageSize);
        consignmentResponsePaginReponse.setPageNum(pageNo);
        consignmentResponsePaginReponse.setTotalElements(consignmentPage.getTotalElements());
        consignmentResponsePaginReponse.setTotalPages(consignmentPage.getTotalPages());

        return consignmentResponsePaginReponse;
    }

    @Override
    public PaginReponse<ConsignmentResponse> getAllConsignmentForStaff(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentDate", "consignmentID").descending());
        Page<Consignment> consignmentPage = consignmentRepository.findAll(pageable);
        List<ConsignmentResponse> consignmentResponses = new ArrayList<>();
        for (Consignment consignment : consignmentPage.getContent()) {
            ConsignmentResponse consignmentResponse = new ConsignmentResponse();
            consignmentResponse.setConsignmentID(consignment.getConsignmentID());
            consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
            consignmentResponse.setConsignmentType(consignment.isConsignmentType());
            consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
            consignmentResponse.setNotes(consignment.getNotes());
            consignmentResponse.setEmail(consignment.getAccount().getEmail());
            consignmentResponse.setFullname(consignment.getAccount().getFullName());
            consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
            consignmentResponse.setDuration(consignment.getDuration());
            consignmentResponse.setServiceFee(consignment.getServiceFee());
            consignmentResponse.setStartDate(consignment.getStartDate());
            consignmentResponse.setEndDate(consignment.getEndDate());
            consignmentResponse.setStatus(consignment.getStatus());
            consignmentResponse.setOnline(consignment.isOnline());
            consignmentResponses.add(consignmentResponse);
        }
        PaginReponse<ConsignmentResponse> consignmentResponsePaginReponse = new PaginReponse<>();
        consignmentResponsePaginReponse.setContent(consignmentResponses);
        consignmentResponsePaginReponse.setPageSize(pageSize);
        consignmentResponsePaginReponse.setPageNum(pageNo);
        consignmentResponsePaginReponse.setTotalElements(consignmentPage.getTotalElements());
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
        detailResponse.setDuration(consignment.getDuration());
        detailResponse.setServiceFee(consignment.getServiceFee());
        detailResponse.setStartDate(consignment.getStartDate());
        detailResponse.setEndDate(consignment.getEndDate());
        detailResponse.setStatus(consignment.getStatus());
        detailResponse.setOnline(consignment.isOnline());

        KoiFish koiFish = consignment.getKoiFish();
        if (koiFish != null) {
            KoiFishDetailReponse koiFishResponse = new KoiFishDetailReponse();
            koiFishResponse.setId(koiFish.getKoiID());
            koiFishResponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishResponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishResponse.setOrigin(koiFish.getOrigin());
            koiFishResponse.setGender(koiFish.isGender());
            koiFishResponse.setAge(koiFish.getAge());
            koiFishResponse.setSize(koiFish.getSize());
            koiFishResponse.setPersonality(koiFish.getPersonality());
            koiFishResponse.setPrice(koiFish.getPrice());
            koiFishResponse.setFood(koiFish.getFood());
            koiFishResponse.setHealth(koiFish.getHealth());
            koiFishResponse.setPH(koiFish.getPH());
            koiFishResponse.setTemperature(koiFish.getTemperature());
            koiFishResponse.setWater(koiFish.getWater());
            koiFishResponse.setPurebred(koiFish.getPurebred());
            koiFishResponse.setKoiImage(koiFish.getKoiImage());
            koiFishResponse.setStatus(koiFish.getStatus());

            Certificate certificate = koiFish.getCertificate();
            if (certificate != null) {
                CertificateResponse certificateResponse = new CertificateResponse();
                certificateResponse.setName(certificate.getName());
                certificateResponse.setImage(certificate.getImage());
                certificateResponse.setCreatedDate(certificate.getCreatedDate());
                koiFishResponse.setCertificate(certificateResponse);
            }

            detailResponse.setKoiFish(koiFishResponse);
            if (!consignment.isConsignmentType() && koiFish.getStatus() == 5) {
                Optional<Healthcare> healthcare = healthcareRepository.findById(koiFish.getKoiID());
                if (healthcare.isPresent()) {
                    HealthcareResponse healthcareResponse = new HealthcareResponse();
                    healthcareResponse.setHealthStatus(healthcare.get().getHealthStatus());
                    healthcareResponse.setGrowthStatus(healthcare.get().getGrowthStatus());
                    healthcareResponse.setCareEnvironment(healthcare.get().getCareEnvironment());
                    healthcareResponse.setNote(healthcare.get().getNote());
                    healthcareResponse.setChecked(healthcare.get().isChecked());
                    healthcareResponse.setDate(healthcare.get().getCreatedDate());
                    LocalDate currentDate = LocalDate.now();
                    LocalDate futureDate = LocalDate.of(healthcare.get().getConsignmentDate().getYear(), healthcare.get().getConsignmentDate().getMonth(), healthcare.get().getConsignmentDate().getDay());
                    healthcareResponse.setDayRemain(ChronoUnit.DAYS.between(currentDate, futureDate));
                    detailResponse.setHealthcare(healthcareResponse);
                }
            }
        }

        return detailResponse;
    }

    @Override
    public ConsignmentResponse processPayment(int consignmentId, boolean isPaid) {
        Consignment consignment = consignmentRepository.findConsignmentByConsignmentID(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        ConsignmentResponse consignmentResponse = new ConsignmentResponse();

        // Kiểm tra nếu đơn ký gửi đang ở trạng thái chờ thanh toán
        if (consignment.getStatus() == 4) { // Pending Payment
            // Trước tiên, kiểm tra thời hạn thanh toán
            if (isPaymentOverdue(consignment)) {
                consignment.setStatus(5); // Đơn quá hạn
                consignmentRepository.save(consignment);
                throw new AppException(ErrorCode.CONSIGNMENT_OUT_OF_DATE);
            } // Nếu đơn đang ở trạng thái Pending Payment
            if (isPaid) {
                consignment.setStatus(2); // Đơn đã được thanh toán, chờ bán
                KoiFish koiFish = koiFishRepository.findByKoiID(consignment.getKoiFish().getKoiID());
                if (koiFish != null) {
                    if (consignment.isConsignmentType())
                        koiFish.setStatus(3);// Đặt trạng thái bán cho cá
                    else koiFish.setStatus(5);// Đặt trạng thái chăm sóc cho cá
                } else throw new AppException(ErrorCode.KOINOTFOUND);

                consignmentRepository.save(consignment);
                //return consignmentResponse;
            }
        }
        consignmentResponse.setConsignmentID(consignment.getConsignmentID());
        consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
        consignmentResponse.setConsignmentType(consignment.isConsignmentType());
        consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
        consignmentResponse.setNotes(consignment.getNotes());
        consignmentResponse.setEmail(consignment.getAccount().getEmail());
        consignmentResponse.setFullname(consignment.getAccount().getFullName());
        consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
        consignmentResponse.setDuration(consignment.getDuration());
        consignmentResponse.setServiceFee(consignment.getServiceFee());
        consignmentResponse.setStartDate(consignment.getStartDate());
        consignmentResponse.setEndDate(consignment.getEndDate());
        consignmentResponse.setStatus(consignment.getStatus());
        consignmentResponse.setOnline(consignment.isOnline());

        return consignmentResponse;
    }

    public boolean isPaymentOverdue(Consignment consignment) {
        // Kiểm tra nếu ngày hiện tại sau ngày kết thúc thanh toán
        return new Date().after(consignment.getEndDate());
    }

    @Override
    public String deleteConsignment(int consignmentId) {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));

        if (consignment != null) {

            KoiFish koiFish = koiFishRepository.findByKoiID(consignment.getKoiFish().getKoiID());
            consignment.setKoiFish(null);
            consignmentRepository.save(consignment);

            if (koiFish != null) {

                Certificate certificate = koiFish.getCertificate();
                if (certificate != null) {
                    certificateRepository.delete(certificate);
                }

                koiFishRepository.delete(koiFish);
            }

            Payment payment = consignment.getPayment();
            if (payment != null) {
                paymentRepository.delete(payment);
            }
            consignmentRepository.delete(consignment);

            return "Đã xoá thành công đơn ký gửi!";
        } else {
            throw new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND);
        }
    }

    @Override
    public ConsignmentDetailResponse updateConsignment(ConsignmentKoiRequest consignmentKoiRequest, int consignmentId, int koiId) throws IOException {
        if (consignmentKoiRequest.getConsignmentRequest().getStatus() != 1) {
            throw new AppException(ErrorCode.CANNOTUPDATE);
        }
        //Consignment
        Consignment consignmentToUpdated = new Consignment();
        Optional<Account> account = accountRepository.findById(consignmentKoiRequest.getConsignmentRequest().getAccountid());
        consignmentToUpdated.setConsignmentID(consignmentId);
        consignmentToUpdated.setConsignmentDate(consignmentKoiRequest.getConsignmentRequest().getConsignmentDate());
        consignmentToUpdated.setConsignmentType(consignmentKoiRequest.getConsignmentRequest().isConsignmentType());
        consignmentToUpdated.setAccount(account.get());
        consignmentToUpdated.setStatus(consignmentKoiRequest.getConsignmentRequest().getStatus());
        consignmentToUpdated.setOnline(consignmentKoiRequest.getConsignmentRequest().isOnline());
        consignmentToUpdated.setNotes(consignmentKoiRequest.getConsignmentRequest().getNotes());
        consignmentToUpdated.setDuration(consignmentKoiRequest.getConsignmentRequest().getDuration());
        consignmentToUpdated.setKoiFish(koiFishRepository.findByKoiID(koiFishService.updateKoiFish(koiId, consignmentKoiRequest.getKoiRequest()).getId()));
        consignmentToUpdated.setAgreedPrice(consignmentKoiRequest.getConsignmentRequest().getAgreedPrice());
        consignmentToUpdated.setEndDate(consignmentKoiRequest.getConsignmentRequest().getEndDate());
        consignmentToUpdated.setStartDate(consignmentKoiRequest.getConsignmentRequest().getStartDate());
        consignmentToUpdated.setServiceFee(consignmentKoiRequest.getConsignmentRequest().getServiceFee());
        Consignment consignmentUpdated = consignmentRepository.save(consignmentToUpdated);
        return ConsignmentDetailResponse.builder()
                .consignmentID(consignmentUpdated.getConsignmentID())
                .consignmentDate(consignmentUpdated.getConsignmentDate())
                .consignmentType(consignmentUpdated.isConsignmentType())
                .email(consignmentUpdated.getAccount().getEmail())
                .agreedPrice(consignmentUpdated.getAgreedPrice())
                .notes(consignmentUpdated.getNotes())
                .duration(consignmentUpdated.getDuration())
                .endDate(consignmentUpdated.getEndDate())
                .startDate(consignmentUpdated.getStartDate())
                .serviceFee(consignmentUpdated.getServiceFee())
                .fullname(consignmentUpdated.getAccount().getFullName())
                .koiFish(koiFishService.getKoiFishById(consignmentUpdated.getKoiFish().getKoiID()))
                .online(consignmentUpdated.isOnline())
                .phoneNumber(consignmentUpdated.getPhoneNumber())
                .notes(consignmentUpdated.getNotes())
                .build();
    }

    @Override
    public HealthcareResponse updateHealth(ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException {
        Consignment consignment = consignmentRepository.findConsignmentByKoiFish_KoiID(consignmentKoiCare.getKoiCareId()).get();
        Optional<KoiFish> koiFish = koiFishRepository.findById(consignmentKoiCare.getKoiCareId());
        Optional<Healthcare> healthcareToCheck = healthcareRepository.findLatestHealthcareByKoiFish(koiFish.get());
        Healthcare healthcare = new Healthcare();
        healthcare.setKoiFish(consignment.getKoiFish());
        healthcare.setHealthStatus(consignmentKoiCare.getHealthStatus());
        healthcare.setGrowthStatus(consignmentKoiCare.getGrowthStatus());
        healthcare.setCareEnvironment(consignmentKoiCare.getCareEnvironment());
        healthcare.setNote(consignmentKoiCare.getNote());
        healthcare.setChecked(true);
        healthcare.setConsignmentDate(healthcareToCheck.get().getConsignmentDate());
        long differenceInMillis = Math.abs(new Date().getTime() - healthcareToCheck.get().getConsignmentDate().getTime());
        long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
        Healthcare healthcare1 = healthcareRepository.save(healthcare);
        emailService.sendEmailForCareFish(consignment.getAccount().getEmail(), consignment.getConsignmentID(), consignmentKoiCare);
        KoiFishDetailReponse koiFishDetailReponse = koiFishService.updateKoiCare(consignment.getKoiFish().getKoiID(), consignmentKoiCare);
        return HealthcareResponse.builder()
                .healthStatus(healthcare1.getHealthStatus())
                .careEnvironment(healthcare1.getCareEnvironment())
                .growthStatus(healthcare1.getGrowthStatus())
                .note(healthcare1.getNote())
                .dayRemain(differenceInDays)
                .checked(healthcare1.isChecked())
                .date(healthcare1.getCreatedDate())
                .build();
    }

    @Override
    public HealthcareResponse addHealth(ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException {
        Consignment consignment = consignmentRepository.findConsignmentByKoiFish_KoiID(consignmentKoiCare.getKoiCareId()).get();
        Healthcare healthcare = new Healthcare();
        healthcare.setKoiFish(consignment.getKoiFish());
        healthcare.setHealthStatus(consignmentKoiCare.getHealthStatus());
        healthcare.setGrowthStatus(consignmentKoiCare.getGrowthStatus());
        healthcare.setCareEnvironment(consignmentKoiCare.getCareEnvironment());
        healthcare.setNote(consignmentKoiCare.getNote());
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, consignment.getDuration());
        healthcare.setConsignmentDate(calendar.getTime());
        healthcare.setChecked(true);
        Healthcare healthcare1 = healthcareRepository.save(healthcare);
        emailService.sendEmailForCareFish(consignment.getAccount().getEmail(), consignment.getConsignmentID(), consignmentKoiCare);
        long differenceInMillis = Math.abs(new Date().getTime() - healthcare1.getConsignmentDate().getTime());
        long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
        KoiFishDetailReponse koiFishDetailReponse = koiFishService.updateKoiCare(consignment.getKoiFish().getKoiID(), consignmentKoiCare);
        return HealthcareResponse.builder()
                .healthStatus(healthcare1.getHealthStatus())
                .careEnvironment(healthcare1.getCareEnvironment())
                .growthStatus(healthcare1.getGrowthStatus())
                .note(healthcare1.getNote())
                .dayRemain(differenceInDays)
                .checked(healthcare1.isChecked())
                .date(healthcare1.getCreatedDate())
                .build();
    }

    @Override
    public PaginReponse<ConsignmentResponse> getAllConsignmentForCare(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("consignmentDate").descending());
        Specification<Consignment> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            predicates.add(criteriaBuilder.equal(root.get("consignmentType"), false));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Consignment> consignmentPage = consignmentRepository.findAll(spec, pageable);
        List<ConsignmentResponse> consignmentResponses = new ArrayList<>();
        for (Consignment consignment : consignmentPage.getContent()) {
            ConsignmentResponse consignmentResponse = new ConsignmentResponse();
            consignmentResponse.setConsignmentID(consignment.getConsignmentID());
            consignmentResponse.setConsignmentDate(consignment.getConsignmentDate());
            consignmentResponse.setConsignmentType(consignment.isConsignmentType());
            consignmentResponse.setAgreedPrice(consignment.getAgreedPrice());
            consignmentResponse.setNotes(consignment.getNotes());
            consignmentResponse.setEmail(consignment.getAccount().getEmail());
            consignmentResponse.setFullname(consignment.getAccount().getFullName());
            consignmentResponse.setPhoneNumber(consignment.getPhoneNumber());
            consignmentResponse.setDuration(consignment.getDuration());
            consignmentResponse.setServiceFee(consignment.getServiceFee());
            consignmentResponse.setStartDate(consignment.getStartDate());
            consignmentResponse.setEndDate(consignment.getEndDate());
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
    public KoiFishPageResponse getFishCare(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Specification<KoiFish> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), 5);

        Page<KoiFish> koiFishPage = koiFishRepository.findAll(spec, pageable);

        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishPage.getContent()) {
            KoiFishDetailReponse koiFishReponse = new KoiFishDetailReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.isGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishReponse.setFood(koiFish.getFood());
            koiFishReponse.setHealth(koiFish.getHealth());
            koiFishReponse.setPH(koiFish.getPH());
            koiFishReponse.setTemperature(koiFish.getTemperature());
            koiFishReponse.setWater(koiFish.getWater());
            koiFishReponse.setPurebred(koiFish.getPurebred());
            koiFishReponse.setStatus(koiFish.getStatus());

            Optional<Healthcare> healthcareToCheck = healthcareRepository.findLatestHealthcareByKoiFish(koiFish);

            HealthcareResponse healthcareResponse = new HealthcareResponse();
            if (healthcareToCheck.isPresent()) {
                Healthcare healthcare = healthcareToCheck.get();
                healthcareResponse.setCareEnvironment(healthcare.getCareEnvironment());
                healthcareResponse.setHealthStatus(healthcare.getHealthStatus());
                healthcareResponse.setGrowthStatus(healthcare.getGrowthStatus());
                healthcareResponse.setNote(healthcare.getNote());
                healthcareResponse.setChecked(healthcare.isChecked());
                healthcareResponse.setDate(healthcare.getCreatedDate());
                long differenceInMillis = Math.abs(new Date().getTime() - healthcare.getConsignmentDate().getTime());
                long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
                healthcareResponse.setDayRemain(differenceInDays);
            }
            koiFishReponse.setHealthcare(healthcareResponse);
            koiFishReponseList.add(koiFishReponse);
        }

        return KoiFishPageResponse.builder()
                .pageNum(koiFishPage.getNumber() + 1)
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getTotalElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public KoiFishPageResponse getAllFishCareForCustomer(int pageNo, int pageSize, int accountId) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        Page<KoiFish> koiList=consignmentRepository.findKoiFishByAccountIdAndStatus(accountId,pageable);
        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiList) {
                KoiFishDetailReponse koiFishReponse = new KoiFishDetailReponse();
                koiFishReponse.setId(koiFish.getKoiID());
                koiFishReponse.setOrigin(koiFish.getOrigin());
                koiFishReponse.setAge(koiFish.getAge());
                koiFishReponse.setSize(koiFish.getSize());
                koiFishReponse.setGender(koiFish.isGender());
                koiFishReponse.setPersonality(koiFish.getPersonality());
                koiFishReponse.setPrice(koiFish.getPrice());
                koiFishReponse.setKoiImage(koiFish.getKoiImage());
                koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
                koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
                koiFishReponse.setFood(koiFish.getFood());
                koiFishReponse.setHealth(koiFish.getHealth());
                koiFishReponse.setPH(koiFish.getPH());
                koiFishReponse.setTemperature(koiFish.getTemperature());
                koiFishReponse.setWater(koiFish.getWater());
                koiFishReponse.setPurebred(koiFish.getPurebred());
                koiFishReponse.setStatus(koiFish.getStatus());
                Optional<Healthcare> healthcareToCheck=healthcareRepository.findById(koiFish.getKoiID());

                HealthcareResponse healthcareResponse = new HealthcareResponse();
                if(healthcareToCheck.isPresent()) {
                    Healthcare healthcare=healthcareToCheck.get();
                    healthcareResponse.setCareEnvironment(healthcare.getCareEnvironment());
                    healthcareResponse.setHealthStatus(healthcare.getHealthStatus());
                    healthcareResponse.setGrowthStatus(healthcare.getGrowthStatus());
                    healthcareResponse.setNote(healthcare.getNote());
                    healthcareResponse.setChecked(healthcare.isChecked());
                    healthcareResponse.setDate(healthcare.getCreatedDate());
                }
                koiFishReponse.setHealthcare(healthcareResponse);
                koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .koiFishReponseList(koiFishReponseList)
                .pageNum(koiList.getNumber() + 1)
                .totalPages(koiList.getTotalPages())
                .totalElements(koiList.getTotalElements())
                .pageSize(koiList.getSize())
                .build();
    }

    public FishCareDetailResponse getFishCareDetail(int koiId) {
        Optional<KoiFish> koiFish=koiFishRepository.findById(koiId);
        KoiFish koiFishFound= new KoiFish();
        if(koiFish.isPresent()) {
            koiFishFound=koiFish.get();
        }else {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        List<Healthcare> healthcareList=healthcareRepository.findAllByKoiFish(koiFish.get())
                .stream()
                .sorted(Comparator.comparing(Healthcare::getCreatedDate).reversed()) // Sắp xếp theo ngày mới nhất
                .toList();
        List<HealthcareResponse> healthcareResponseList=new ArrayList<>();


        for(Healthcare healthcare:healthcareList) {
            HealthcareResponse healthcareResponse=new HealthcareResponse();
            healthcareResponse.setCareEnvironment(healthcare.getCareEnvironment());
            healthcareResponse.setHealthStatus(healthcare.getHealthStatus());
            healthcareResponse.setGrowthStatus(healthcare.getGrowthStatus());
            healthcareResponse.setNote(healthcare.getNote());
            healthcareResponse.setChecked(healthcare.isChecked());
            healthcareResponse.setDate(healthcare.getCreatedDate());
            long differenceInMillis = Math.abs(new Date().getTime() - healthcare.getConsignmentDate().getTime());
            long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
            healthcareResponse.setDayRemain(differenceInDays);
            healthcareResponseList.add(healthcareResponse);
        }
        return FishCareDetailResponse.builder()
                .id(koiFishFound.getKoiID())
                .pH(koiFishFound.getPH())
                .food(koiFishFound.getFood())
                .gender(koiFishFound.isGender())
                .categoryId(koiFishFound.getCategory().getCategoryID())
                .koiImage(koiFishFound.getKoiImage())
                .category(koiFishFound.getCategory().getCategoryName())
                .origin(koiFishFound.getOrigin())
                .personality(koiFishFound.getPersonality())
                .price(koiFishFound.getPrice())
                .status(koiFishFound.getStatus())
                .purebred(koiFishFound.getPurebred())
                .water(koiFishFound.getWater())
                .age(koiFishFound.getAge())
                .health(koiFishFound.getHealth())
                .temperature(koiFishFound.getTemperature())
                .size(koiFishFound.getSize())
                .healthcare(healthcareResponseList)
                .age(koiFishFound.getAge())
                .build();
    }
}




