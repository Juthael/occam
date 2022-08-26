package com.tregouet.occam.alg.displayers.visualizers;

import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphViz;
import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive.BasicDescriptionViz;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive.BasicDescriptionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive.BasicDescriptionVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt.SummarizedPropOptDescriptionViz;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt.SummarizedPropOptDescriptionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt.SummarizedPropOptDescriptionVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt.SummarizedWeightOptDescriptionViz;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt.SummarizedWeightOptDescriptionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt.SummarizedWeightOptDescriptionVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceViz;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionViz;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionVizStrategy;

public class VisualizersAbstractFactory {

	public static final VisualizersAbstractFactory INSTANCE = new VisualizersAbstractFactory();

	private ConceptGraphVizStrategy conceptGraphVizStrategy = null;
	private BasicDescriptionVizStrategy basicDescriptionVizStrategy = null;
	private SummarizedWeightOptDescriptionVizStrategy summarizedWeightOptDescriptionVizStrategy = null;
	private SummarizedPropOptDescriptionVizStrategy summarizedPropOptDescriptionVizStrategy = null;
	private ProblemSpaceVizStrategy problemSpaceVizStrategy = null;
	private TransitionFunctionVizStrategy transitionFunctionVizStrategy = null;

	private VisualizersAbstractFactory() {
	}

	public ConceptGraphViz getConceptGraphViz() {
		return ConceptGraphVizFactory.INSTANCE.apply(conceptGraphVizStrategy);
	}

	public BasicDescriptionViz getDescriptionViz() {
		return BasicDescriptionVizFactory.INSTANCE.apply(basicDescriptionVizStrategy);
	}

	public ProblemSpaceViz getProblemSpaceViz() {
		return ProblemSpaceVizFactory.INSTANCE.apply(problemSpaceVizStrategy);
	}

	public TransitionFunctionViz getTransitionFunctionViz() {
		return TransitionFunctionVizFactory.INSTANCE.apply(transitionFunctionVizStrategy);
	}
	
	public SummarizedWeightOptDescriptionViz getSummarizedWeightOptDescriptionViz() {
		return SummarizedWeightOptDescriptionVizFactory.INSTANCE.apply(summarizedWeightOptDescriptionVizStrategy);
	}
	
	public SummarizedPropOptDescriptionViz getSummarizedPropOptDescriptionViz() {
		return SummarizedPropOptDescriptionVizFactory.INSTANCE.apply(summarizedPropOptDescriptionVizStrategy);
	}

	public void setUpStrategy(VisualizationStrategy strategy) {
		switch (strategy) {
		case VISUALIZATION_STRATEGY_1:
			conceptGraphVizStrategy = ConceptGraphVizStrategy.BASIC;
			basicDescriptionVizStrategy = BasicDescriptionVizStrategy.USE_DIFF_LABELLER;
			summarizedWeightOptDescriptionVizStrategy = SummarizedWeightOptDescriptionVizStrategy.USE_DIFF_WEIGHT_OPT_SUMMARIZER;
			summarizedPropOptDescriptionVizStrategy = SummarizedPropOptDescriptionVizStrategy.USE_DIFF_PROP_OPT_SUMMARIZER;
			problemSpaceVizStrategy = ProblemSpaceVizStrategy.BASIC;
			transitionFunctionVizStrategy = TransitionFunctionVizStrategy.BASIC;
			break;
		default:
			break;
		}
	}

}
