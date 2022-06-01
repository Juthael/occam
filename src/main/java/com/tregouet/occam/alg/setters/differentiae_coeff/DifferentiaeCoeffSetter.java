package com.tregouet.occam.alg.setters.differentiae_coeff;

import com.tregouet.occam.alg.setters.Setter;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

public interface DifferentiaeCoeffSetter extends Setter<ADifferentiae> {

	DifferentiaeCoeffSetter setContext(InvertedTree<IConcept, IIsA> conceptTree);

}
