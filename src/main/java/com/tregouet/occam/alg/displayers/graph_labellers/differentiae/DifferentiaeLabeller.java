package com.tregouet.occam.alg.displayers.graph_labellers.differentiae;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.graph_labellers.LabellersAbstractFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public interface DifferentiaeLabeller extends Function<AbstractDifferentiae, String> {

	public static PropertyLabeller getPropertyDisplayer() {
		return LabellersAbstractFactory.INSTANCE.getPropertyDisplayer();
	}

}
