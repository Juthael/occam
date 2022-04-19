package com.tregouet.occam.alg.builders.representations.concept_lattices.denotations;

import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.impl.MaxSymbolSubsequences;

public class DenotationBuilderFactory {

	public static final DenotationBuilderFactory INSTANCE = new DenotationBuilderFactory();

	private DenotationBuilderFactory() {
	}

	public DenotationBuilder apply(DenotationBuilderStrategy strategy) {
		switch (strategy) {
		case MAX_SYMBOL_SUBSEQUENCES:
			return new MaxSymbolSubsequences();
		default:
			return null;
		}
	}

}
