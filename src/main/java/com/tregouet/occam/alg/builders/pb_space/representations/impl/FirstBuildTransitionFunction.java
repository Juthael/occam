package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.impl.Representation;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class FirstBuildTransitionFunction implements RepresentationBuilder {
	
	private IConceptLattice conceptLattice;
	private Set<IContextualizedProduction> productions;
	private List<Integer> particularIDs;
	
	public FirstBuildTransitionFunction() {
	}

	@Override
	public IRepresentation apply(InvertedTree<IConcept, IIsA> conceptTree) {
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.getTransFuncBuilder()
				.apply(conceptTree, productions);
		IFactEvaluator factEvaluator = RepresentationBuilder.getFactEvaluatorBuilder().apply(transFunc);
		Map<Integer, Integer> particularID2MostSpecificConceptID = mapContextParticularID2MostSpecificConceptID(conceptTree);
		IDescription description = RepresentationBuilder.getDescriptionBuilder().apply(transFunc, particularID2MostSpecificConceptID);
		Set<IPartition> partitions = RepresentationBuilder.getPartitionBuilder().apply(description, conceptTree);
		IRepresentation representation = new Representation(conceptTree, description, factEvaluator, partitions);
		return representation;
	}

	@Override
	public FirstBuildTransitionFunction setUp(IConceptLattice conceptLattice, Set<IContextualizedProduction> productions) {
		this.conceptLattice = conceptLattice;
		this.productions = productions;
		particularIDs = new ArrayList<>();
		for (IConcept leaf : conceptLattice.getOntologicalUpperSemilattice().getLeaves())
			particularIDs.add(leaf.iD());
		return this;
	}
	
	private Map<Integer, Integer> mapContextParticularID2MostSpecificConceptID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> particularID2MostSpecificConceptID = new HashMap<>();
		for (IConcept particular : conceptLattice.getOntologicalUpperSemilattice().getLeaves())
			particularID2MostSpecificConceptID.put(particular.iD(), mostSpecificConceptInTree(particular, conceptTree));
		return particularID2MostSpecificConceptID;
	}
	
	private Integer mostSpecificConceptInTree(IConcept particular, InvertedTree<IConcept, IIsA> conceptTree) {
		if (conceptTree.containsVertex(particular))
			return particular.iD();
		Integer particularID = particular.iD();
		for (IConcept leaf : conceptTree.getLeaves()) {
			if (leaf.getExtentIDs().contains(particularID))
				return leaf.iD();
		}
		return null; //never happens
	}	

}
