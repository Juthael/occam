package com.tregouet.occam.alg.builders.pb_space;

import java.util.Collection;
import java.util.function.Function;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilder;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface ProblemSpaceExplorer extends Function<Integer, DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>> {
	
	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> initialize(Collection<IContextObject> context);
	
	public static ConceptLatticeBuilder getConceptLatticeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}
	
	public static ConceptTreeGrower getConceptTreeGrower() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptTreeGrower();
	}
	
	public static ProductionBuilder getProductionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice();
	}
	
	public static RepresentationBuilder getRepresentationBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getRepresentationBuilder();
	}

}
