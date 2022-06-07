package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.impl.BuildFromScratch;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.impl.FilterLatticeProductions;

public class ClassificationProductionSetBuilderFactory {
	
	public static final ClassificationProductionSetBuilderFactory INSTANCE = 
			new ClassificationProductionSetBuilderFactory();
	
	private ClassificationProductionSetBuilderFactory() {
	}
	
	public ClassificationProductionSetBuilder apply(ClassificationProductionSetBuilderStrategy strategy) {
		switch (strategy) {
		case FILTER_UPDATE_REDUCE_SET : 
			return new FilterLatticeProductions();
		case BUILD_FROM_SCRATCH : 
			return BuildFromScratch.INSTANCE;
		default : 
			return null;
		}
	}

}
