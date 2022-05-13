package com.tregouet.occam.alg.builders.pb_space.pb_transitions;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

/**
 * Input list must be topologically ordered. No transitive reduction applied to
 * the output.
 * 
 * @author Gael Tregouet
 *
 */
public interface ProblemTransitionBuilder extends Function<List<IRepresentation>, Set<AProblemStateTransition>> {

}
