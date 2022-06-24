package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.difference.DifferenceScorer;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;

public interface DifferenceMatrixBuilder {
	
	double[][] getDifferenceMatrix(IConceptLattice lattice);
	
	public static DifferenceScorer differenceScorer() {
		return ScorersAbstractFactory.INSTANCE.getDifferenceScorer();
	}

}
