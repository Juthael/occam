package com.tregouet.occam.alg.builders.comparator;

import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.comparator.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.RepresentationBuilder;
import com.tregouet.occam.data.modules.comparison.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public interface ComparatorSetter {

	ComparatorSetter accept(List<IContextObject> context);

	IConceptLattice getConceptLattice();

	List<IContextObject> getContext();

	Map<UnorderedPair<Integer, Integer>, IRepresentation> getDichotomies();

	Map<UnorderedPair<Integer, Integer>, IRepresentation> getDifferences();

	ISimilarityMetrics getSimilarityMetrics();

	public static ClassificationBuilder classificationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getClassificationBuilder();
	}

	public static ConceptLatticeBuilder conceptLatticeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}

	public static RepresentationBuilder representationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationBuilder();
	}

	public static SimilarityMetricsBuilder similarityMetricsBuilder() {
		return BuildersAbstractFactory.INSTANCE.getSimilarityMetricsBuilder();
	}

}
