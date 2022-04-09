package com.tregouet.occam.alg.builders.problem_spaces.partial_representations;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.impl.InferNullMembers;

public class PartialRepresentationLateSetterFactory {
	
	public static final PartialRepresentationLateSetterFactory INSTANCE = 
			new PartialRepresentationLateSetterFactory();
	
	private PartialRepresentationLateSetterFactory() {
	}
	
	public PartialRepresentationLateSetter apply(PartialRepresentationLateSetterStrategy strategy) {
		switch (strategy) {
			case INFER_NULL_MEMBERS : 
				return InferNullMembers.INSTANCE;
			default : 
				return null;
		}
	}

}
