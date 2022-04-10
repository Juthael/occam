package com.tregouet.occam.alg.setters.differentiae_coeff;

import com.tregouet.occam.alg.setters.Setter;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface DifferentiaeCoeffSetter extends Setter<AbstractDifferentiae> {
	
	DifferentiaeCoeffSetter setContext(Tree<Integer, AbstractDifferentiae> classification);

}
