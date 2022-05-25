package com.tregouet.occam.alg;

import com.tregouet.occam.alg.builders.GenerationStrategy;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.FormattingStrategy;
import com.tregouet.occam.alg.displayers.visualizers.VisualizationStrategy;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScoringStrategy;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.SettingStrategy;

public class OverallAbstractFactory {

	public static final OverallAbstractFactory INSTANCE = new OverallAbstractFactory();

	private GenerationStrategy generationStrategy = null;
	private ScoringStrategy scoringStrategy = null;
	private SettingStrategy settingStrategy = null;
	private FormattingStrategy formattingStrategy = null;
	private VisualizationStrategy visualizationStrategy = null;

	private OverallAbstractFactory() {
	}

	public void apply(OverallStrategy strategy) {
		switch (strategy) {
		case OVERALL_STRATEGY_1:
			generationStrategy = GenerationStrategy.GENERATION_STRATEGY_1;
			scoringStrategy = ScoringStrategy.SCORING_STRATEGY_1;
			settingStrategy = SettingStrategy.SETTING_STRATEGY_1;
			formattingStrategy = FormattingStrategy.LABELLING_STRATEGY_1;
			visualizationStrategy = VisualizationStrategy.VISUALIZATION_STRATEGY_1;
			break;
		case OVERALL_STRATEGY_2 : 
			generationStrategy = GenerationStrategy.GENERATION_STRATEGY_1;
			scoringStrategy = ScoringStrategy.SCORING_STRATEGY_1;
			settingStrategy = SettingStrategy.SETTING_STRATEGY_2;
			formattingStrategy = FormattingStrategy.LABELLING_STRATEGY_1;
			visualizationStrategy = VisualizationStrategy.VISUALIZATION_STRATEGY_1;
		default:
			break;
		}
		setUp();
	}

	private void setUp() {
		GeneratorsAbstractFactory.INSTANCE.setUpStrategy(generationStrategy);
		ScorersAbstractFactory.INSTANCE.setUpStrategy(scoringStrategy);
		SettersAbstractFactory.INSTANCE.setUpStrategy(settingStrategy);
		FormattersAbstractFactory.INSTANCE.setUpStrategy(formattingStrategy);
		VisualizersAbstractFactory.INSTANCE.setUpStrategy(visualizationStrategy);
	}

}
