package com.tregouet.occam.alg;

import com.tregouet.occam.alg.builders.GenerationStrategy;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.displayers.DisplayStrategy;
import com.tregouet.occam.alg.displayers.DisplayersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScoringStrategy;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.SettingStrategy;

public class OverallAbstractFactory {
	
	public static final OverallAbstractFactory INSTANCE = new OverallAbstractFactory();
	
	private GenerationStrategy generationStrategy = null;
	private ScoringStrategy scoringStrategy = null;
	private SettingStrategy settingStrategy = null;
	private DisplayStrategy displayStrategy = null;
	
	private OverallAbstractFactory() {
	}
	
	public void apply(OverallStrategy strategy) {
		switch (strategy) {
			case OVERALL_STRATEGY_1 : 
				generationStrategy = GenerationStrategy.GENERATION_STRATEGY_1;
				scoringStrategy = ScoringStrategy.SCORING_STRATEGY_1;
				settingStrategy = SettingStrategy.SETTING_STRATEGY_1;
				displayStrategy = DisplayStrategy.DISPLAY_STRATEGY_1;
				break;
			default : 
				break;
		}
		setUp();
	}
	
	private void setUp() {
		GeneratorsAbstractFactory.INSTANCE.setUpStrategy(generationStrategy);
		ScorersAbstractFactory.INSTANCE.setUpStrategy(scoringStrategy);
		SettersAbstractFactory.INSTANCE.setUpStrategy(settingStrategy);
		DisplayersAbstractFactory.INSTANCE.setUpStrategy(displayStrategy);
	}

}
