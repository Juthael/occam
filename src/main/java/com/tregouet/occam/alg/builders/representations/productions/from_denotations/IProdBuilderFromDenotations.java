package com.tregouet.occam.alg.builders.representations.productions.from_denotations;

import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IDenotation;

public interface IProdBuilderFromDenotations {
	
	/**
	 * Meaningless if Concepts.isA(source.cat, target.cat) == false
	 * @param source
	 * @param target
	 */
	IProdBuilderFromDenotations input(IDenotation source, IDenotation target);
	
	Set<IContextualizedProduction> output();

}
