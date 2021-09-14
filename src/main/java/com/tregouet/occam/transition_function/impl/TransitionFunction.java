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

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.compiler.impl.Compiler;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ConjunctiveOperator;
import com.tregouet.occam.data.operators.impl.Operator;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IInfoMeter;
import com.tregouet.occam.transition_function.ISimilarityCalculator;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.InTree;

public class TransitionFunction implements ITransitionFunction {

	private final List<IContextObject> objects;
	private final InTree<ICategory, DefaultEdge> categories;
	private final Map<ICategory, IState> categoryToState = new HashMap<>();
	private final List<IOperator> operators;
	private final List<IConjunctiveOperator> conjunctiveOperators = new ArrayList<>();
	private final IInfoMeter infometer;
	private final ISimilarityCalculator similarityCalc;
	
	public TransitionFunction(List<IContextObject> objects, List<ICategory> objectCategories, 
			InTree<ICategory, DefaultEdge> categories, InTree<IIntentAttribute, IProduction> constructs) {
		IOperator.initializeNameProvider();
		this.objects = objects;
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
		infometer = new InfoMeter(objects, categories, operators);
		operators.stream().forEach(o -> o.setInformativity(infometer));
		for (IOperator op : operators) {
			if (!conjunctiveOperators.stream().anyMatch(c -> c.addOperator(op)))
				conjunctiveOperators.add(new ConjunctiveOperator(op));
		}
		similarityCalc = new SimilarityCalculator(categories, conjunctiveOperators);
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
	public InTree<ICategory, DefaultEdge> getCategoryTree() {
		return categories;
	}

	@Override
	public String getCategoryTreeAsDOTFile() {
		DOTExporter<ICategory,DefaultEdge> exporter = new DOTExporter<>();
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
	public ICompiler getCompiler() {
		return new Compiler(objects, this);
	}

	@Override
	public IDSLanguageDisplayer getDomainSpecificLanguage() {
		return new DSLanguageDisplayer(this.getStates(), operators);
	}

	@Override
	public List<IState> getStates() {
		return new ArrayList<>(categoryToState.values());
	}

	@Override
	public String getTransitionFunctionAsDOTFile() {
		DOTExporter<IState,IOperator> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(setIntentsAsString(s.getInputLanguage())));
			return map;
		});
		exporter.setEdgeAttributeProvider((o) -> {
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
		exporter.exportGraph(stateMachine, writer);
		return writer.toString();
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
	public int compareTo(ITransitionFunction other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		//to avoid loss of elements in TreeSet
		if (this.equals(other))
			return 0;
		return 1;
	}

	@Override
	public ISimilarityCalculator getSimilarityCalculator() {
		return similarityCalc;
	}

	@Override
	public double getCoherenceScore() {
		return similarityCalc.getCoherenceScore();
	}

	@Override
	public IInfoMeter getInfometer() {
		return infometer;
	}
	
	private static String setOperatorAsString(IOperator operator) {
		if (operator.isBlank())
			return operator.getName() + " : inheritance";
		StringBuilder sB = new StringBuilder();
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
	public String getTFWithConjunctiveOperatorsAsDOTFile() {
		DOTExporter<IState,IConjunctiveOperator> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(setIntentsAsString(s.getInputLanguage())));
			return map;
		});
		exporter.setEdgeAttributeProvider((o) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(setOperatorAsString(o)));
			return map;
		});		
		Writer writer = new StringWriter();
		DirectedMultigraph<IState, IConjunctiveOperator> stateMachine = new DirectedMultigraph<>(null, null, false);
		for (IState state : getStates())
			stateMachine.addVertex(state);
		for (IConjunctiveOperator operator : conjunctiveOperators)
			stateMachine.addEdge(operator.getOperatingState(), operator.getNextState(), operator);
		exporter.exportGraph(stateMachine, writer);
		return writer.toString();
	}

}
