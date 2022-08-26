package com.tregouet.occam.alg.displayers.formatters;

import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive.DifferentiaeExhaustiveLabeller;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive.DifferentiaeExhaustiveLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive.DifferentiaeExhaustiveLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.DifferentiaePropOptSummarizer;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.DifferentiaePropOptSummarizerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.DifferentiaePropOptSummarizerStrategy;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer.DifferentiaeWeightOptSummarizer;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer.DifferentiaeWeightOptSummarizerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer.DifferentiaeWeightOptSummarizerStrategy;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabellerStrategy;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.ComputationLabeller;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.ComputationLabellerFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.ComputationLabellerStrategy;
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
	private DifferentiaeExhaustiveLabellerStrategy differentiaeExhaustiveLabellerStrategy = null;
	private DifferentiaeWeightOptSummarizerStrategy differentiaeWeightOptSummarizerStrategy = null;
	private DifferentiaePropOptSummarizerStrategy differentiaePropOptSummarizerStrategy = null;
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

	public DifferentiaeExhaustiveLabeller getDifferentiaeExhaustiveLabeller() {
		return DifferentiaeExhaustiveLabellerFactory.INSTANCE.apply(differentiaeExhaustiveLabellerStrategy);
	}

	public FactDisplayer getFactDisplayer() {
		return FactDisplayerFactory.INSTANCE.apply(factDisplayerStrategy);
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
	
	public DifferentiaeWeightOptSummarizer getDifferentiaeWeightOptSummarizer() {
		return DifferentiaeWeightOptSummarizerFactory.INSTANCE.apply(differentiaeWeightOptSummarizerStrategy);
	}
	
	public DifferentiaePropOptSummarizer getDifferentiaePropOptSummarizer() {
		return DifferentiaePropOptSummarizerFactory.INSTANCE.apply(differentiaePropOptSummarizerStrategy);
	}

	public void setUpStrategy(FormattingStrategy strategy) {
		switch (strategy) {
		case LABELLING_STRATEGY_1:
			abstrAppLabellerStrategy = AbstrAppLabellerStrategy.CONJUNCTION;
			transitionLabellerStrategy = TransitionLabellerStrategy.CANONICAL_NOTATION;
			transitionFunctionLabellerStrategy = TransitionFunctionLabellerStrategy.DISPLAY_ALL_TRANSITIONS;
			computationLabellerStrategy = ComputationLabellerStrategy.CONJUNCTION;
			propertyLabellerStrategy = PropertyLabellerStrategy.CURLY_BRACKETS_WITH_WEIGHT;
			differentiaeExhaustiveLabellerStrategy = DifferentiaeExhaustiveLabellerStrategy.PROPERTIES_THEN_WEIGHT;
			differentiaeWeightOptSummarizerStrategy = DifferentiaeWeightOptSummarizerStrategy.NON_REDUNDANT_WEIGHT_OPTIMAL_SUBSET;
			differentiaePropOptSummarizerStrategy = DifferentiaePropOptSummarizerStrategy.NON_REDUNDANT_PROP_OPT_SUBSET;
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
