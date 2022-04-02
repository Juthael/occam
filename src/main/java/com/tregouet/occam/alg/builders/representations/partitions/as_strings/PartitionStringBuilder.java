package com.tregouet.occam.alg.builders.representations.partitions.as_strings;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface PartitionStringBuilder 
	extends BiFunction<
		Tree<IConcept, IIsA>, 
		DirectedAcyclicGraph<Integer, AbstractDifferentiae>, 
		String> {

}
