package com.tregouet.occam.alg.builders.concept_lattices.denotations;

import com.tregouet.occam.alg.builders.concept_lattices.denotations.impl.MaxSymbolSubsequences;
import com.tregouet.occam.alg.builders.concept_lattices.denotations.impl.NoRedundancy;

public class DenotationBuilderFactory {

	public static final DenotationBuilderFactory INSTANCE = new DenotationBuilderFactory();

	private DenotationBuilderFactory() {
	}

	public DenotationBuilder apply(DenotationBuilderStrategy strategy) {
		switch (strategy) {
		case MAX_SYMBOL_SUBSEQUENCES:
			return new MaxSymbolSubsequences();
		case NO_REDUNDANCY:
			return new NoRedundancy();
		default:
			return null;
		}
	}

}
