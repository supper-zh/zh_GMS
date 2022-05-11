package com.zh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.ReviewMapper;
import com.zh.po.Review;
import com.zh.po.Review1;
import com.zh.service.ReviewService;
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public int insertReview(Review review) {
		return reviewMapper.insert(review);
	}

	@Override
	public List<Review1> findByMemberId(Review1 review,String memberTId) {
		List<Review1> list = null;
		if(review!=null) {
			if(review.getsName() !=null&&!"".equals(review.getsName())) {
				list = reviewMapper.findBysNameAndMemberId(review.getsName(),memberTId);
				return list;
			}
		}
		return reviewMapper.findByMemberId(memberTId);
	}

	@Override
	public int updateReview(Review review) {
		return reviewMapper.update(review);
	}

	@Override
	public List<Review1> findByReplyLeader(String replyLeader) {
		return reviewMapper.findByReplyLeader(replyLeader);
	}

	@Override
	public List<Review1> findReviewByRLer(Review1 review,String replyLeader) {
		List<Review1> list = null;
		if(review!=null) {
			if(review.getsName() !=null&&!"".equals(review.getsName())) {
				list = reviewMapper.findReviewByRLer1(review.getsName(),replyLeader);
				return list;
			}
		}
		return reviewMapper.findReviewByRLer(replyLeader);
	}

	@Override
	public List<Review1> findBySId(String sId) {
		return reviewMapper.findBySId(sId);
	}

}
