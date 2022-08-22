package com.tregouet.occam.data.representations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.impl.Representation;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.structures.automata.IPushdownAutomaton;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.partial_order.PartiallyComparable;

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

	boolean isExpandable();

	boolean isFullyDeveloped();

	Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts();

	Map<Integer, List<String>> mapParticularIDsToFactualDescription(FactDisplayer factDisplayer);

	double score();

	void setScore(double score);

	public static void initializeIDGenerator() {
		Representation.initializeIDGenerator();
	}

}
