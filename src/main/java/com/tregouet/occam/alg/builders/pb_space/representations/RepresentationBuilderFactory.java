package com.tregouet.occam.alg.builders.pb_space.representations;

import com.tregouet.occam.alg.builders.pb_space.representations.impl.BuildTreeSpecificSetOfProductions;
import com.tregouet.occam.alg.builders.pb_space.representations.impl.FilterTreeSpecificSetOfProductions;

public class RepresentationBuilderFactory {
	
	public static final RepresentationBuilderFactory INSTANCE = new RepresentationBuilderFactory();
	
	private RepresentationBuilderFactory() {
	}
	
	public RepresentationBuilder apply(RepresentationBuilderStrategy strategy) {
		switch (strategy) {
		case FILTER_TREE_SPECIFIC_PRODUCTION_SET : 
			return new FilterTreeSpecificSetOfProductions();
		case BUILD_TREE_SPECIFIC_PRODUCTION_SET :
			return BuildTreeSpecificSetOfProductions.INSTANCE;
		default : 
			return null;
		}
	}

}
