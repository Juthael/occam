package com.tregouet.occam.alg.displayers.graph_labellers;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.DifferentiaeLabellerFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.DifferentiaeLabellerStrategy;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabellerFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabellerStrategy;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_states.ProblemStateLabellerFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_states.ProblemStateLabellerStrategy;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabellerFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabellerStrategy;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabellerFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabellerStrategy;

public class LabellersAbstractFactory {

	public static final LabellersAbstractFactory INSTANCE = new LabellersAbstractFactory();

	private TransitionFunctionLabellerStrategy transitionFunctionLabellerStrategy = null;
	private PropertyLabellerStrategy propertyLabellerStrategy = null;
	private DifferentiaeLabellerStrategy differentiaeLabellerStrategy = null;
	private ProblemStateLabellerStrategy problemStateLabellerStrategy = null;
	private ProblemTransitionLabellerStrategy problemTransitionLabellerStrategy = null;

	private LabellersAbstractFactory() {
	}

	public DifferentiaeLabeller getDifferentiaeDisplayer() {
		return DifferentiaeLabellerFactory.INSTANCE.apply(differentiaeLabellerStrategy);
	}

	public ProblemStateLabeller getProblemStateDisplayer() {
		return ProblemStateLabellerFactory.INSTANCE.apply(problemStateLabellerStrategy);
	}

	public ProblemTransitionLabeller getProblemTransitionDisplayer() {
		return ProblemTransitionLabellerFactory.INSTANCE.apply(problemTransitionLabellerStrategy);
	}

	public PropertyLabeller getPropertyDisplayer() {
		return PropertyLabellerFactory.INSTANCE.apply(propertyLabellerStrategy);
	}

	public TransitionFunctionLabeller getTransitionFunctionDisplayer() {
		return TransitionFunctionLabellerFactory.INSTANCE.apply(transitionFunctionLabellerStrategy);
	}

	public void setUpStrategy(LabellingStrategy strategy) {
		switch (strategy) {
		case LABELLING_STRATEGY_1:
			transitionFunctionLabellerStrategy = TransitionFunctionLabellerStrategy.REMOVE_NON_SALIENT_APP;
			propertyLabellerStrategy = PropertyLabellerStrategy.AS_PRODUCTIONS;
			differentiaeLabellerStrategy = DifferentiaeLabellerStrategy.PROPERTIES_THEN_WEIGHT;
			problemStateLabellerStrategy = ProblemStateLabellerStrategy.AS_NESTED_FRAMES;
			problemTransitionLabellerStrategy = ProblemTransitionLabellerStrategy.WEIGHT_ONLY;
			break;
		default:
			break;
		}
	}

}
