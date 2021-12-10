package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.IProduction;

public interface IProductionBuilder {
	
	List<IProduction> getProductions();

}
