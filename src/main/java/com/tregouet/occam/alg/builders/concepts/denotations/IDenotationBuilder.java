package com.tregouet.occam.alg.builders.concepts.denotations;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IDenotationBuilder {
	
	IDenotationBuilder input(Collection<IContextObject> extent);
	
	Set<IConstruct> output();
	
	

}
