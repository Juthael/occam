package com.tregouet.occam.alg.builders.problem_spaces.modifier;

import java.util.Set;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.ProblemTransitionBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;

public interface ProblemSpaceModifier {

	public static ProblemTransitionBuilder getCategorizationTransitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemTransitionBuilder();
	}
	
	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}	

	public static PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getPartialRepresentationLateSetter();
	}

	IProblemSpace add(IProblemSpace pbSpace, String regularExpression);

	IProblemSpace remove(IProblemSpace pbSpace, int stateID);

	IProblemSpace remove(IProblemSpace pbSpace, Set<Integer> stateIDs);

	IProblemSpace restrictTo(IProblemSpace pbSpace, Set<Integer> stateIDs);

}
