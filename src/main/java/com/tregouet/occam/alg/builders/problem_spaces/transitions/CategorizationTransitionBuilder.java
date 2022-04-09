package com.tregouet.occam.alg.builders.problem_spaces.transitions;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;

/**
 * Input list must be topologically ordered
 * @author Gael Tregouet
 *
 */
public interface CategorizationTransitionBuilder 
	extends Function<List<ICategorizationState>, Set<ACategorizationTransition>> { 

}
