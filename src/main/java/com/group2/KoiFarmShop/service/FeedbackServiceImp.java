package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.entity.Feedback;

import java.util.List;

public interface FeedbackServiceImp {
    public void addFeedback(Feedback feedback);
    public List<Feedback> getFeedback();
    public Feedback getFeedbackById(int id);
    public void updateFeedback(Feedback feedback);
    public void deleteFeedback(int id);

}
