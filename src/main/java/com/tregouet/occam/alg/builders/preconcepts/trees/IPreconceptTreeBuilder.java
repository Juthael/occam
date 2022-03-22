package com.tregouet.occam.alg.builders.preconcepts.trees;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;
import com.tregouet.tree_finder.data.Tree;

public interface IPreconceptTreeBuilder extends Iterator<Tree<IPreconcept, IIsA>> {
	
	IPreconceptTreeBuilder input(IPreconceptLattice preconceptLattice) throws IOException;
	
	Set<Tree<IPreconcept, IIsA>> output();

}
