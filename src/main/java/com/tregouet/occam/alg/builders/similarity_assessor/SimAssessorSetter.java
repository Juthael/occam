package com.tregouet.occam.alg.builders.similarity_assessor;

import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.RepresentationBuilder;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;

public interface SimAssessorSetter {
	
	List<IContextObject> getContext();
	
	IConceptLattice getConceptLattice();
	
	Map<UnorderedPair<Integer, Integer>, IRepresentation> getDichotomies();
	
	Map<UnorderedPair<Integer, Integer>, IRepresentation> getDifferences();
	
	ISimilarityMetrics getSimilarityMetrics();
	
	public static ConceptLatticeBuilder conceptLatticeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}
	
	public static ClassificationBuilder classificationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getClassificationBuilder();
	}
	
	public static RepresentationBuilder representationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationBuilder();
	}

}
