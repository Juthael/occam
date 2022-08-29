package com.tregouet.occam.data.structures.representations.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.data.modules.sorting.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.structures.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.structures.representations.evaluation.tapes.impl.FactTape;
import com.tregouet.occam.data.structures.representations.transitions.IRepresentationTransitionFunction;

public class Representation implements IRepresentation {

	private static int nextID = IRepresentation.FIRST_ID;

	protected final int iD;
	protected IClassification classification;
	protected IFactEvaluator factEvaluator;
	protected IDescription description;
	protected final Set<IPartition> partitions;
	protected Double score = null;

	public Representation(IClassification classification, IDescription description, IFactEvaluator factEvaluator,
			Set<IPartition> partitions) {
		this.classification = classification;
		this.description = description;
		this.partitions = partitions;
		this.factEvaluator = factEvaluator;
		iD = nextID++;
	}

	@Override
	public Integer compareTo(IRepresentation other) {
		if (this.equals(other))
			return 0;
		if (this.partitions.containsAll(other.getPartitions()))
			return 1;
		if (other.getPartitions().containsAll(this.partitions))
			return -1;
		return null;
	}

	@Override
	public Set<IFact> enumerateMachineLanguage() {
		Set<IFact> trueFacts = new HashSet<>();
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			trueFacts.add(output.getTapeSet().getInputTape().getFact());
		}
		factEvaluator.reinitialize();
		return trueFacts;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Representation other = (Representation) obj;
		return Objects.equals(partitions, other.partitions);
	}

	@Override
	public boolean evaluate(IFact fact) {
		factEvaluator.input(new FactTape(fact));
		Set<IFactEvaluator> evaluations = factEvaluator.evaluate();
		for (IFactEvaluator evaluation : evaluations) {
			if (evaluation.accepts())
				return true;
		}
		return false;
	}

	@Override
	public IClassification getClassification() {
		return classification;
	}

	@Override
	public IDescription getDescription() {
		return description;
	}

	@Override
	public List<Integer> getExtentIDs(Integer conceptID) {
		return classification.getExtentIDs(conceptID);
	}

	@Override
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public Set<IConcept> getStates() {
		return classification.asGraph().vertexSet();
	}

	@Override
	public IRepresentationTransitionFunction getTransitionFunction() {
		return factEvaluator.getTransitionFunction();
	}

	@Override
	public int hashCode() {
		return Objects.hash(partitions);
	}

	@Override
	public int iD() {
		return iD;
	}

	@Override
	public boolean isExpandable() {
		return classification.isExpandable();
	}

	@Override
	public boolean isFullyDeveloped() {
		return classification.isFullyDeveloped();
	}

	@Override
	public Map<IConcept, Set<IFact>> mapAcceptStateToAcceptedWords() {
		Map<IConcept, Set<IFact>> acceptedState2AcceptedWords = new HashMap<>();
		Map<Integer, IConcept> accStID2AccSt = new HashMap<>();
		Map<Integer, Set<IFact>> accStID2Facts = new HashMap<>();
		for (IConcept acceptState : classification.getMostSpecificConcepts()) {
			int accStID = acceptState.iD();
			accStID2Facts.put(accStID, new HashSet<>());
			accStID2AccSt.put(accStID, acceptState);
		}
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			accStID2Facts.get(output.getActiveStateID()).add(output.getTapeSet().getInputTape().getFact());
		}
		for (Integer leafID : accStID2Facts.keySet()) {
			acceptedState2AcceptedWords.put(accStID2AccSt.get(leafID),
					accStID2Facts.get(leafID));
		}
		factEvaluator.reinitialize();
		return acceptedState2AcceptedWords;
	}

	@Override
	public Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts() {
		Map<Integer, Set<IFact>> accStateIDToFacts = new HashMap<>();
		for (IConcept acceptState : classification.getMostSpecificConcepts())
			accStateIDToFacts.put(acceptState.iD(), new HashSet<>());
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			accStateIDToFacts.get(output.getActiveStateID()).add(output.getTapeSet().getInputTape().getFact());
		}
		factEvaluator.reinitialize();
		return accStateIDToFacts;
	}

	@Override
	public Map<Integer, List<String>> mapParticularIDsToFactualDescription(FactDisplayer factDisplayer) {
		Map<Integer, List<String>> accStateID2FactualDesc = new HashMap<>();
		Map<Integer, Set<IFact>> accStateID2Facts = mapParticularIDsToAcceptedFacts();
		for (Entry<Integer, Set<IFact>> entry : accStateID2Facts.entrySet())
			accStateID2FactualDesc.put(entry.getKey(), new ArrayList<>(factDisplayer.apply(entry.getValue())));
		return accStateID2FactualDesc;
	}

	@Override
	public double score() {
		return score;
	}

	@Override
	public void setScore(double score) {
		this.score = score;
	}

	public static void initializeIDGenerator() {
		nextID = IRepresentation.FIRST_ID;
	}

}
