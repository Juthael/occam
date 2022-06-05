package com.tregouet.occam.alg.builders.pb_space.representations;

import com.tregouet.occam.alg.builders.pb_space.representations.impl.FirstBuildTreeSpecificSetOfProductions;

public class RepresentationBuilderFactory {
	
	public static final RepresentationBuilderFactory INSTANCE = new RepresentationBuilderFactory();
	
	private RepresentationBuilderFactory() {
	}
	
	public RepresentationBuilder apply(RepresentationBuilderStrategy strategy) {
		switch (strategy) {
		case TREE_SPECIFIC_PRODUCTION_SET_FIRST : 
			return new FirstBuildTreeSpecificSetOfProductions();
		default : 
			return null;
		}
	}

}
