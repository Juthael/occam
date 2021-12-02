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

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.property_weighing.IPropertyWeigher;
import com.tregouet.occam.cost_calculation.property_weighing.PropertyWeigherFactory;
import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.cost_calculation.similarity_calculation.SimilarityCalculatorFactory;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ConjunctiveOperator;
import com.tregouet.occam.data.operators.impl.Operator;
import com.tregouet.occam.data.operators.impl.Rebutter;
import com.tregouet.occam.finite_automaton.IFiniteAutomaton;
import com.tregouet.occam.finite_automaton.impl.FiniteAutomaton;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.tree_finder.data.Tree;

public class TransitionFunction implements ITransitionFunction {

	private final List<IContextObject> objects;
	private final List<ICategory> objectCategories;
	private final Tree<ICategory, IsA> categories;
	private final Map<ICategory, IState> categoryToState = new HashMap<>();
	private final List<IOperator> operators;
	private final List<IConjunctiveOperator> conjunctiveOperators = new ArrayList<>();
	private final IPropertyWeigher propWeigher;
	private final ISimilarityCalculator similarityCalc;
	private DirectedMultigraph<IState, IOperator> finiteAutomatonMultigraph = null;
	private SimpleDirectedGraph<IState, IConjunctiveOperator> finiteAutomatonGraph = null;
	
	public TransitionFunction(List<IContextObject> objects, List<ICategory> objectCategories, 
			Tree<ICategory, IsA> categories, Tree<IIntentAttribute, IProduction> constructs, 
			PropertyWeighingStrategy propWeighingStrategy, SimilarityCalculationStrategy simCalculationStrategy) {
		IOperator.initializeNameProvider();
		IConjunctiveOperator.initializeNameProvider();
		this.objects = objects;
		this.objectCategories = objectCategories;
		this.categories = categories;
		for (ICategory category : categories.vertexSet()) {
			if (objectCategories.contains(category))
				categoryToState.put(category, new State(category, 1));
			else {
				int extentSize = 0;
				for (ICategory ancestor : categories.getAncestors(category)) {
					if (categories.inDegreeOf(ancestor) == 0)
						extentSize++;
				}
				categoryToState.put(category, new State(category, extentSize));	
			}
		}
		operators = buildOperators(new ArrayList<>(constructs.edgeSet()), categoryToState);
		propWeigher = PropertyWeigherFactory.apply(propWeighingStrategy);
		propWeigher.set(objects, categories, operators);
		operators.stream().forEach(o -> o.setInformativity(propWeigher));
		for (IOperator op : operators) {
			if (!conjunctiveOperators.stream().anyMatch(c -> c.addOperator(op)))
				conjunctiveOperators.add(new ConjunctiveOperator(op));
		}
		buildRebutterOperators();
		similarityCalc = SimilarityCalculatorFactory.apply(simCalculationStrategy); 
		similarityCalc.set(categories, conjunctiveOperators);
	}

	public static List<IOperator> buildOperators(
			List<IProduction> productions, Map<ICategory, IState> categoryToState){
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
			operators.add(new Operator(activeState, operation, nextState));	
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

	private static String setOperatorAsString(IOperator operator) {
		StringBuilder sB = new StringBuilder();
		if (operator instanceof IConjunctiveOperator) {
			BigDecimal approxInformativitySum = BigDecimal.valueOf(operator.getInformativity());
			sB.append("***" + operator.getName() + " : " + 
					approxInformativitySum.round(new MathContext(3)).toString()+ "***" + System.lineSeparator());
			List<IOperator> components = new ArrayList<>();
			for (IOperator component : ((IConjunctiveOperator) operator).getComponents()) {
				if (!component.isBlank())
					components.add(component);
			}
			for (int i = 0 ; i < components.size() ; i++) {
				sB.append(setOperatorAsString(components.get(i)));
				if (i < components.size() - 1)
					sB.append(System.lineSeparator());
			}
			return sB.toString();
		}
		if (operator.isBlank())
			return operator.getName() + " : inheritance";
		BigDecimal approxInformativity = BigDecimal.valueOf(operator.getInformativity());
		sB.append(operator.getName() + " : " + 
				approxInformativity.round(new MathContext(3)).toString() + System.lineSeparator());
		List<IProduction> productions = operator.operation();
		for (int i = 0 ; i < productions.size() ; i++) {
			sB.append(productions.get(i).toString());
			if (i < productions.size() - 1)
				sB.append(System.lineSeparator());
		}
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
		if (operators == null) {
			if (other.operators != null)
				return false;
		} else if (!operators.equals(other.operators))
			return false;
		return true;
	}
	
	@Override
	public Tree<ICategory, IsA> getCategoryTree() {
		return categories;
	}

	@Override
	public String getCategoryTreeAsDOTFile() {
		DOTExporter<ICategory,IsA> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(categories, writer);
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
	public List<IConjunctiveOperator> getConjunctiveTransitions() {
		return conjunctiveOperators;
	}

	@Override
	public IDSLanguageDisplayer getDomainSpecificLanguage() {
		return new DSLanguageDisplayer(this.getStates(), operators);
	}

	@Override
	public IPropertyWeigher getInfometer() {
		return propWeigher;
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
				DOTExporter<IState,IOperator> multigraphExporter = new DOTExporter<>();
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
					map.put("label", DefaultAttribute.createAttribute(setOperatorAsString(o)));
					return map;
				});		
				Writer writer = new StringWriter();
				DirectedMultigraph<IState, IOperator> stateMachine = new DirectedMultigraph<>(null, null, false);
				for (IState state : getStates())
					stateMachine.addVertex(state);
				for (IOperator operator : operators)
					stateMachine.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
				multigraphExporter.exportGraph(stateMachine, writer);
				dOTFile = writer.toString();
				break;
			case FINITE_AUTOMATON : 
				DOTExporter<IState,IConjunctiveOperator> simpleGraphExporter = new DOTExporter<>();
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
					map.put("label", DefaultAttribute.createAttribute(setOperatorAsString(o)));
					return map;
				});		
				Writer simpleGraphWriter = new StringWriter();
				SimpleDirectedGraph<IState, IConjunctiveOperator> simpleGraph = 
						new SimpleDirectedGraph<>(null, null, false);
				for (IState state : getStates())
					simpleGraph.addVertex(state);
				for (IConjunctiveOperator operator : conjunctiveOperators)
					simpleGraph.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
				simpleGraphExporter.exportGraph(simpleGraph, simpleGraphWriter);
				dOTFile = simpleGraphWriter.toString();
				break;
		}
		return dOTFile;
	}

	@Override
	public List<IOperator> getTransitions() {
		return operators;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((operators == null) ? 0 : operators.hashCode());
		return result;
	}

	@Override
	public SimpleDirectedGraph<IState, IConjunctiveOperator> getFiniteAutomatonGraph() {
		if (finiteAutomatonGraph == null) {
			finiteAutomatonGraph = new SimpleDirectedGraph<>(null, null, false);
			for (IState state : getStates())
				finiteAutomatonGraph.addVertex(state);
			for (IConjunctiveOperator operator : conjunctiveOperators)
				finiteAutomatonGraph.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
		}
		return finiteAutomatonGraph;
	}

	@Override
	public DirectedMultigraph<IState, IOperator> getFiniteAutomatonMultigraph() {
		if (finiteAutomatonMultigraph == null) {
			finiteAutomatonMultigraph = new DirectedMultigraph<>(null, null, false);
			for (IState state : getStates())
				finiteAutomatonMultigraph.addVertex(state);
			for (IOperator operator : operators)
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
			int iObjCatID = objectCategories.get(i).getID();
			similarityMatrix[i][i] = 1.0;
			for (int j = i + 1 ; j < nbOfObjects ; j++) {
				int jObjCatID = objectCategories.get(j).getID();
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
			int iObjCatID = objectCategories.get(i).getID();
			similarityMatrix[i][i] = 1.0;
			for (int j = i + 1 ; j < nbOfObjects ; j++) {
				int jObjCatID = objectCategories.get(j).getID();
				similarityMatrix[i][j] = similarityCalc.howSimilarTo(iObjCatID, jObjCatID);
				similarityMatrix[j][i] = similarityCalc.howSimilarTo(jObjCatID, iObjCatID);
			}
		}
		return similarityMatrix;
	}

	@Override
	public Map<Integer, Double> getCategoricalCoherenceMap() {
		Map<Integer, Double> catIDToCoherenceScore = new HashMap<>();
		TopologicalOrderIterator<ICategory, IsA> iterator = new TopologicalOrderIterator<>(categories);
		while (iterator.hasNext()) {
			ICategory nextCat = iterator.next();
			Set<IContextObject> extent = nextCat.getExtent();
			int[] extentIDs = new int[extent.size()];
			int idx = 0;
			for (IContextObject obj : extent) {
				extentIDs[idx++] = objectCategories.get(objects.indexOf(obj)).getID();
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
			typicalityArray[i] = similarityCalc.howProtoypical(objectCategories.get(i).getID());
		}
		return typicalityArray;
	}
	
	private void buildRebutterOperators() {
		List<IOperator> rebutterOperators = new ArrayList<>();
		List<ICategory> rebutterCats = new ArrayList<>();
		for (ICategory category : categories.vertexSet()) {
			if (category.isComplementary())
				rebutterCats.add(category);
		}
		for (ICategory rebutterCat : rebutterCats) {
			ICategory rebuttedCat = rebutterCat.getComplemented();
			IState rebutterState = categoryToState.get(rebutterCat);
			IState rebuttedState = categoryToState.get(rebuttedCat);
			IState rebutterNextState = null;
			IConjunctiveOperator rebuttedOperator = null;
			int conjunctiveOpIdx = 0;
			while (rebuttedOperator == null) {
				IConjunctiveOperator op = conjunctiveOperators.get(conjunctiveOpIdx);
				if (op.getOperatingState().equals(rebuttedState)) {
					rebuttedOperator = op;
					rebutterNextState = op.getNextState();
				}
				conjunctiveOpIdx++;
			}
			rebutterOperators.add(new Rebutter(rebutterState, rebutterNextState, rebuttedOperator));
		}
		for (IOperator rebutter : rebutterOperators) {
			if (!conjunctiveOperators.stream().anyMatch(c -> c.addOperator(rebutter)))
				conjunctiveOperators.add(new ConjunctiveOperator(rebutter));
		}
	}

}
