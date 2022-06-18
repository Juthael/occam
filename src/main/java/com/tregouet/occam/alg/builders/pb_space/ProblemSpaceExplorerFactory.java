package com.tregouet.occam.alg.builders.pb_space;

import com.tregouet.occam.alg.builders.pb_space.impl.AutomaticallyExpandTrivialLeaves;
import com.tregouet.occam.alg.builders.pb_space.impl.NormalizeClassificationThenBuildProductions;
import com.tregouet.occam.alg.builders.pb_space.impl.RemoveMeaningless;
import com.tregouet.occam.alg.builders.pb_space.impl.RemoveUninformative;
import com.tregouet.occam.alg.builders.pb_space.impl.SparseReducer;

public class ProblemSpaceExplorerFactory {

	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();

	private ProblemSpaceExplorerFactory() {
	}

	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case REMOVE_MEANINGLESS :
			return new RemoveMeaningless();
		case REMOVE_UNINFORMATIVE :
			return new RemoveUninformative();
		case EXPAND_TRIVIAL_LEAVES :
			return new AutomaticallyExpandTrivialLeaves();
		case NORMALIZE_CLASS_THEN_BUILD :
			return new NormalizeClassificationThenBuildProductions();
		case SPARSE_REDUCER : 
			return new SparseReducer();
		default :
			return null;
		}
	}

}
