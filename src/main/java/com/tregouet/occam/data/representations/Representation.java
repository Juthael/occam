package com.tregouet.occam.data.representations;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.head.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactAsTape;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class Representation implements IRepresentation<LexicographicScore> {

	private final Tree<IConcept, IIsA> treeOfConcepts;
	private final IFactEvaluator factEvaluator;
	private final IDescription description;
	private LexicographicScore score = null;
	
	public Representation(Tree<IConcept, IIsA> treeOfConcepts, IFactEvaluator factEvaluator, 
			IDescription description) {
		this.treeOfConcepts = treeOfConcepts;
		this.factEvaluator = factEvaluator;
		this.description = description;
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
	public boolean evaluate(IFactAsTape factAsTape) {
		factEvaluator.input(factAsTape);
		Set<IFactEvaluator> evaluations = factEvaluator.evaluate();
		for (IFactEvaluator evaluation : evaluations) {
			if (evaluation.accepts())
				return true;
		}
		return false;
	}

	@Override
	public Set<IFactAsTape> enumerateMachineLanguage() {
		Set<IFactAsTape> trueFacts = new HashSet<>();
		for (IFactEvaluator output : factEvaluator.generateEverySuccessfulEvaluation()) {
			trueFacts.add(output.getTapeSet().getInputTape());
		}
		factEvaluator.reinitialize();
		return trueFacts;
	}

	@Override
	public Map<IConcept, Set<IFactAsTape>> acceptStateToAcceptedWords() {
		Map<IConcept, Set<IFactAsTape>> acceptStateToAcceptedWords = new HashMap<>();
		Map<Integer, IConcept> particularIDToParticular = new HashMap<>();
		Map<Integer, Set<IFactAsTape>> particularIDToFacts = new HashMap<>();
		for (IConcept particular : treeOfConcepts.getLeaves()) {
			int particularID = particular.iD();
			particularIDToFacts.put(particularID, new HashSet<>());
			particularIDToParticular.put(particularID, particular);
		}
		Set<IFactEvaluator> printers = factEvaluator.printer();
		for (IFactEvaluator printer : printers) {
			particularIDToFacts.get(printer.getActiveStateID()).add(printer.getTapeSet().getInputTape());
		}
		for (Integer particularID : particularIDToFacts.keySet()) {
			acceptStateToAcceptedWords.put(particularIDToParticular.get(particularID), particularIDToFacts.get(particularID));
		}
		factEvaluator.reinitialize();
		return acceptStateToAcceptedWords;
	}

	@Override
	public Map<IConcept, Set<IFactAsTape>> anyStateToAcceptedWords() {
		// NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public Map<Integer, Set<IFactAsTape>> mapParticularIDsToAcceptedFacts() {
		Map<Integer, Set<IFactAsTape>> particularIDToParticular = new HashMap<>();
		for (IConcept particular : treeOfConcepts.getLeaves())
			particularIDToParticular.put(particular.iD(), new HashSet<>());
		
	}

	@Override
	public IDescription getContextDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree<IConcept, IIsA> getTreeOfConcepts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexicographicScore getScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPartition> getPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScore(LexicographicScore score) {
		// TODO Auto-generated method stub

	}

}
