package com.tregouet.occam.alg.builders.problem_spaces;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.data.problem_spaces.ICategorizationProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentations;

public interface CategorizationProblemSpaceBuilder 
	extends BiFunction<
				 ICompleteRepresentations, 
				PartialRepresentationLateSetter, 
				ICategorizationProblemSpace> {

}
