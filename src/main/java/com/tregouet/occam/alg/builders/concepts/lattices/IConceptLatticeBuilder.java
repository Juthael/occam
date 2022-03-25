package com.tregouet.occam.alg.builders.concepts.lattices;

import java.util.Collection;

import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IConceptLattice;

public interface IConceptLatticeBuilder {
	
	IConceptLatticeBuilder input(Collection<IContextObject> objects);
	
	IConceptLattice output();

}
