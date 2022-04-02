package com.tregouet.occam.alg.builders.representations.partitions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface PartitionBuilder 
	extends BiFunction<Tree<IConcept, IIsA>, Tree<Integer, AbstractDifferentiae>, Set<IPartition>> {

}
