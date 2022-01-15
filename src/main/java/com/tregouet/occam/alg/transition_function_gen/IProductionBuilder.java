package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.languages.specific.IEdgeProduction;

public interface IProductionBuilder {
	
	List<IEdgeProduction> getProductions();

}
