package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.alg.builders.representations.impl.BuildTreeSpecificSetOfProductions;

public class RepresentationBuilderFactory {

	public static final RepresentationBuilderFactory INSTANCE = new RepresentationBuilderFactory();

	private RepresentationBuilderFactory() {
	}

	public RepresentationBuilder apply(RepresentationBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_TREE_SPECIFIC_PRODUCTION_SET :
			return BuildTreeSpecificSetOfProductions.INSTANCE;
		default :
			return null;
		}
	}

}
