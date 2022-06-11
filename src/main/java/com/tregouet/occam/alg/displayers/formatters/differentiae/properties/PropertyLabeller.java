package com.tregouet.occam.alg.displayers.formatters.differentiae.properties;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.applications.ApplicationLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;

public interface PropertyLabeller extends Function<IProperty, String> {

	public static ApplicationLabeller applicationLabeller() {
		return FormattersAbstractFactory.INSTANCE.getApplicationLabeller();
	}

}
