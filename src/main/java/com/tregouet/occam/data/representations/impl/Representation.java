package com.tregouet.occam.data.representations.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.head.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.head.impl.FactEvaluator;
import com.tregouet.occam.data.representations.evaluation.tapes.impl.FactTape;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class Representation implements IRepresentation {
	
	private static int nextID = 1;

	protected final int iD;
	protected InvertedTree<IConcept, IIsA> classification;
	protected IFactEvaluator factEvaluator = new FactEvaluator();
	protected IDescription description;
	protected final Set<IPartition> partitions;
	
	Representation(InvertedTree<IConcept, IIsA> classification, IDescription description, 
			IRepresentationTransitionFunction transitionFunction, Set<IPartition> partitions) {
		this.classification = classification;
		this.description = description;
		this.partitions = partitions;
		factEvaluator.set(transitionFunction);
		iD = nextID++;
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
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public IDescription getDescription() {
		return description;
	}

	@Override
	abstract public Set<ICategorizationGoalState> getReachableGoalStates();

	@Override
	public int id() {
		return iD;
	}

	@Override
	public void initializeIDGenerator() {
		nextID = 1;
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
	public Set<IFact> enumerateMachineLanguage() {
		Set<IFact> trueFacts = new HashSet<>();
		for (IFactEvaluator output : factEvaluator.factEnumerator()) {
			trueFacts.add(output.getTapeSet().getInputTape().getFact());
		}
		factEvaluator.reinitialize();
		return trueFacts;
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
			acceptStateToAcceptedWords.put(particularIDToParticular.get(particularID), particularIDToFacts.get(particularID));
		}
		factEvaluator.reinitialize();
		return acceptStateToAcceptedWords;
	}

	@Override
	public Integer compareTo(ICategorizationState other) {
		if (this.equals(other))
			return 0;
		if (this.partitions.containsAll(other.getPartitions()))
			return 1;
		if (other.getPartitions().containsAll(this.partitions))
			return -1;
		return null;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Representation other = (Representation) obj;
		return Objects.equals(partitions, other.partitions);
	}

}
