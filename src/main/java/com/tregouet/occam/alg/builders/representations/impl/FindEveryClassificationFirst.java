package com.tregouet.occam.alg.builders.representations.impl;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilder;
import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.IRepresentations;
import com.tregouet.occam.data.representations.Representation;
import com.tregouet.occam.data.representations.Representations;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class FindEveryClassificationFirst implements RepresentationSortedSetBuilder {
	
	public static final FindEveryClassificationFirst INSTANCE = new FindEveryClassificationFirst();
	
	private FindEveryClassificationFirst() {
	}

	@Override
	public IRepresentations apply(Set<IContextObject> particulars) {
		SortedSet<IRepresentation> representations = new TreeSet<>();
		IConceptLattice conceptLattice = 
				RepresentationSortedSetBuilder.getConceptLatticeBuilder().apply(particulars);
		Set<InvertedTree<IConcept, IIsA>> classifications = 
				RepresentationSortedSetBuilder.getConceptTreeBuilder().apply(conceptLattice);
		Set<IContextualizedProduction> productions = 
				RepresentationSortedSetBuilder.getProductionBuilder().apply(conceptLattice);
		for (InvertedTree<IConcept, IIsA> classification : classifications) {
			IRepresentationTransitionFunction transFunc = 
					RepresentationSortedSetBuilder.getTransFuncBuilder().apply(classification, productions);
			IDescription description = 
					RepresentationSortedSetBuilder.getDescriptionBuilder().apply(transFunc);
			Set<IPartition> partitions = RepresentationSortedSetBuilder.getPartitionBuilder().apply(classification, description);
			IRepresentation representation = new Representation(classification, description, transFunc, partitions);
			representation.setScore(RepresentationSortedSetBuilder.getRepresentationScorer().apply(representation));
			representations.add(representation);
		}
		return new Representations(conceptLattice, representations);
	}

}
