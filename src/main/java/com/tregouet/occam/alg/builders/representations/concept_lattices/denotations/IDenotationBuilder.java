package com.tregouet.occam.alg.builders.representations.concept_lattices.denotations;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface IDenotationBuilder {
	
	IDenotationBuilder input(Collection<IContextObject> extent);
	
	Set<IConstruct> output();
	
	

}
