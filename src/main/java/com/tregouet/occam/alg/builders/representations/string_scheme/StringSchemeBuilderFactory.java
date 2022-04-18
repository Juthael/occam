package com.tregouet.occam.alg.builders.representations.string_scheme;

import com.tregouet.occam.alg.builders.representations.string_scheme.impl.RecursiveFraming;

public class StringSchemeBuilderFactory {

	public static final StringSchemeBuilderFactory INSTANCE = new StringSchemeBuilderFactory();

	private StringSchemeBuilderFactory() {
	}

	public StringSchemeBuilder apply(StringSchemeBuilderStrategy strategy) {
		switch(strategy) {
			case RECURSIVE_FRAMING :
				return new RecursiveFraming();
			default :
				return null;
		}
	}

}
