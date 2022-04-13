package com.tregouet.occam.alg.builders.representations.partitions.as_strings;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

/**
 * The second graph must be isomorphic to - or a restriction of - the first one, with each concept
 * in the first graph matching with its iD in the second one. 
 * @author Gael Tregouet
 *
 */
@FunctionalInterface
public interface PartitionStringBuilder 
	extends BiFunction<
		DirectedAcyclicGraph<IConcept, IIsA>, 
		DirectedAcyclicGraph<Integer, AbstractDifferentiae>, 
		String> {

}
