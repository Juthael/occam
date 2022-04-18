package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.alg.builders.representations.impl.FindEveryClassificationFirst;

public class RepresentationSortedSetBuilderFactory {

	public static final RepresentationSortedSetBuilderFactory INSTANCE = new RepresentationSortedSetBuilderFactory();

	private RepresentationSortedSetBuilderFactory() {
	}

	public RepresentationSortedSetBuilder apply(RepresentationSortedSetBuilderStrategy strategy) {
		switch (strategy) {
		case FIND_EVERY_CLASSIFICATION_FIRST:
			return FindEveryClassificationFirst.INSTANCE;
		default:
			return null;
		}

	}

}
