package com.tregouet.occam.alg.builders.representations.properties.transitions;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.preconcepts.IPreconcepts;

public interface IProductionSetBuilder<T extends IProduction> {
	
	IProductionSetBuilder<T> input(IPreconcepts preconcepts);
	
	Set<T> output();

}
