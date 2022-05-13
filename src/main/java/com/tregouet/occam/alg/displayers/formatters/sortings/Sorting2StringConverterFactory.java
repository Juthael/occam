package com.tregouet.occam.alg.displayers.formatters.sortings;

import com.tregouet.occam.alg.displayers.formatters.sortings.impl.RecursiveFraming;

public class Sorting2StringConverterFactory {

	public static final Sorting2StringConverterFactory INSTANCE = new Sorting2StringConverterFactory();

	private Sorting2StringConverterFactory() {
	}

	public Sorting2StringConverter apply(Sorting2StringConverterStrategy strategy) {
		switch (strategy) {
		case RECURSIVE_FRAMING:
			return new RecursiveFraming();
		default:
			return null;
		}
	}

}
