package com.tregouet.occam.alg.builders.problem_spaces;

import com.tregouet.occam.alg.builders.problem_spaces.impl.NoDumbState;

public class ProblemSpaceExplorerFactory {
	
	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();
	
	private ProblemSpaceExplorerFactory() {
	}
	
	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case NO_DUMB_STATE : 
			return new NoDumbState();
		default : 
			return null;
		}
	}

}
