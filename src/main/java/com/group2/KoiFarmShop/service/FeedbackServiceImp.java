package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.response.FeedbackResponse;
import com.group2.KoiFarmShop.entity.Feedback;

import java.util.List;

public interface FeedbackServiceImp {
    public FeedbackResponse addFeedback(Feedback feedback);
    public List<FeedbackResponse> getFeedback();
    public FeedbackResponse getFeedbackById(int id);
    public FeedbackResponse updateFeedback(Feedback feedback,int id);
    public FeedbackResponse deleteFeedback(int id);

}
