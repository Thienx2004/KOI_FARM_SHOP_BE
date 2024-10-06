package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.entity.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackService implements FeedbackServiceImp {
    @Override
    public void addFeedback(Feedback feedback) {

    }

    @Override
    public List<Feedback> getFeedback() {
        return List.of();
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return null;
    }

    @Override
    public void updateFeedback(Feedback feedback) {

    }

    @Override
    public void deleteFeedback(int id) {

    }
}
