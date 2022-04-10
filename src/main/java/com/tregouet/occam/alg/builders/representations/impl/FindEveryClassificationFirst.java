package com.tregouet.occam.alg.builders.representations.impl;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.impl.CompleteRepresentation;
import com.tregouet.occam.data.representations.impl.CompleteRepresentations;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class FindEveryClassificationFirst implements RepresentationSortedSetBuilder {
	
	public static final FindEveryClassificationFirst INSTANCE = new FindEveryClassificationFirst();
	
	private Integer maxSize = null;
	
	private FindEveryClassificationFirst() {
	}

	@Override
	public ICompleteRepresentations apply(Set<IContextObject> particulars) {
		SortedSet<ICompleteRepresentation> representations = new TreeSet<>();
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
			ICompleteRepresentation representation = new CompleteRepresentation(classification, description, transFunc, partitions);
			representation.setScore(RepresentationSortedSetBuilder.getRepresentationScorer().apply(representation));
			addAndTrimIfRequired(representation, representations);
		}
		return new CompleteRepresentations(conceptLattice, representations);
	}
	
	private void addAndTrimIfRequired(ICompleteRepresentation newRepresentation, 
			SortedSet<ICompleteRepresentation> representations) {
		representations.add(newRepresentation);
		if (maxSize != null & representations.size() > maxSize)
			representations.remove(representations.first());
	}

	@Override
	public RepresentationSortedSetBuilder setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
		return this;
	}

}
