package com.tregouet.occam.alg.setters.differentiae_coeff;

import com.tregouet.occam.alg.setters.Setter;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

public interface DifferentiaeCoeffSetter extends Setter<AbstractDifferentiae> {

	DifferentiaeCoeffSetter setContext(InvertedTree<IConcept, IIsA> conceptTree);

}
