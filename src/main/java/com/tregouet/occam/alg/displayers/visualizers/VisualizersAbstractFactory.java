package com.tregouet.occam.alg.displayers.visualizers;

import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphViz;
import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.concepts.ConceptGraphVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionViz;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceViz;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceVizStrategy;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionViz;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionVizFactory;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionVizStrategy;

public class VisualizersAbstractFactory {

	public static final VisualizersAbstractFactory INSTANCE = new VisualizersAbstractFactory();

	private ConceptGraphVizStrategy conceptGraphVizStrategy = null;
	private DescriptionVizStrategy descriptionVizStrategy = null;
	private ProblemSpaceVizStrategy problemSpaceVizStrategy = null;
	private TransitionFunctionVizStrategy transitionFunctionVizStrategy = null;

	private VisualizersAbstractFactory() {
	}

	public ConceptGraphViz getConceptGraphViz() {
		return ConceptGraphVizFactory.INSTANCE.apply(conceptGraphVizStrategy);
	}

	public DescriptionViz getDescriptionViz() {
		return DescriptionVizFactory.INSTANCE.apply(descriptionVizStrategy);
	}

	public ProblemSpaceViz getProblemSpaceViz() {
		return ProblemSpaceVizFactory.INSTANCE.apply(problemSpaceVizStrategy);
	}

	public TransitionFunctionViz getTransitionFunctionViz() {
		return TransitionFunctionVizFactory.INSTANCE.apply(transitionFunctionVizStrategy);
	}

	public void setUpStrategy(VisualizationStrategy strategy) {
		switch (strategy) {
		case VISUALIZATION_STRATEGY_1:
			conceptGraphVizStrategy = ConceptGraphVizStrategy.BASIC;
			descriptionVizStrategy = DescriptionVizStrategy.DELEGATE_FORMAT_MANAGING;
			problemSpaceVizStrategy = ProblemSpaceVizStrategy.BASIC;
			transitionFunctionVizStrategy = TransitionFunctionVizStrategy.BASIC;
			break;
		default:
			break;
		}
	}

}
