package com.tregouet.occam.alg.scorers.difference;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;

public interface DifferenceScorer {

	double score(int conceptID1, int conceptID2, IConceptLattice lattice);

	public static ClassificationBuilder classificationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getClassificationBuilder();
	}

	public static DescriptionBuilder descriptionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDescriptionBuilder();
	}

	public static ProductionSetBuilder productionSetBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProductionSetBuilder();
	}

}
