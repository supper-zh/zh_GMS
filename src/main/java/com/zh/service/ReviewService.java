package com.zh.service;

import java.util.List;

import com.zh.po.Review;
import com.zh.po.Review1;

public interface ReviewService {
	int insertReview(Review review);
	List<Review1> findByMemberId(Review1 review, String memberTId);
	List<Review1> findByReplyLeader(String replyLeader);
	List<Review1> findReviewByRLer(Review1 review, String replyLeader);
	int updateReview(Review review);
	List<Review1> findBySId(String sId);
}
