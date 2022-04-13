package com.tregouet.occam.alg.displayers.problem_states;

import java.util.function.Function;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilder;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;

/**
 * The second parameter must be the concept lattice generated from the current context
 * @author Gael Tregouet
 *
 */
public interface ProblemStateDisplayer extends Function<IProblemState, String> {
	
	public static PartitionStringBuilder getPartitionStringBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPartitionStringBuilder();
	}

}
