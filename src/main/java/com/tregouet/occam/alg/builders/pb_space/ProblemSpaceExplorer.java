package com.tregouet.occam.alg.builders.pb_space;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.pb_space.pb_transitions.ProblemTransitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRanker;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

/**
 * Returns null if parameter is not the iD of some representation in the graph, and false if the 
 * representation with this id is a dead-end
 * @author Gael Tregouet
 *
 */
public interface ProblemSpaceExplorer extends Function<Integer, Boolean> {
	
	ProblemSpaceExplorer initialize(Collection<IContextObject> context);
	
	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();
	
	Set<Integer> getIDsOfRepresentationsWithIncompleteSorting();
	
	public static ConceptLatticeBuilder getConceptLatticeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}
	
	public static ConceptTreeGrower getConceptTreeGrower() {
		return BuildersAbstractFactory.INSTANCE.getConceptTreeGrower();
	}
	
	public static ProductionBuilder getProductionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice();
	}
	
	public static RepresentationBuilder getRepresentationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationBuilder();
	}
	
	public static ProblemTransitionBuilder getProblemTransitionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProblemTransitionBuilder();
	}

	public static ProblemTransitionWeigher getProblemTransitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getCategorizationTransitionWeigher();
	}

	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}

	public static Sorting2StringConverter getSorting2StringConverter() {
		return FormattersAbstractFactory.INSTANCE.getSorting2StringConverter();
	}
	
	public static ProblemTransitionRanker getProblemTransitionRanker() {
		return BuildersAbstractFactory.INSTANCE.getProblemTransitionRanker();
	}	

}
