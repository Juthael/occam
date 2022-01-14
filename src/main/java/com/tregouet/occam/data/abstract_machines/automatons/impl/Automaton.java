package com.tregouet.occam.data.abstract_machines.automatons.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring.scores.functions.IFunctionScorer;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.transition_function_gen.IOntologist;
import com.tregouet.occam.data.abstract_machines.IFiniteAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.states.impl.State;
import com.tregouet.occam.data.abstract_machines.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transition_rules.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.ICostedTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.IReframerRule;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRules;
import com.tregouet.occam.data.abstract_machines.transition_rules.impl.Operator;
import com.tregouet.occam.data.abstract_machines.transition_rules.impl.ConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.impl.ReframerRule;
import com.tregouet.occam.data.denotations.IComplementaryDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguageDisplayer;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.tree_finder.data.Tree;

public class Automaton implements IAutomaton {

	private final List<IState> states;
	private final Set<IProduction> inputAlphabet;
	private final Set<AVariable> stackAlphabet;
	private final ITransitionFunction transitionFunction;
	private final IState startState;
	private final AVariable initialStackSymbol = null;
	private final List<IState> acceptStates;
	private final List<IState> objectStates;
	private DirectedMultigraph<IState, ITransitionRule> finiteAutomatonMultigraph = null;
	private SimpleDirectedGraph<IState, ITransitionRules> finiteAutomatonGraph = null;
	
	public Automaton(Tree<IDenotationSet, IIsA> denotationSets, Tree<IDenotation, IProduction> denotations) {
		ITransitionRule.initializeNameProvider();
		IConjunctiveTransition.initializeNameProvider();
		this.denotationSets = denotationSets;
		this.denotations = denotations;
		for (IDenotationSet denotationSet : denotationSets.vertexSet())
			denotationSetToState.put(denotationSet, new State(denotationSet));
		transitions.addAll(buildOperators(new ArrayList<>(denotations.edgeSet())));
		connectEmptyComplementaryDenotationSets();
		for (ITransitionRule transitionRule : transitions) {
			if (!conjunctiveTransitions.stream().anyMatch(t -> t.loadTransitionRule(transition)))
				conjunctiveTransitions.add(new ConjunctiveTransition(transitionRule));
		}
		prophyrianTree = IOntologist.getPorphyrianTree(this);
		setUpCostsAndScores();
	}

	public static String setDenotationsAsString(Set<IDenotation> denotations){
		StringBuilder sB = new StringBuilder();
		for (IDenotation denotation : denotations) {
			sB.append(denotation.toString() + System.lineSeparator());
		}
		sB.deleteCharAt(sB.length() - 1);
		return sB.toString();
	}

	private static List<List<Integer>> groupIndexesOfProductionsHandledByTheSameOperator(
			List<IProduction> productions) {
		List<List<Integer>> prodIndexesSets = new ArrayList<>();
		List<Integer> skipIdx = new ArrayList<>();	
		for (int i = 0 ; i < productions.size() ; i++) {
			if (!skipIdx.contains(i)) {
				List<Integer> sameOperatorProds = new ArrayList<>(Arrays.asList(new Integer[] {i}));
				IProduction iProduction = productions.get(i);
				for (int j = i+1 ; j < productions.size() ; j++) {
					if (!skipIdx.contains(j)) {
						IProduction jProduction = productions.get(j);
						if (iProduction.getSourceDenotationSet().equals(jProduction.getSourceDenotationSet())
								&& iProduction.getTargetDenotationSet().equals(jProduction.getTargetDenotationSet())
								&& (iProduction.getTarget().equals(jProduction.getTarget())
									|| new ArrayList<>(iProduction.getValues()).removeAll(jProduction.getValues()))) {
							sameOperatorProds.add(j);
							skipIdx.add(j);
						}
					}
				}
				prodIndexesSets.add(sameOperatorProds);
			}
		}
		return prodIndexesSets;
	}

	public List<IBasicOperator> buildOperators(List<IProduction> productions){
		List<IBasicOperator> operators = new ArrayList<>();
		List<List<Integer>> operatorProdsSets = groupIndexesOfProductionsHandledByTheSameOperator(productions);
		for (List<Integer> idxes : operatorProdsSets) {
			List<IProduction> operation = new ArrayList<>();
			IState activeState = null;
			IState nextState = null;
			for (int k = 0 ; k < idxes.size() ; k++) {
				if (k == 0) {
					IProduction kProduction = productions.get(idxes.get(k));
					activeState = denotationSetToState.get(kProduction.getSourceDenotationSet());
					nextState = denotationSetToState.get(kProduction.getTargetDenotationSet());
					operation.add(kProduction);
				}
				else operation.add(productions.get(idxes.get(k)));
			}
			operators.add(new Operator(activeState, operation, nextState));	
		}
		return operators;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automaton other = (Automaton) obj;
		if (transitions == null) {
			if (other.transitions != null)
				return false;
		} else if (!transitions.equals(other.transitions))
			return false;
		return true;
	}

	@Override
	public IState getAssociatedStateOf(IDenotationSet denotationSet) {
		return denotationSetToState.get(denotationSet);
	}

	@Override
	public IFiniteAutomaton getCompiler() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public List<IConjunctiveTransition> getConjunctiveTransitions() {
		return conjunctiveTransitions;
	}

	@Override
	public Double getCost() {
		return cost;
	}
	
	@Override
	public IDomainSpecificLanguageDisplayer getDomainSpecificLanguage() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph() {
		if (finiteAutomatonGraph == null) {
			finiteAutomatonGraph = new SimpleDirectedGraph<>(null, null, false);
			for (IState state : getStates())
				finiteAutomatonGraph.addVertex(state);
			for (IConjunctiveTransition operator : conjunctiveTransitions)
				finiteAutomatonGraph.addEdge(operator.getInputState(), operator.getOutputState(), operator);
		}
		return finiteAutomatonGraph;
	}

	@Override
	public DirectedMultigraph<IState, ITransitionRule> getFiniteAutomatonMultigraph() {
		if (finiteAutomatonMultigraph == null) {
			finiteAutomatonMultigraph = new DirectedMultigraph<>(null, null, false);
			for (IState state : getStates())
				finiteAutomatonMultigraph.addVertex(state);
			for (ITransitionRule operator : transitions)
				finiteAutomatonMultigraph.addEdge(operator.getInputState(), operator.getOutputState(), operator);			
		}
		return finiteAutomatonMultigraph;
	}

	@Override
	public Tree<IState, IGenusDifferentiaDefinition> getPorphyrianTree() {
		return prophyrianTree;
	}

	@Override
	public Double getScore() {
		return score;
	}

	@Override
	public ISimilarityScorer getSimilarityCalculator() {
		return similarityScorer;
	}

	@Override
	public List<IState> getStates() {
		return new ArrayList<>(denotationSetToState.values());
	}
	
	@Override
	public List<ICostedTransition> getTransitions() {
		return transitions;
	}

	@Override
	public Tree<IDenotationSet, IIsA> getTreeOfDenotationSets() {
		return denotationSets;
	}
	
	@Override
	public Tree<IDenotation, IProduction> getTreeOfDenotations(){
		return denotations;
	}
	
	@Override
	public Tree<IDenotation, IProduction> getTreeOfDenotationsWithNoBlankProduction(){
		DirectedAcyclicGraph<IDenotation, IProduction> restrictedDenotationTree = new DirectedAcyclicGraph<>(null, null, false);
		IDenotation root = denotations.getRoot();
		Set<IDenotation> leaves = new HashSet<>();
		List<IDenotation> topoOrderedSet = new ArrayList<>();
		Graphs.addAllVertices(restrictedDenotationTree, denotations.vertexSet());
		for (IProduction production : denotations.edgeSet()) {
			if (!production.isBlank() || production.isVariableSwitcher()) {
				restrictedDenotationTree.addEdge(production.getSource(), production.getTarget(), production);
			}
		}
		Set<IDenotation> unconnectedVertices = new HashSet<>();
		for (IDenotation vertex : restrictedDenotationTree.vertexSet()) {
			if (restrictedDenotationTree.inDegreeOf(vertex) == 0) {
				if (restrictedDenotationTree.outDegreeOf(vertex) == 0)
					unconnectedVertices.add(vertex);
				else leaves.add(vertex);
			}
		}
		TopologicalOrderIterator<IDenotation, IProduction> topoIte = new TopologicalOrderIterator<>(restrictedDenotationTree);
		topoIte.forEachRemaining(v -> topoOrderedSet.add(v));
		restrictedDenotationTree.removeAllVertices(unconnectedVertices);
		return new Tree<IDenotation, IProduction>(restrictedDenotationTree, root, leaves, topoOrderedSet);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((transitions == null) ? 0 : transitions.hashCode());
		return result;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;		
	}

	@Override
	public void setScore(double score) {
		this.score = score;		
	}

	@Override
	public void setSimilarityScorer(ISimilarityScorer similarityScorer) {
		this.similarityScorer = similarityScorer;
	}
	
	@Override
	public boolean validate(Predicate<IAutomaton> validator) {
		return validator.test(this);
	}

	private void connectEmptyComplementaryDenotationSets() {
		for (IDenotationSet denotationSet : denotationSets.vertexSet()) {
			if (denotationSet.isComplementary()) {
				IComplementaryDenotationSet complementaryDenotationSet = (IComplementaryDenotationSet) denotationSet;
				IState complementaryState = denotationSetToState.get(complementaryDenotationSet);
				int complementedStateID = complementaryDenotationSet.getComplemented().getID();
				IState successorState = 
						denotationSetToState.get(Graphs.successorListOf(denotationSets, complementaryDenotationSet).get(0));
				IReframerRule reframerRule = new ReframerRule(complementaryState, complementedStateID, successorState);
				transitions.add(reframerRule);
				if (!complementaryDenotationSet.containsDenotations()) {
					for (IDenotationSet predecessor : Graphs.predecessorListOf(denotationSets, denotationSet)) {
						IState connectedState = denotationSetToState.get(predecessor);
						IReframerRule connector = new ReframerRule(connectedState, complementaryState);
						transitions.add(connector);
					}
				}
			}
		}
	}
	
	private void setUpCostsAndScores() {
		CalculatorsAbstractFactory factory = CalculatorsAbstractFactory.INSTANCE;
		//transition costs
		ITransitionCoster transitionCoster = factory.getTransitionCoster();
		transitionCoster.setCosterParameters(this);
		for (ICostedTransition transition : transitions)
			transitionCoster.input(transition).setCost();
		//function cost
		IFunctionCoster transitionFunctionCoster = factory.getTransitionFunctionCoster();
		transitionFunctionCoster.input(this).setCost();
		//definition costs
		IDefinitionCoster definitionCoster = factory.getDefinitionCoster();
		definitionCoster.setCosterParameters(this);
		for (IGenusDifferentiaDefinition definition : prophyrianTree.edgeSet())
			definitionCoster.input(definition).setCost();
		//similarity scores
		ISimilarityScorer similarityScorer = factory.getSimilarityScorer();
		similarityScorer.input(this).setScore();
		//function score
		IFunctionScorer functionScorer = factory.getTransitionFunctionScorer();
		functionScorer.input(this).setScore();
	}

}

