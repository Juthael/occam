package com.tregouet.occam.alg.builders.preconcepts.lattices;

import java.util.Collection;

import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;

public interface IPreconceptLatticeBuilder {
	
	IPreconceptLatticeBuilder input(Collection<IContextObject> objects);
	
	IPreconceptLattice output();

}
