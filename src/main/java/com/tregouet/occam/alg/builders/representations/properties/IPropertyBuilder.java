package com.tregouet.occam.alg.builders.representations.properties;

import java.util.Set;

import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public interface IPropertyBuilder {
	
	void intput(Set<IApplication> applications);
	
	Set<IProperty> output();

}
