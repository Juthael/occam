package com.tregouet.occam.alg.builders.problem_spaces;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.ranker.ProblemTransitionRanker;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.ProblemTransitionBuilder;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentations;

public interface ProblemSpaceBuilder extends Function<ICompleteRepresentations, IProblemSpace> {

	public static ProblemTransitionBuilder getCategorizationTransitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemTransitionBuilder();
	}

	public static ProblemTransitionWeigher getProblemTransitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getCategorizationTransitionWeigher();
	}

	public static PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getPartialRepresentationLateSetter();
	}

	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}

	public static StringPatternBuilder getStringPatternBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getStringPatternBuilder();
	}
	
	public static ProblemTransitionRanker getProblemTransitionRanker() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemTransitionRanker();
	}

}
