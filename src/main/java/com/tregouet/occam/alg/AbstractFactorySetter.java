package com.tregouet.occam.alg;

import com.tregouet.occam.alg.builders.GenerationStrategy;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScoringStrategy;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.SettingStrategy;

public class AbstractFactorySetter {
	
	public static final AbstractFactorySetter INSTANCE = new AbstractFactorySetter();
	
	private GenerationStrategy generationStrategy = null;
	private ScoringStrategy scoringStrategy = null;
	private SettingStrategy settingStrategy = null;
	
	private AbstractFactorySetter() {
	}
	
	public AbstractFactorySetter define(OverallStrategy strategy) {
		switch (strategy) {
			case OVERALL_STRATEGY_1 : 
				generationStrategy = GenerationStrategy.GENERATION_STRATEGY_1;
				scoringStrategy = ScoringStrategy.SCORING_STRATEGY_1;
				settingStrategy = SettingStrategy.SETTING_STRATEGY_1;
				break;
			default : 
				return null;
		}
		return this;
	}
	
	public void set() {
		GeneratorsAbstractFactory.INSTANCE.setUpStrategy(generationStrategy);
		ScorersAbstractFactory.INSTANCE.setUpStrategy(scoringStrategy);
		SettersAbstractFactory.INSTANCE.setUpStrategy(settingStrategy);
	}

}
