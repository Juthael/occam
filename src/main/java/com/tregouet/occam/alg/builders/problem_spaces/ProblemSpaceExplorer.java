package com.tregouet.occam.alg.builders.problem_spaces;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface ProblemSpaceExplorer extends BiFunction<IProblemSpace, Integer, Set<IProblemSpace>> {
	
	ProblemSpaceExplorer setUp(List<IContextObject> context);
	
	IProblemSpace initialize();

}
