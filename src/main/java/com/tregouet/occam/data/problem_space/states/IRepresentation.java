package com.tregouet.occam.data.problem_space.states;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.data.logical_structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.impl.Representation;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public interface IRepresentation extends
		IPushdownAutomaton<
			IConcept,
			IAbstractionApplication,
			IFact,
			IBindings,
			IConceptTransitionIC,
			IConceptTransitionOIC,
			IConceptTransition,
			IRepresentationTransitionFunction>,
		PartiallyComparable<IRepresentation> {

	public static final int FIRST_ID = 1;

	@Override
	boolean equals(Object o);

	IClassification getClassification();

	IDescription getDescription();

	List<Integer> getExtentIDs(Integer conceptID);

	Set<IPartition> getPartitions();

	double[][] getSimilarityMatrix();

	@Override
	int hashCode();

	int iD();

	boolean isFullyDeveloped();

	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();

	Map<Integer, List<String>> mapParticularIDsToFactualDescription(FactDisplayer factDisplayer);

	double score();

	void setScore(double score);

	public static void initializeIDGenerator() {
		Representation.initializeIDGenerator();
	}

}
