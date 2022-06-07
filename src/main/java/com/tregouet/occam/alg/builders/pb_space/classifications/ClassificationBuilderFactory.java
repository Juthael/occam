package com.tregouet.occam.alg.builders.pb_space.classifications;

import com.tregouet.occam.alg.builders.pb_space.classifications.impl.BuildParametersThenInstantiate;

public class ClassificationBuilderFactory {
	
	public static final ClassificationBuilderFactory INSTANCE = new ClassificationBuilderFactory();
	
	private ClassificationBuilderFactory() {
	}
	
	public ClassificationBuilder apply(ClassificationBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_PARAM_THEN_INST : 
			return BuildParametersThenInstantiate.INSTANCE;
		default : 
			return null;
		}
	}

}
