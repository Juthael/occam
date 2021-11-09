package com.tregouet.occam.cost_calculation.property_weighing;

import java.util.List;

import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.exceptions.PropertyTargetingException;

public interface IPropertyWeigher {
	
	double getPropertyWeight(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException;
	
	double getPropertyWeight(IOperator property);

}
