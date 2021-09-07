package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.exceptions.PropertyTargetingException;

public interface IInfoMeter {
	
	double getInformativity(IOperator property);
	
	double getInformativity(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException;

}
