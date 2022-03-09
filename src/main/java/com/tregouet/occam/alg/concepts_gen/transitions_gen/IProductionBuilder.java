package com.tregouet.occam.alg.concepts_gen.transitions_gen;

import java.util.List;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.preconcepts.IDenotation;

public interface IProductionBuilder<T extends IProduction> {
	
	/**
	 * Meaningless if Concepts.isA(source.cat, target.cat) == false
	 * @param source
	 * @param target
	 */
	IProductionBuilder<T> input(IDenotation source, IDenotation target);
	
	List<T> output();

}
