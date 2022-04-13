package com.tregouet.occam.alg.displayers.differentiae;

import com.tregouet.occam.alg.displayers.differentiae.impl.PropertiesThenWeight;

public class DifferentiaeDisplayerFactory {
	
	public static final DifferentiaeDisplayerFactory INSTANCE = new DifferentiaeDisplayerFactory();
	
	private DifferentiaeDisplayerFactory() {
	}
	
	public DifferentiaeDisplayer apply(DifferentiaeDisplayerStrategy strategy) {
		switch (strategy) {
			case PROPERTIES_THEN_WEIGHT:  
				return PropertiesThenWeight.INSTANCE;
			default : 
				return null;
		}
	}

}
