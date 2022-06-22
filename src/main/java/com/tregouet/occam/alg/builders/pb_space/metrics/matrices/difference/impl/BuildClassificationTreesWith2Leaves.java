package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.DifferenceMatrixBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;

public class BuildClassificationTreesWith2Leaves implements DifferenceMatrixBuilder {

	@Override
	public double[][] apply(IConceptLattice lattice) {
		List<Integer> particularIDs = new ArrayList<>();
		for (IConcept particular : lattice.getParticulars())
			particularIDs.add(particular.iD());
		particularIDs.sort((x, y) -> Integer.compare(x, y));
		int nbOfParticulars = particularIDs.size();
		double[][] differenceMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars - 1 ; i++) {
			for (int j = i + 1 ; i < nbOfParticulars ; j++) {
				differenceMatrix[i][j] = 
						DifferenceMatrixBuilder.differenceScorer().score(particularIDs.get(i), particularIDs.get(j), lattice);
				differenceMatrix[j][i] = differenceMatrix[i][j];
			}
		}
		return differenceMatrix;
	}

}
