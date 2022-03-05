package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.languages.specific.IProduction;

public interface IProductionSetBuilder<T extends IProduction> {
	
	IProductionSetBuilder<T> input(IConcepts concepts);
	
	List<T> output();

}
