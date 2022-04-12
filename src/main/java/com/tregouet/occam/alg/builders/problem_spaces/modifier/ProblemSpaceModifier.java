package com.tregouet.occam.alg.builders.problem_spaces.modifier;

import java.util.Set;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilder;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;

public interface ProblemSpaceModifier {
	
	IProblemSpace remove(IProblemSpace pbSpace, int stateID);
	
	IProblemSpace remove(IProblemSpace pbSpace, Set<Integer> stateIDs);
	
	IProblemSpace add(IProblemSpace pbSpace, String regularExpression);
	
	IProblemSpace restrictTo(IProblemSpace pbSpace, Set<Integer> stateIDs);
	
	public static TransitionBuilder getCategorizationTransitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getCategorizationTransitionBuilder();
	}
	
	public static PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getPartialRepresentationLateSetter();
	}

}
