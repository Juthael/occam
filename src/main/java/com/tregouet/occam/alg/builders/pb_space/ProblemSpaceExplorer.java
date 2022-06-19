package com.tregouet.occam.alg.builders.pb_space;

import java.util.Collection;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.expander.ProblemSpaceGraphExpander;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.ProblemSpaceGraphRestrictor;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

/**
 * Returns null if parameter is not the iD of some representation in the graph, and false if the
 * representation with this id is a dead-end
 * @author Gael Tregouet
 *
 */
public interface ProblemSpaceExplorer {
	
	Boolean develop(int representationID);
	
	Boolean restrictTo(Set<Integer> representationIDs);

	Set<Integer> getIDsOfRepresentationsWithIncompleteSorting();

	DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts();

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();

	ProblemSpaceExplorer initialize(Collection<IContextObject> context);

	public static ClassificationBuilder classificationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getClassificationBuilder();
	}

	public static ConceptLatticeBuilder conceptLatticeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}

	public static ConceptTreeGrower getConceptTreeGrower() {
		return BuildersAbstractFactory.INSTANCE.getConceptTreeGrower();
	}

	public static ProblemStateScorer getProblemStateScorer() {
		return ScorersAbstractFactory.INSTANCE.getProblemStateScorer();
	}
	
	public static ProblemTransitionWeigher getProblemTransitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getCategorizationTransitionWeigher();
	}

	public static RepresentationBuilder representationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationBuilder();
	}

	public static Sorting2StringConverter getSorting2StringConverter() {
		return FormattersAbstractFactory.INSTANCE.getSorting2StringConverter();
	}

	public static ProblemSpaceGraphExpander problemSpaceGraphExpander() {
		return BuildersAbstractFactory.INSTANCE.getProblemSpaceGraphExpander();
	}
	
	public static ProblemSpaceGraphRestrictor problemSpaceGraphRestrictor() {
		return BuildersAbstractFactory.INSTANCE.getProblemSpaceGraphRestrictor();
	}

}
