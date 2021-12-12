package com.tregouet.occam.data.abstract_machines.functions.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.abstract_machines.IFiniteAutomaton;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.TransitionFunctionGraphType;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.states.impl.State;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.abstract_machines.transitions.impl.BasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.impl.ConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.impl.Reframer;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguage;
import com.tregouet.tree_finder.data.Tree;

public class TransitionFunction implements ITransitionFunction {

	private final IClassification classification;
	private final Map<IConcept, IState> categoryToState = new HashMap<>();
	private final List<ITransition> transitions = new ArrayList<>();
	private final List<IConjunctiveTransition> conjunctiveTransitions = new ArrayList<>();
	private DirectedMultigraph<IState, ITransition> finiteAutomatonMultigraph = null;
	private SimpleDirectedGraph<IState, IConjunctiveTransition> finiteAutomatonGraph = null;
	
	public TransitionFunction(IClassification classification, Tree<IIntentAttribute, IProduction> constructs) {
		ITransition.initializeNameProvider();
		IConjunctiveTransition.initializeNameProvider();
		this.classification = classification;
		for (IConcept concept : classification.getClassificationTree().vertexSet())
			categoryToState.put(concept, new State(concept));
		transitions.addAll(buildOperators(new ArrayList<>(constructs.edgeSet())));
		transitions.addAll(buildReframers());
		for (ITransition transition : transitions) {
			if (!conjunctiveTransitions.stream().anyMatch(t -> t.addTransition(transition)))
				conjunctiveTransitions.add(new ConjunctiveTransition(transition));
		}
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
						if (iProduction.getSourceCategory().equals(jProduction.getSourceCategory())
								&& iProduction.getTargetCategory().equals(jProduction.getTargetCategory())
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

	private static String operatorAsString(ITransition transition) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		if (!transition.isBlank()) {
			if (transition instanceof IConjunctiveTransition) {
				IConjunctiveTransition conjTrans = (IConjunctiveTransition) transition;
				IReframer reframer = conjTrans.getReframer();
				if (reframer != null && !reframer.isBlank())
					sB.append("FRAME : " + reframer.getReframer() + nL);
				for (IBasicOperator operator : conjTrans.getOperators()) {
					sB.append(operatorAsString(operator) + nL);
				}
			}
			else if (transition instanceof IBasicOperator) {
				IOperator operator = (IOperator) transition;
				sB.append(operator.getName() + " : ");
				List<IProduction> productions = operator.operation();
				for (int i = 0 ; i < productions.size() ; i++) {
					sB.append(productions.get(i).toString());
					if (i < productions.size() - 1)
						sB.append(nL);
				}
			}
			else if (transition instanceof IReframer) {
				IReframer reframer = (IReframer) transition;
				sB.append(reframer.getName() + " : " + reframer.getReframer() + nL);
			}
		}
		return sB.toString();
	}

	public static String setIntentsAsString(Set<IIntentAttribute> attributes){
		StringBuilder sB = new StringBuilder();
		for (IIntentAttribute att : attributes) {
			sB.append(att.toString() + System.lineSeparator());
		}
		sB.deleteCharAt(sB.length() - 1);
		return sB.toString();
	}

	public List<IOperator> buildOperators(List<IProduction> productions){
		List<IOperator> operators = new ArrayList<>();
		List<List<Integer>> operatorProdsSets = groupIndexesOfProductionsHandledByTheSameOperator(productions);
		for (List<Integer> idxes : operatorProdsSets) {
			List<IProduction> operation = new ArrayList<>();
			IState activeState = null;
			IState nextState = null;
			for (int k = 0 ; k < idxes.size() ; k++) {
				if (k == 0) {
					IProduction kProduction = productions.get(idxes.get(k));
					activeState = categoryToState.get(kProduction.getSourceCategory());
					nextState = categoryToState.get(kProduction.getTargetCategory());
					operation.add(kProduction);
				}
				else operation.add(productions.get(idxes.get(k)));
			}
			operators.add(new BasicOperator(activeState, operation, nextState));	
		}
		return operators;
	}

	@Override
	public int compareTo(ITransitionFunction other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		//to prevent loss of elements in TreeSet
		if (this.equals(other))
			return 0;
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransitionFunction other = (TransitionFunction) obj;
		if (Double.doubleToLongBits(getCoherenceScore()) != Double.doubleToLongBits(other.getCoherenceScore()))
			return false;
		if (transitions == null) {
			if (other.transitions != null)
				return false;
		} else if (!transitions.equals(other.transitions))
			return false;
		return true;
	}
	
	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		return classification.getAsymmetricalSimilarityMatrix();
	}

	@Override
	public Tree<IConcept, IsA> getCategoryTree() {
		return classification.getClassificationTree();
	}

	@Override
	public String getCategoryTreeAsDOTFile() {
		DOTExporter<IConcept,IsA> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(classification.getClassificationTree(), writer);
		return writer.toString();
	}

	@Override
	public double getCoherenceScore() {
		return classification.getCoherenceScore();
	}
	
	@Override
	public IFiniteAutomaton getCompiler() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public Map<Integer, Double> getConceptualCoherenceMap() {
		return classification.getConceptualCoherenceMap();
	}

	@Override
	public List<IConjunctiveTransition> getConjunctiveTransitions() {
		return conjunctiveTransitions;
	}

	@Override
	public IDomainSpecificLanguage getDomainSpecificLanguage() {
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
				finiteAutomatonGraph.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
		}
		return finiteAutomatonGraph;
	}

	@Override
	public DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph() {
		if (finiteAutomatonMultigraph == null) {
			finiteAutomatonMultigraph = new DirectedMultigraph<>(null, null, false);
			for (IState state : getStates())
				finiteAutomatonMultigraph.addVertex(state);
			for (ITransition operator : transitions)
				finiteAutomatonMultigraph.addEdge(operator.getOperatingState(), operator.getNextState(), operator);			
		}
		return finiteAutomatonMultigraph;
	}

	@Override
	public ISimilarityCalculator getSimilarityCalculator() {
		return classification.getSimilarityCalculator();
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return classification.getSimilarityMatrix();
	}

	@Override
	public List<IState> getStates() {
		return new ArrayList<>(categoryToState.values());
	}

	@Override
	public String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType) {
		String dOTFile = null;
		switch (graphType) {
			case FINITE_AUTOMATON_MULTIGRAPH : 
				DOTExporter<IState,ITransition> multigraphExporter = new DOTExporter<>();
				multigraphExporter.setGraphAttributeProvider(() -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("rankdir", DefaultAttribute.createAttribute("BT"));
					return map;
				});
				multigraphExporter.setVertexAttributeProvider((s) -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("label", DefaultAttribute.createAttribute(Integer.toString(s.getStateID())));
					return map;
				});
				multigraphExporter.setEdgeAttributeProvider((o) -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("label", DefaultAttribute.createAttribute(operatorAsString(o)));
					return map;
				});		
				Writer writer = new StringWriter();
				DirectedMultigraph<IState, ITransition> stateMachine = new DirectedMultigraph<>(null, null, false);
				for (IState state : getStates())
					stateMachine.addVertex(state);
				for (ITransition transition : transitions)
					stateMachine.addEdge(transition.getOperatingState(), transition.getNextState(), transition);
				multigraphExporter.exportGraph(stateMachine, writer);
				dOTFile = writer.toString();
				break;
			case FINITE_AUTOMATON : 
				DOTExporter<IState,IConjunctiveTransition> simpleGraphExporter = new DOTExporter<>();
				simpleGraphExporter.setGraphAttributeProvider(() -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("rankdir", DefaultAttribute.createAttribute("BT"));
					return map;
				});
				simpleGraphExporter.setVertexAttributeProvider((s) -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("label", DefaultAttribute.createAttribute(Integer.toString(s.getStateID())));
					return map;
				});
				simpleGraphExporter.setEdgeAttributeProvider((o) -> {
					Map<String, Attribute> map = new LinkedHashMap<>();
					map.put("label", DefaultAttribute.createAttribute(operatorAsString(o)));
					return map;
				});		
				Writer simpleGraphWriter = new StringWriter();
				SimpleDirectedGraph<IState, IConjunctiveTransition> simpleGraph = 
						new SimpleDirectedGraph<>(null, null, false);
				for (IState state : getStates())
					simpleGraph.addVertex(state);
				for (IConjunctiveTransition operator : conjunctiveTransitions)
					simpleGraph.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
				simpleGraphExporter.exportGraph(simpleGraph, simpleGraphWriter);
				dOTFile = simpleGraphWriter.toString();
				break;
		}
		return dOTFile;
	}

	@Override
	public List<ITransition> getTransitions() {
		return transitions;
	}

	@Override
	public double[] getTypicalityArray() {
		return classification.getTypicalityArray();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((transitions == null) ? 0 : transitions.hashCode());
		return result;
	}

	@Override
	public boolean validate(Predicate<ITransitionFunction> validator) {
		return validator.test(this);
	}
	
	private List<IReframer> buildReframers() {
		List<IReframer> reframers = new ArrayList<>();
		Tree<IConcept, IsA> concepts = classification.getClassificationTree();
		//there can only be one such relation, actually
		for (IsA relation : concepts.incomingEdgesOf(concepts.getRoot())) {
			reframers.addAll(buildReframers(relation, new ArrayList<Integer>()));
		}
		return reframers;
	}
	
	private List<IReframer> buildReframers(IsA relation, List<Integer> previousComplementedStatesIDs) {
		List<IReframer> reframers = new ArrayList<>();
		List<Integer> nextComplementedStatesIDs;
		Tree<IConcept, IsA> concepts = classification.getClassificationTree();
		IConcept sourceConcept = concepts.getEdgeSource(relation);
		IState sourceState = categoryToState.get(sourceConcept);
		IState targetState = categoryToState.get(concepts.getEdgeTarget(relation));
		IReframer reframer;
		if (sourceConcept.isComplementary()) {
			IState complementedState = categoryToState.get(sourceConcept.getComplemented());
			reframer = new Reframer(sourceState, complementedState, targetState, previousComplementedStatesIDs);
			nextComplementedStatesIDs = reframer.getComplementedConceptsIDs();
		}
		else {
			reframer = new Reframer(sourceState, targetState, previousComplementedStatesIDs);
			nextComplementedStatesIDs = previousComplementedStatesIDs;
		}
		reframers.add(reframer);
		for (IsA nextRelation : concepts.incomingEdgesOf(sourceConcept)) {
			reframers.addAll(buildReframers(nextRelation, nextComplementedStatesIDs));
		}
		return reframers;
	}

}

