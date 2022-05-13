package com.tregouet.occam.alg.displayers.formatters;

import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayerFactory;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayerStrategy;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverterFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverterStrategy;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabellerStrategy;

public class FormattersAbstractFactory {

	public static final FormattersAbstractFactory INSTANCE = new FormattersAbstractFactory();

	private TransitionFunctionLabellerStrategy transitionFunctionLabellerStrategy = null;
	private PropertyLabellerStrategy propertyLabellerStrategy = null;
	private DifferentiaeLabellerStrategy differentiaeLabellerStrategy = null;
	private Sorting2StringConverterStrategy sorting2StringConverterStrategy = null;
	private ProblemStateLabellerStrategy problemStateLabellerStrategy = null;
	private ProblemTransitionLabellerStrategy problemTransitionLabellerStrategy = null;
	private FactDisplayerStrategy factDisplayerStrategy = null;

	private FormattersAbstractFactory() {
	}

	public DifferentiaeLabeller getDifferentiaeDisplayer() {
		return DifferentiaeLabellerFactory.INSTANCE.apply(differentiaeLabellerStrategy);
	}
	
	public Sorting2StringConverter getSorting2StringConverter() {
		return Sorting2StringConverterFactory.INSTANCE.apply(sorting2StringConverterStrategy);
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
	
	public FactDisplayer getFactDisplayer() {
		return FactDisplayerFactory.INSTANCE.apply(factDisplayerStrategy);
	}

	public void setUpStrategy(FormattingStrategy strategy) {
		switch (strategy) {
		case LABELLING_STRATEGY_1:
			transitionFunctionLabellerStrategy = TransitionFunctionLabellerStrategy.REMOVE_NON_SALIENT_APP;
			propertyLabellerStrategy = PropertyLabellerStrategy.AS_PRODUCTIONS;
			differentiaeLabellerStrategy = DifferentiaeLabellerStrategy.PROPERTIES_THEN_WEIGHT;
			sorting2StringConverterStrategy = Sorting2StringConverterStrategy.RECURSIVE_FRAMING;
			problemStateLabellerStrategy = ProblemStateLabellerStrategy.AS_NESTED_FRAMES;
			problemTransitionLabellerStrategy = ProblemTransitionLabellerStrategy.PROBABILITY;
			factDisplayerStrategy = FactDisplayerStrategy.NON_TRIVIAL_MAXIMAL_FACTS;
			break;
		default:
			break;
		}
	}

}
