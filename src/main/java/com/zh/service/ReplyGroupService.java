package com.zh.service;

import java.util.List;

import com.zh.po.ReplyGroup;

public interface ReplyGroupService {
	int createReply(ReplyGroup replyGroup);
	List<ReplyGroup> replyGrouplist(String creator);
	int deleteReply(String replyId);
}
