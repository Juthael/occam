package com.tregouet.occam.cost_calculation.property_weighing;

import java.util.function.Function;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.property_weighing.impl.InformativityDiagnosticity;

public class PropertyWeigherFactory implements Function<PropertyWeighingStrategy, IPropertyWeigher> {

	public PropertyWeigherFactory() {
	}

	@Override
	public IPropertyWeigher apply(PropertyWeighingStrategy strategy) {
		switch (strategy) {
			case INFORMATIVITY_DIAGNOSTIVITY : 
				return new InformativityDiagnosticity();
			default : 
				return null;
		}
	}

}
