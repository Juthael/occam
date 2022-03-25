package com.tregouet.occam.alg.builders.concepts.lattices;

import java.util.Collection;

import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface IConceptLatticeBuilder {
	
	IConceptLatticeBuilder input(Collection<IContextObject> objects);
	
	IConceptLattice output();

}
