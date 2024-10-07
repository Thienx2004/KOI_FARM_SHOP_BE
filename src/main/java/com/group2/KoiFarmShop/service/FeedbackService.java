package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.FeedbackResponse;
import com.group2.KoiFarmShop.entity.Feedback;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements FeedbackServiceImp {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Override
    public FeedbackResponse addFeedback(Feedback feedback) {
        Feedback feedbackSaved = feedbackRepository.save(feedback);
        return FeedbackResponse.builder()
                .feedbackId(feedbackSaved.getFeedbackID())
                .account(feedbackSaved.getAccount())
                .rating(feedbackSaved.getRating())
                .comment(feedbackSaved.getComment())
                .build();
    }

    @Override
    public List<FeedbackResponse> getFeedback() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setFeedbackId(feedback.getFeedbackID());
            feedbackResponse.setAccount(feedback.getAccount());
            feedbackResponse.setRating(feedback.getRating());
            feedbackResponse.setComment(feedback.getComment());
            feedbackResponseList.add(feedbackResponse);
        }

        return feedbackResponseList;
    }

    @Override
    public FeedbackResponse getFeedbackById(int id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (!feedback.isPresent()) {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        return FeedbackResponse.builder()
                .feedbackId(feedback.get().getFeedbackID())
                .comment(feedback.get().getComment())
                .rating(feedback.get().getRating())
                .account(feedback.get().getAccount())
                .build();
    }

    @Override
    public FeedbackResponse updateFeedback(Feedback feedback,int id) {
            Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
            Feedback feedbackToUpdate = (Feedback) feedbackOptional.get();
            feedbackToUpdate.setFeedbackID(id);
            Feedback updatedFeedback = feedbackRepository.save(feedbackToUpdate);
        return FeedbackResponse.builder()
                .feedbackId(updatedFeedback.getFeedbackID())
                .comment(updatedFeedback.getComment())
                .rating(updatedFeedback.getRating())
                .account(updatedFeedback.getAccount())
                .build();
    }

    @Override
    public FeedbackResponse deleteFeedback(int id) {
        return null;
    }
}
