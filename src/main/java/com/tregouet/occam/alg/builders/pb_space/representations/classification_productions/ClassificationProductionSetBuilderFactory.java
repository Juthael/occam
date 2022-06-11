package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.impl.BuildFromScratch;

public class ClassificationProductionSetBuilderFactory {
	
	public static final ClassificationProductionSetBuilderFactory INSTANCE = 
			new ClassificationProductionSetBuilderFactory();
	
	private ClassificationProductionSetBuilderFactory() {
	}
	
	public ProductionsBuilder apply(ClassificationProductionSetBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_FROM_SCRATCH : 
			return BuildFromScratch.INSTANCE;
		default : 
			return null;
		}
	}

}
