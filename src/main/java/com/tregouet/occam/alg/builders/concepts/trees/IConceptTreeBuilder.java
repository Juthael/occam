package com.tregouet.occam.alg.builders.concepts.trees;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConceptLattice;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptTreeBuilder extends Iterator<Tree<IConcept, IIsA>> {
	
	IConceptTreeBuilder input(IConceptLattice conceptLattice) throws IOException;
	
	Set<Tree<IConcept, IIsA>> output();

}
