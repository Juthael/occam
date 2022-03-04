package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;

public interface IProductionBuilder {
	
	List<IBasicProductionAsEdge> getProductions();

}
