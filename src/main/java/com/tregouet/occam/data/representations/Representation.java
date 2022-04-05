package com.tregouet.occam.data.representations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.head.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.tapes.impl.FactTape;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class Representation implements IRepresentation<LexicographicScore> {

	private final Tree<IConcept, IIsA> treeOfConcepts;
	private final IFactEvaluator factEvaluator;
	private final IDescription description;
	private final Set<IPartition> partitions;
	private LexicographicScore score = null;
	
	public Representation(Tree<IConcept, IIsA> treeOfConcepts, IFactEvaluator factEvaluator, 
			IDescription description, Set<IPartition> partitions) {
		this.treeOfConcepts = treeOfConcepts;
		this.factEvaluator = factEvaluator;
		this.description = description;
		this.partitions = partitions;
	}
	
	@Override
	public Set<IConcept> getStates() {
		return treeOfConcepts.vertexSet();
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
		for (IConcept particular : treeOfConcepts.getLeaves()) {
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
		for (IConcept particular : treeOfConcepts.getLeaves())
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
	public Tree<IConcept, IIsA> getTreeOfConcepts() {
		return treeOfConcepts;
	}

	@Override
	public LexicographicScore getScore() {
		return score;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(IRepresentation<LexicographicScore> o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
