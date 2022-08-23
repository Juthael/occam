package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.difference_DEP.DifferenceScorerDEP;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public interface DifferenceMatrixBuilderDEP {

	double[][] getDifferenceMatrix(IConceptLattice lattice);

	public static DifferenceScorerDEP differenceScorerDEP() {
		return ScorersAbstractFactory.INSTANCE.getDifferenceScorerDEP();
	}

}
