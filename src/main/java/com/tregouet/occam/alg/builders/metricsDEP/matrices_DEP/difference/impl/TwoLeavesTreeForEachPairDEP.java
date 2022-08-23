package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.DifferenceMatrixBuilderDEP;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public class TwoLeavesTreeForEachPairDEP implements DifferenceMatrixBuilderDEP {

	public static final TwoLeavesTreeForEachPairDEP INSTANCE = new TwoLeavesTreeForEachPairDEP();

	private TwoLeavesTreeForEachPairDEP() {
	}

	@Override
	public double[][] getDifferenceMatrix(IConceptLattice lattice) {
		List<Integer> particularIDs = new ArrayList<>();
		for (IConcept particular : lattice.getParticulars())
			particularIDs.add(particular.iD());
		particularIDs.sort((x, y) -> Integer.compare(x, y));
		int nbOfParticulars = particularIDs.size();
		double[][] differenceMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars - 1 ; i++) {
			for (int j = i + 1 ; j < nbOfParticulars ; j++) {
				differenceMatrix[i][j] =
						DifferenceMatrixBuilderDEP.differenceScorerDEP().score(particularIDs.get(i), particularIDs.get(j), lattice);
				differenceMatrix[j][i] = differenceMatrix[i][j];
			}
		}
		return differenceMatrix;
	}

}
