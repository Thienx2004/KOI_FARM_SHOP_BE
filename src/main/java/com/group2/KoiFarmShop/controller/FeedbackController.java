package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.response.FeedbackResponse;
import com.group2.KoiFarmShop.entity.Feedback;
import com.group2.KoiFarmShop.service.FeedbackService;
import com.group2.KoiFarmShop.service.FeedbackServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackServiceImp feedbackServiceImp;

    // Lấy tất cả feedbacks
    @GetMapping
    public List<FeedbackResponse> getAllFeedbacks() {
        return null;
    }

    // Lấy feedback theo ID
    @GetMapping("/{id}")
    public FeedbackResponse getFeedbackById(@PathVariable int id) {

        return null;
    }

    // Tạo mới feedback
    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return null;
    }

    // Cập nhật feedback
    @PutMapping("/{id}")
    public FeedbackResponse updateFeedback(@PathVariable int id, @RequestBody Feedback feedbackDetails) {
        return null;
    }

    // Xóa feedback
    @DeleteMapping("/{id}")
    public FeedbackResponse deleteFeedback(@PathVariable int id) {
        return null;
    }
}
