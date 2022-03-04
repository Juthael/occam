package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

public interface IProductionBuilder {
	
	List<IProductionAsEdge> getProductions();

}
