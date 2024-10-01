package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.entity.Orders;
import com.group2.KoiFarmShop.entity.Payment;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.PaymentRepository;
import com.group2.KoiFarmShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ApiReponse<String> saveOrder(@RequestBody OrderRequest orderRequest, @RequestParam String transactionCode) {
        ApiReponse<String> resp = new ApiReponse<>();
        Payment payment = paymentRepository.findPaymentByTransactionCode(transactionCode).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_FAILED));
        if(!payment.isStatus()) {
            Orders success = orderService.addOrder(orderRequest);
            if(success == null){
                throw new AppException(ErrorCode.PAYMENT_FAILED);
            }
            payment.setOrder(success);
            payment.setStatus(true);
            paymentRepository.save(payment);
            resp.setData("Lưu thanh toán thành công");
            return resp;
        }
        else {
            resp.setStatusCode(601);
            resp.setData("Lưu hoá đơn thất bại");
            resp.setMessage("Xử lí thất bại!");
            return resp;
        }
    }

    @GetMapping("/getOrderHistory")
    public ApiReponse<PaginReponse<OrderHistoryReponse>> getAllOrdersWithDetails(
            @RequestParam(required = false) String accountID,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "3") int pageSize) {

        PaginReponse<OrderHistoryReponse> historyReponsePaginReponse = orderService.getOrdersHistory(pageNo, pageSize, accountID, type);
        ApiReponse<PaginReponse<OrderHistoryReponse>> historyReponse = new ApiReponse<>();
        historyReponse.setData(historyReponsePaginReponse);

        return historyReponse;
    }

}
