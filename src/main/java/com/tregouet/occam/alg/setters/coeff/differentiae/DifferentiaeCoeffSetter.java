package com.tregouet.occam.alg.setters.coeff.differentiae;

import com.tregouet.occam.alg.setters.Setter;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

public interface DifferentiaeCoeffSetter extends Setter<ADifferentiae> {

	DifferentiaeCoeffSetter setContext(InvertedTree<IConcept, IIsA> conceptTree);

}
