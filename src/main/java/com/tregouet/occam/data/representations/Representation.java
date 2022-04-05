package com.tregouet.occam.data.representations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.head.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.head.impl.FactEvaluator;
import com.tregouet.occam.data.representations.evaluation.tapes.impl.FactTape;
import com.tregouet.occam.data.representations.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class Representation implements IRepresentation {

	private final InvertedTree<IConcept, IIsA> classification;
	private final IFactEvaluator factEvaluator = new FactEvaluator();
	private final IDescription description;
	private final Set<IPartition> partitions;
	private LexicographicScore score = null;
	
	public Representation(InvertedTree<IConcept, IIsA> classification, IDescription description, 
			Set<IPartition> partitions) {
		this.classification = classification;
		this.description = description;
		this.partitions = partitions;
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
	public Map<IConcept, Set<IFact>> acceptStateToAcceptedWords() {
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
	public Map<IConcept, Set<IFact>> anyStateToAcceptedWords() {
		// NOT IMPLEMENTED YET
		return null;
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
	public IDescription getContextDescription() {
		return description;
	}

	@Override
	public InvertedTree<IConcept, IIsA> getTreeOfConcepts() {
		return classification;
	}

	@Override
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public void setScore(LexicographicScore score) {
		this.score = score;
	}

	@Override
	public LexicographicScore score() {
		return score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, classification);
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
		return Objects.equals(description, other.description) && Objects.equals(classification, other.classification);
	}

	@Override
	public int compareTo(IRepresentation other) {
		int scoreComparison = this.score.compareTo(other.score());
		if (scoreComparison == 0 && !this.equals(other))
			return System.identityHashCode(this) - System.identityHashCode(other);
		return scoreComparison;
	}

}
