package com.tregouet.occam.alg.displayers.formatters.differentiae.properties;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.ComputationLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface PropertyLabeller extends Function<IProperty, String> {

	public static ComputationLabeller computationLabeller() {
		return FormattersAbstractFactory.INSTANCE.getComputationLabeller();
	}

}
