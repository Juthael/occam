package com.tregouet.occam.cost_calculation.property_weighing;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.property_weighing.impl.Informativity;
import com.tregouet.occam.cost_calculation.property_weighing.impl.InformativityDiagnosticity;

public class PropertyWeigherFactory{
	
	private PropertyWeigherFactory() {
	}
	
	public static IPropertyWeigher apply(PropertyWeighingStrategy strategy) {
		switch (strategy) {
			case INFORMATIVITY_DIAGNOSTIVITY : 
				return new InformativityDiagnosticity();
			case INFORMATIVITY : 
				return new Informativity();
			default : 
				return null;
		}
	}

}
