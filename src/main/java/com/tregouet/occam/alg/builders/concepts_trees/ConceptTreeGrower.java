package com.tregouet.occam.alg.builders.concepts_trees;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface ConceptTreeGrower extends BiFunction<IConceptLattice, InvertedTree<IConcept, IIsA>, Set<InvertedTree<IConcept, IIsA>>> {

}
