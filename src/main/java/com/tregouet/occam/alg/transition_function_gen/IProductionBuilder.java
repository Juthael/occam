package com.tregouet.occam.alg.transition_function_gen;

import java.util.List;

import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.specific.IProduction;

public interface IProductionBuilder<T extends IProduction> {
	
	/**
	 * Meaningless if Concepts.isA(source.cat, target.cat) == false
	 * @param source
	 * @param target
	 */
	IProductionBuilder<T> input(IDenotation source, IDenotation target);
	
	List<T> output();

}
