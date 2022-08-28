package com.tregouet.occam.alg.displayers.formatters;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.DifferentiaeFormatter;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.DifferentiaeFormatterFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.DifferentiaeFormatterStrategy;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.computations.ComputationLabeller;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.computations.ComputationLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.computations.ComputationLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.GenusFormatter;
import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.GenusFormatterFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.GenusFormatterStrategy;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayerFactory;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayerStrategy;
import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatter;
import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatterFactory;
import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatterStrategy;
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
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.AbstrAppLabeller;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.AbstrAppLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.AbstrAppLabellerStrategy;

public class FormattersAbstractFactory {

	public static final FormattersAbstractFactory INSTANCE = new FormattersAbstractFactory();

	private AbstrAppLabellerStrategy abstrAppLabellerStrategy = null;
	private TransitionLabellerStrategy transitionLabellerStrategy = null;
	private TransitionFunctionLabellerStrategy transitionFunctionLabellerStrategy = null;
	private ComputationLabellerStrategy computationLabellerStrategy = null;
	private PropertyLabellerStrategy propertyLabellerStrategy = null;
	private GenusFormatterStrategy genusFormatterStrategy = null;
	private DifferentiaeFormatterStrategy differentiaeFormatterStrategy = null;
	private Sorting2StringConverterStrategy sorting2StringConverterStrategy = null;
	private ProblemStateLabellerStrategy problemStateLabellerStrategy = null;
	private ProblemTransitionLabellerStrategy problemTransitionLabellerStrategy = null;
	private FactDisplayerStrategy factDisplayerStrategy = null;
	private MatrixFormatterStrategy matrixFormatterStrategy = null;

	private FormattersAbstractFactory() {
	}

	public AbstrAppLabeller getAbstrAppLabeller() {
		return AbstrAppLabellerFactory.INSTANCE.apply(abstrAppLabellerStrategy);
	}

	public ComputationLabeller getComputationLabeller() {
		return ComputationLabellerFactory.INSTANCE.apply(computationLabellerStrategy);
	}

	public DifferentiaeFormatter getDifferentiaeFormatter() {
		return DifferentiaeFormatterFactory.INSTANCE.apply(differentiaeFormatterStrategy);
	}

	public FactDisplayer getFactDisplayer() {
		return FactDisplayerFactory.INSTANCE.apply(factDisplayerStrategy);
	}

	public GenusFormatter getGenusFormatter() {
		return GenusFormatterFactory.INSTANCE.apply(genusFormatterStrategy);
	}

	public MatrixFormatter getMatrixFormatter() {
		return MatrixFormatterFactory.INSTANCE.apply(matrixFormatterStrategy);
	}

	public ProblemStateLabeller getProblemStateDisplayer() {
		return ProblemStateLabellerFactory.INSTANCE.apply(problemStateLabellerStrategy);
	}

	public ProblemTransitionLabeller getProblemTransitionDisplayer() {
		return ProblemTransitionLabellerFactory.INSTANCE.apply(problemTransitionLabellerStrategy);
	}

	public PropertyLabeller getPropertyLabeller() {
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
			abstrAppLabellerStrategy = AbstrAppLabellerStrategy.CONJUNCTION;
			transitionLabellerStrategy = TransitionLabellerStrategy.CANONICAL_NOTATION;
			transitionFunctionLabellerStrategy = TransitionFunctionLabellerStrategy.DISPLAY_ALL_TRANSITIONS;
			computationLabellerStrategy = ComputationLabellerStrategy.CONJUNCTION;
			propertyLabellerStrategy = PropertyLabellerStrategy.CURLY_BRACKETS_WITH_WEIGHT;
			genusFormatterStrategy = GenusFormatterStrategy.ID_THEN_EXTENT;
			differentiaeFormatterStrategy = DifferentiaeFormatterStrategy.MANAGE_FORMAT;
			sorting2StringConverterStrategy = Sorting2StringConverterStrategy.RECURSIVE_FRAMING;
			problemStateLabellerStrategy = ProblemStateLabellerStrategy.AS_NESTED_FRAMES_WITH_SCORE;
			problemTransitionLabellerStrategy = ProblemTransitionLabellerStrategy.WEIGHT;
			factDisplayerStrategy = FactDisplayerStrategy.NON_TRIVIAL_MAXIMAL_FACTS;
			matrixFormatterStrategy = MatrixFormatterStrategy.THREE_DECIMALS;
			break;
		default:
			break;
		}
	}

}
