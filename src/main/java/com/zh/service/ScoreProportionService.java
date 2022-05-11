package com.zh.service;

import com.zh.po.ScoreProportion;

public interface ScoreProportionService {
	public ScoreProportion getScoreProportion(String proportionId);
	int update(ScoreProportion scoreProportion);
}
