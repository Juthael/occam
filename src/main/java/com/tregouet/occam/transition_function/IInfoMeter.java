package com.tregouet.occam.transition_function;

import java.util.List;

import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.exceptions.PropertyTargetingException;

public interface IInfoMeter {
	
	double getInformativity(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException;
	
	double getInformativity(IOperator property);

}
