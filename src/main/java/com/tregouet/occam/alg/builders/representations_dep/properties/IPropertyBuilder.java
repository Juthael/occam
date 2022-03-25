package com.tregouet.occam.alg.builders.representations_dep.properties;

import java.util.Set;

import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface IPropertyBuilder {
	
	void intput(IRepresentationTransitionFunction transFunction);
	
	Set<IProperty> output();

}
