package com.zh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.ReplyGroupMapper;
import com.zh.po.ReplyGroup;
import com.zh.po.ReplyGroupExample;
@Service
public class ReplyGroupServiceImpl implements com.zh.service.ReplyGroupService {

	@Autowired
	private ReplyGroupMapper replyGroupMapper;
	
	@Override
	public int createReply(ReplyGroup replyGroup) {
		return replyGroupMapper.insert(replyGroup);
	}

	@Override
	public List<ReplyGroup> replyGrouplist(String creator) {
		ReplyGroupExample example = new ReplyGroupExample();
		ReplyGroupExample.Criteria criteria = example.createCriteria();
		criteria.andCreatorEqualTo(creator);
		return replyGroupMapper.selectByExample(example);
	}

	@Override
	public int deleteReply(String replyId) {
		return replyGroupMapper.deleteByPrimaryKey(replyId);
	}

}
