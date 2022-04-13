package com.tregouet.occam.alg.displayers.differentiae.properties;

import com.tregouet.occam.alg.displayers.differentiae.properties.impl.AsLambdaShorterString;
import com.tregouet.occam.alg.displayers.differentiae.properties.impl.AsLambdaString;
import com.tregouet.occam.alg.displayers.differentiae.properties.impl.AsProductionString;

public class PropertyDisplayerFactory {
	
	public static final PropertyDisplayerFactory INSTANCE = new PropertyDisplayerFactory();
	
	private PropertyDisplayerFactory() {
	}
	
	public PropertyDisplayer apply(PropertyDisplayerStrategy strategy) {
		switch (strategy) {
			case AS_LAMBDA : 
				return AsLambdaString.INSTANCE;
			case AS_SHORTER_LAMBDA : 
				return AsLambdaShorterString.INSTANCE;
			case AS_PRODUCTIONS : 
				return AsProductionString.INSTANCE;
			default : 
				return null;
		}
	}

}
