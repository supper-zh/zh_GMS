package com.zh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.ScoreProportionMapper;
import com.zh.po.ScoreProportion;
import com.zh.service.ScoreProportionService;
@Service
public class ScoreProportionServiceImpl implements ScoreProportionService {

	@Autowired
	private ScoreProportionMapper scoreProportionMapper;
	@Override
	public ScoreProportion getScoreProportion(String proportionId) {
		return scoreProportionMapper.selectByPrimaryKey(proportionId);
	}
	@Override
	public int update(ScoreProportion scoreProportion) {
		return scoreProportionMapper.updateByPrimaryKey(scoreProportion);
	}

}
