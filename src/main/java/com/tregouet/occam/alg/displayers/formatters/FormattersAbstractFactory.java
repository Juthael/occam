package com.tregouet.occam.alg.displayers.formatters;

import com.tregouet.occam.alg.displayers.formatters.applications.ApplicationLabeller;
import com.tregouet.occam.alg.displayers.formatters.applications.ApplicationLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.applications.ApplicationLabellerStrategy;
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
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabeller;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabellerStrategy;

public class FormattersAbstractFactory {

	public static final FormattersAbstractFactory INSTANCE = new FormattersAbstractFactory();

	private TransitionLabellerStrategy transitionLabellerStrategy = null;
	private TransitionFunctionLabellerStrategy transitionFunctionLabellerStrategy = null;
	private ApplicationLabellerStrategy applicationLabellerStrategy = null;
	private PropertyLabellerStrategy propertyLabellerStrategy = null;
	private DifferentiaeLabellerStrategy differentiaeLabellerStrategy = null;
	private Sorting2StringConverterStrategy sorting2StringConverterStrategy = null;
	private ProblemStateLabellerStrategy problemStateLabellerStrategy = null;
	private ProblemTransitionLabellerStrategy problemTransitionLabellerStrategy = null;
	private FactDisplayerStrategy factDisplayerStrategy = null;

	private FormattersAbstractFactory() {
	}

	public ApplicationLabeller getApplicationLabeller() {
		return ApplicationLabellerFactory.INSTANCE.apply(applicationLabellerStrategy);
	}

	public DifferentiaeLabeller getDifferentiaeLabeller() {
		return DifferentiaeLabellerFactory.INSTANCE.apply(differentiaeLabellerStrategy);
	}

	public FactDisplayer getFactDisplayer() {
		return FactDisplayerFactory.INSTANCE.apply(factDisplayerStrategy);
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

	public Sorting2StringConverter getSorting2StringConverter() {
		return Sorting2StringConverterFactory.INSTANCE.apply(sorting2StringConverterStrategy);
	}

	public TransitionFunctionLabeller getTransitionFunctionDisplayer() {
		return TransitionFunctionLabellerFactory.INSTANCE.apply(transitionFunctionLabellerStrategy);
	}
	
	public TransitionLabeller getTransitionLabeller() {
		return TransitionLabellerFactory.INSTANCE.apply(transitionLabellerStrategy);
	}

	public void setUpStrategy(FormattingStrategy strategy) {
		switch (strategy) {
		case LABELLING_STRATEGY_1:
			transitionLabellerStrategy = TransitionLabellerStrategy.CANONICAL_NOTATION;
			transitionFunctionLabellerStrategy = TransitionFunctionLabellerStrategy.DISPLAY_ALL_TRANSITIONS;
			applicationLabellerStrategy = ApplicationLabellerStrategy.ANGLE_BRACKETS;
			propertyLabellerStrategy = PropertyLabellerStrategy.CURLY_BRACKETS;
			differentiaeLabellerStrategy = DifferentiaeLabellerStrategy.PROPERTIES_THEN_WEIGHT;
			sorting2StringConverterStrategy = Sorting2StringConverterStrategy.RECURSIVE_FRAMING;
			problemStateLabellerStrategy = ProblemStateLabellerStrategy.AS_NESTED_FRAMES_WITH_SCORE;
			problemTransitionLabellerStrategy = ProblemTransitionLabellerStrategy.WEIGHT;
			factDisplayerStrategy = FactDisplayerStrategy.NON_TRIVIAL_MAXIMAL_FACTS;
			break;
		default:
			break;
		}
	}

}
