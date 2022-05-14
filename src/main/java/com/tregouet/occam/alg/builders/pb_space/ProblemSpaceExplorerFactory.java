package com.tregouet.occam.alg.builders.pb_space;

import com.tregouet.occam.alg.builders.pb_space.impl.RebuildFromScratch;

public class ProblemSpaceExplorerFactory {
	
	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();
	
	private ProblemSpaceExplorerFactory() {
	}
	
	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case WITH_CLUSTERING_COMES_MEANING : 
			return new RebuildFromScratch();
		default : 
			return null;
		}
	}

}
