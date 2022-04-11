package com.tregouet.occam.alg.builders.problem_spaces;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentations;

public interface CategorizationProblemSpaceBuilder 
	extends Function<ICompleteRepresentations, IProblemSpace> {
	
	public static PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getPartialRepresentationLateSetter();
	}
	
	public static CategorizationTransitionBuilder getCategorizationTransitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getCategorizationTransitionBuilder();
	}
	
	public static CategorizationTransitionWeigher getCategorizationTransitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getCategorizationTransitionWeigher();
	}
	
	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}

}
