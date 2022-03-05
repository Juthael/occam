package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.languages.specific.IStronglyContextualized;

public interface IProductionBuilder {
	
	List<IStronglyContextualized> getProductions();

}
