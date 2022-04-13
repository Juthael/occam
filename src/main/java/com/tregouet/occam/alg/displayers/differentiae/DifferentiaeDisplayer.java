package com.tregouet.occam.alg.displayers.differentiae;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.DisplayersAbstractFactory;
import com.tregouet.occam.alg.displayers.differentiae.properties.PropertyDisplayer;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public interface DifferentiaeDisplayer extends Function<AbstractDifferentiae, String> {
	
	public static PropertyDisplayer getPropertyDisplayer() {
		return DisplayersAbstractFactory.INSTANCE.getPropertyDisplayer();
	}

}
