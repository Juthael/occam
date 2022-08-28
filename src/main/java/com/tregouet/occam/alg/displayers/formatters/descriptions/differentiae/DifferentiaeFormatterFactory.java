package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.impl.ManageFormat;

public class DifferentiaeFormatterFactory {

	public static final DifferentiaeFormatterFactory INSTANCE = new DifferentiaeFormatterFactory();

	private DifferentiaeFormatterFactory() {
	}

	public DifferentiaeFormatter apply(DifferentiaeFormatterStrategy strategy) {
		switch (strategy) {
		case MANAGE_FORMAT :
			return ManageFormat.INSTANCE;
		default :
			return null;
		}
	}

}
