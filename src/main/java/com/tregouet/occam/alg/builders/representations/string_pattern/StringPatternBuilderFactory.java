package com.tregouet.occam.alg.builders.representations.string_pattern;

import com.tregouet.occam.alg.builders.representations.string_pattern.impl.RecursiveFraming;

public class StringPatternBuilderFactory {

	public static final StringPatternBuilderFactory INSTANCE = new StringPatternBuilderFactory();

	private StringPatternBuilderFactory() {
	}

	public StringPatternBuilder apply(StringPatternBuilderStrategy strategy) {
		switch (strategy) {
		case RECURSIVE_FRAMING:
			return new RecursiveFraming();
		default:
			return null;
		}
	}

}
