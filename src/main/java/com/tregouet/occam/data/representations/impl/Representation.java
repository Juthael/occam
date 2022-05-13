package com.tregouet.occam.data.representations.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.evaluation.tapes.impl.FactTape;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class Representation implements IRepresentation {

	private static int nextID = 1;

	protected final int iD;
	protected InvertedTree<IConcept, IIsA> classification;
	protected IFactEvaluator factEvaluator;
	protected IDescription description;
	protected final Set<IPartition> partitions;
	protected DoubleScore score = null;

	public Representation(InvertedTree<IConcept, IIsA> classification, IDescription description, IFactEvaluator factEvaluator,
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
	public IDescription getDescription() {
		return description;
	}

	@Override
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public Set<IConcept> getStates() {
		return classification.vertexSet();
	}

	@Override
	public IRepresentationTransitionFunction getTransitionFunction() {
		return factEvaluator.getTransitionFunction();
	}

	@Override
	public InvertedTree<IConcept, IIsA> getTreeOfConcepts() {
		return classification;
	}

	@Override
	public int hashCode() {
		return Objects.hash(partitions);
	}

	@Override
	public int id() {
		return iD;
	}

	@Override
	public void initializeIDGenerator() {
		nextID = 1;
	}

	@Override
	public Map<IConcept, Set<IFact>> mapAcceptStateToAcceptedWords() {
		Map<IConcept, Set<IFact>> acceptStateToAcceptedWords = new HashMap<>();
		Map<Integer, IConcept> particularIDToParticular = new HashMap<>();
		Map<Integer, Set<IFact>> particularIDToFacts = new HashMap<>();
		for (IConcept particular : classification.getLeaves()) {
			int particularID = particular.iD();
			particularIDToFacts.put(particularID, new HashSet<>());
			particularIDToParticular.put(particularID, particular);
		}
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			particularIDToFacts.get(output.getActiveStateID()).add(output.getTapeSet().getInputTape().getFact());
		}
		for (Integer particularID : particularIDToFacts.keySet()) {
			acceptStateToAcceptedWords.put(particularIDToParticular.get(particularID),
					particularIDToFacts.get(particularID));
		}
		factEvaluator.reinitialize();
		return acceptStateToAcceptedWords;
	}

	@Override
	public Map<Integer, Set<IFact>> mapParticularIDsToAcceptedFacts() {
		Map<Integer, Set<IFact>> particularIDToFacts = new HashMap<>();
		for (IConcept particular : classification.getLeaves())
			particularIDToFacts.put(particular.iD(), new HashSet<>());
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			particularIDToFacts.get(output.getActiveStateID()).add(output.getTapeSet().getInputTape().getFact());
		}
		factEvaluator.reinitialize();
		return particularIDToFacts;
	}
	
	@Override
	public Map<Integer, List<String>> mapParticularIDsToFactualDescription(FactDisplayer factDisplayer) {
		Map<Integer, List<String>> particularIDToFactualDesc = new HashMap<>();
		Map<Integer, Set<IFact>> particularIDToFacts = mapParticularIDsToAcceptedFacts();
		for (Entry<Integer, Set<IFact>> entry : particularIDToFacts.entrySet())
			particularIDToFactualDesc.put(entry.getKey(), new ArrayList<>(factDisplayer.apply(entry.getValue())));
		return particularIDToFactualDesc;
	}

	@Override
	public DoubleScore score() {
		return score;
	}

	@Override
	public void setScore(DoubleScore score) {
		this.score = score;
	}

	@Override
	public Set<Integer> getExtent(Integer conceptID) {
		Set<Integer> extent = new HashSet<>();
		IConcept concept = null;
		Iterator<IConcept> conceptIte = classification.vertexSet().iterator();
		while (concept == null) {
			IConcept nextConcept = conceptIte.next();
			if (nextConcept.iD() == conceptID.intValue())
				concept = nextConcept;
		}
		for (IContextObject object : concept.getExtent())
			extent.add(object.iD());
		return extent;
	}

	@Override
	public boolean isGoalState() {
		for (IConcept concept : classification.getLeaves()) {
			if (concept.type() != ConceptType.PARTICULAR)
				return false;
		}
		return true;
	}

}
