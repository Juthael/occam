package com.tregouet.occam.alg.builders.representations.concept_trees;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface IConceptTreeBuilder extends Function<IConceptLattice, Set<Tree<IConcept, IIsA>>> {

}
