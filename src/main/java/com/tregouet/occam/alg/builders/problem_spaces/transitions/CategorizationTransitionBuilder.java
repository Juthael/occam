package com.tregouet.occam.alg.builders.problem_spaces.transitions;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

/**
 * Input list must be topologically ordered
 * @author Gael Tregouet
 *
 */
public interface CategorizationTransitionBuilder 
	extends Function<List<IProblemState>, Set<AProblemStateTransition>> { 

}
