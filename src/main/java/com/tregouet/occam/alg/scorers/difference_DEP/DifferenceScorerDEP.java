package com.tregouet.occam.alg.scorers.difference_DEP;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public interface DifferenceScorerDEP {

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
