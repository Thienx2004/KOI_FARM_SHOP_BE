package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
