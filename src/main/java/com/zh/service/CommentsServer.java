package com.zh.service;

import com.zh.po.Comments;

public interface CommentsServer {

	public Comments getcommentsById(Long cId);
	public Comments getcomments(Long fId);
	public int createComments(Comments comments);

}
