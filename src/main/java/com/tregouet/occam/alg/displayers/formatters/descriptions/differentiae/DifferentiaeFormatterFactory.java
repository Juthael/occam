package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.impl.ManageDiffFormat;

public class DifferentiaeFormatterFactory {

	public static final DifferentiaeFormatterFactory INSTANCE = new DifferentiaeFormatterFactory();

	private DifferentiaeFormatterFactory() {
	}

	public DifferentiaeFormatter apply(DifferentiaeFormatterStrategy strategy) {
		switch (strategy) {
		case MANAGE_DIFF_FORMAT :
			return ManageDiffFormat.INSTANCE;
		default :
			return null;
		}
	}

}
