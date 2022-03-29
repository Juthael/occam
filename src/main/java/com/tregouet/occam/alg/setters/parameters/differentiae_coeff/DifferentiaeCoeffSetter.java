package com.tregouet.occam.alg.setters.parameters.differentiae_coeff;

import com.tregouet.occam.alg.setters.parameters.Parameterizer;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface DifferentiaeCoeffSetter extends Parameterizer<AbstractDifferentiae> {
	
	DifferentiaeCoeffSetter setContext(Tree<Integer, AbstractDifferentiae> classification);

}
