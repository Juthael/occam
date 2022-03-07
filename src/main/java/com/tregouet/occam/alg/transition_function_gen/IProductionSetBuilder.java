package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.denotations.IPreconcepts;

public interface IProductionSetBuilder<T extends IProduction> {
	
	IProductionSetBuilder<T> input(IPreconcepts preconcepts);
	
	List<T> output();

}
