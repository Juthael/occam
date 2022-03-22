package com.tregouet.occam.alg.builders.preconcepts.denotations;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.preconcepts.IContextObject;

public interface IDenotationBuilder {
	
	IDenotationBuilder input(Collection<IContextObject> extent);
	
	Set<IConstruct> output();
	
	

}
