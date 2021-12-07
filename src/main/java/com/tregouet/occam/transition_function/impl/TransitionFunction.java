package com.tregouet.occam.transition_function.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.cost_calculation.similarity_calculation.SimilarityCalculatorFactory;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicOperator;
import com.tregouet.occam.data.operators.IConjunctiveTransition;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.data.operators.ITransition;
import com.tregouet.occam.data.operators.impl.ConjunctiveTransition;
import com.tregouet.occam.data.operators.impl.Reframer;
import com.tregouet.occam.data.operators.impl.BasicOperator;
import com.tregouet.occam.finite_automaton.IFiniteAutomaton;
import com.tregouet.occam.finite_automaton.impl.FiniteAutomaton;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.tree_finder.data.Tree;

public class TransitionFunction implements ITransitionFunction {

	private final List<IContextObject> objects;
	private final List<IConcept> singletons;
	private final Tree<IConcept, IsA> concepts;
	private final Map<IConcept, IState> categoryToState = new HashMap<>();
	private final List<ITransition> transitions = new ArrayList<>();
	private final List<IConjunctiveTransition> conjunctiveTransitions = new ArrayList<>();
	private final ISimilarityCalculator similarityCalc;
	private DirectedMultigraph<IState, ITransition> finiteAutomatonMultigraph = null;
	private SimpleDirectedGraph<IState, IConjunctiveTransition> finiteAutomatonGraph = null;
	
	public TransitionFunction(List<IContextObject> objects, List<IConcept> singletons, 
			Tree<IConcept, IsA> concepts, Tree<IIntentAttribute, IProduction> constructs, 
			SimilarityCalculationStrategy simCalculationStrategy) {
		ITransition.initializeNameProvider();
		IConjunctiveTransition.initializeNameProvider();
		this.objects = objects;
		this.singletons = singletons;
		this.concepts = concepts;
		for (IConcept concept : concepts.vertexSet()) {
			if (singletons.contains(concept))
				categoryToState.put(concept, new State(concept, 1));
			else {
				int extentSize = 0;
				for (IConcept ancestor : concepts.getAncestors(concept)) {
					if (concepts.inDegreeOf(ancestor) == 0)
						extentSize++;
				}
				categoryToState.put(concept, new State(concept, extentSize));	
			}
		}
		transitions.addAll(buildOperators(new ArrayList<>(constructs.edgeSet())));
		transitions.addAll(buildReframers());
		for (ITransition transition : transitions) {
			if (!conjunctiveTransitions.stream().anyMatch(t -> t.addTransition(transition)))
				conjunctiveTransitions.add(new ConjunctiveTransition(transition));
		}
		similarityCalc = SimilarityCalculatorFactory.apply(simCalculationStrategy); 
		similarityCalc.set(concepts, conjunctiveTransitions);
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

	private static String setIntentsAsString(Set<IIntentAttribute> attributes){
		StringBuilder sB = new StringBuilder();
		for (IIntentAttribute att : attributes) {
			sB.append(att.toString() + System.lineSeparator());
		}
		sB.deleteCharAt(sB.length() - 1);
		return sB.toString();
	}

	private static String operatorAsString(ITransition transition) {
		StringBuilder sB = new StringBuilder();
		if (transition instanceof IConjunctiveTransition) {
			sB.append("***" + transition.getName() + " : ");
			List<ITransition> components = new ArrayList<>();
			for (ITransition component : ((IConjunctiveTransition) transition).getComponents()) {
				if (component instanceof IReframer || !((IOperator)component).isBlank())
					components.add(component);
			}
			for (int i = 0 ; i < components.size() ; i++) {
				sB.append(operatorAsString(components.get(i)));
				if (i < components.size() - 1)
					sB.append(System.lineSeparator());
			}
			return sB.toString();
		}
		if (transition instanceof IBasicOperator) {
			IOperator operator = (IOperator) transition;
			if (operator.isBlank())
				return operator.getName() + " : inheritance";
			sB.append(operator.getName() + " : ");
			List<IProduction> productions = operator.operation();
			for (int i = 0 ; i < productions.size() ; i++) {
				sB.append(productions.get(i).toString());
				if (i < productions.size() - 1)
					sB.append(System.lineSeparator());
			}
			return sB.toString();
		}
		IReframer reframer = (IReframer) transition;
		sB.append(reframer.getName() + " : " + reframer.reframing());
		return sB.toString();
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
	public Tree<IConcept, IsA> getCategoryTree() {
		return concepts;
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
		exporter.exportGraph(concepts, writer);
		return writer.toString();
	}

	@Override
	public double getCoherenceScore() {
		return similarityCalc.getCoherenceScore();
	}

	@Override
	public IFiniteAutomaton getCompiler() {
		return new FiniteAutomaton(objects, this);
	}
	
	@Override
	public List<IConjunctiveTransition> getConjunctiveTransitions() {
		return conjunctiveTransitions;
	}

	@Override
	public IDSLanguageDisplayer getDomainSpecificLanguage() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public ISimilarityCalculator getSimilarityCalculator() {
		return similarityCalc;
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
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((transitions == null) ? 0 : transitions.hashCode());
		return result;
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
	public boolean validate(Predicate<ITransitionFunction> validator) {
		return validator.test(this);
	}

	@Override
	public double[][] getSimilarityMatrix() {
		int nbOfObjects = objects.size();
		double[][] similarityMatrix = new double[nbOfObjects][nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			int iObjCatID = singletons.get(i).getID();
			similarityMatrix[i][i] = 1.0;
			for (int j = i + 1 ; j < nbOfObjects ; j++) {
				int jObjCatID = singletons.get(j).getID();
				double similarityScoreIJ = similarityCalc.howSimilar(iObjCatID, jObjCatID);
				similarityMatrix[i][j] = similarityScoreIJ;
				similarityMatrix[j][i] = similarityScoreIJ;
			}
		}
		return similarityMatrix;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		int nbOfObjects = objects.size();
		double[][] similarityMatrix = new double[nbOfObjects][nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			int iObjCatID = singletons.get(i).getID();
			similarityMatrix[i][i] = 1.0;
			for (int j = i + 1 ; j < nbOfObjects ; j++) {
				int jObjCatID = singletons.get(j).getID();
				similarityMatrix[i][j] = similarityCalc.howSimilarTo(iObjCatID, jObjCatID);
				similarityMatrix[j][i] = similarityCalc.howSimilarTo(jObjCatID, iObjCatID);
			}
		}
		return similarityMatrix;
	}

	@Override
	public Map<Integer, Double> getCategoricalCoherenceMap() {
		Map<Integer, Double> catIDToCoherenceScore = new HashMap<>();
		TopologicalOrderIterator<IConcept, IsA> iterator = new TopologicalOrderIterator<>(concepts);
		while (iterator.hasNext()) {
			IConcept nextCat = iterator.next();
			Set<IContextObject> extent = nextCat.getExtent();
			int[] extentIDs = new int[extent.size()];
			int idx = 0;
			for (IContextObject obj : extent) {
				extentIDs[idx++] = singletons.get(objects.indexOf(obj)).getID();
			}
			catIDToCoherenceScore.put(nextCat.getID(), similarityCalc.getCoherenceScore(extentIDs));
		}
		return catIDToCoherenceScore;
	}

	@Override
	public double[] getTypicalityArray() {
		int nbOfObjects = objects.size();
		double[] typicalityArray = new double[nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			typicalityArray[i] = similarityCalc.howProtoypical(singletons.get(i).getID());
		}
		return typicalityArray;
	}
	
	private List<IReframer> buildReframers() {
		List<IReframer> reframingOp = new ArrayList<>(); 
		for (IConcept concept : concepts.vertexSet()) {
			if (concept.isComplementary()) {
				IState complementaryState = categoryToState.get(concept);
				IConcept complementedConcept = concept.getComplemented();
				IState complementedState = categoryToState.get(complementedConcept);
				IState successorState = 
						categoryToState.get(Graphs.successorListOf(concepts, complementedConcept).get(0));
				reframingOp.add(new Reframer(complementaryState, complementedState, successorState));
			}
		}
		return reframingOp;
	}

}
