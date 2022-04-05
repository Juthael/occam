package com.tregouet.occam.alg.builders.representations.partitions.as_strings;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

@FunctionalInterface
public interface PartitionStringBuilder 
	extends BiFunction<
		InvertedTree<IConcept, IIsA>, 
		DirectedAcyclicGraph<Integer, AbstractDifferentiae>, 
		String> {

}
