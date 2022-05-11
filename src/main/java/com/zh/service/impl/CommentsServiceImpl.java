package com.zh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.CommentsMapper;
import com.zh.po.Comments;
import com.zh.po.CommentsExample;
import com.zh.service.CommentsServer;
@Service
public class CommentsServiceImpl implements CommentsServer {

	@Autowired
	private CommentsMapper commentsMapper;
	
	@Override
	public Comments getcommentsById(Long cId) {
		return commentsMapper.selectByPrimaryKey(cId);
	}

	@Override
	public Comments getcomments(Long fId) {
		CommentsExample example = new CommentsExample();
		//用来封装查询条件的
		CommentsExample.Criteria criteria=example.createCriteria();
		
		criteria.andFIdEqualTo(fId);
		List<Comments> list= commentsMapper.selectByExample(example);
	    return list.get(0);
	}

	@Override
	public int createComments(Comments comments) {
		return commentsMapper.insert(comments);
	}

}
