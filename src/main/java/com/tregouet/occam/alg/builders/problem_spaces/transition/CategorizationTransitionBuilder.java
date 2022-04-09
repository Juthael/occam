package com.tregouet.occam.alg.builders.problem_spaces.transition;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.ACategorizationStateTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;

public interface CategorizationTransitionBuilder 
	extends Function<Set<ICategorizationState>, Set<ACategorizationStateTransition>> { 

}
