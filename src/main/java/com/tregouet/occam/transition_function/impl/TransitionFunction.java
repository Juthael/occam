package com.tregouet.occam.transition_function.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.Operator;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.InTree;

public class TransitionFunction implements ITransitionFunction {

	private final List<IContextObject> objects;
	private final InTree<ICategory, DefaultEdge> categories;
	private final Map<ICategory, IState> categoryToState = new HashMap<>();
	private final List<IOperator> operators;
	private final double cost;
	
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
		double currCost = 0;
		for (IOperator operator : operators)
			currCost += operator.getCost();
		cost = currCost;
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

	public static List<List<Integer>> groupIndexesOfProductionsHandledByTheSameOperator(
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

	@Override
	public int compareTo(ITransitionFunction other) {
		if (this.getCost() < other.getCost())
			return -1;
		if (this.getCost() > other.getCost())
			return 1;
		return 0;
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
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (operators == null) {
			if (other.operators != null)
				return false;
		} else if (!operators.equals(other.operators))
			return false;
		return true;
	}

	@Override
	public String getCategoryStructureAsDOTFile() {
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
	public double getCost() {
		return cost;
	}
	
	@Override
	public IDSLanguageDisplayer getDomainSpecificLanguage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<IState> getStates() {
		return new ArrayList<>(categoryToState.values());
	}

	@Override
	public String getTransitionFunctionAsDOTFile() {
		DOTExporter<IState,IOperator> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(s.getIntent().toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((o) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(
					o.getName() 
					+ System.lineSeparator()
					+ Double.toString(Math.round(o.getCost()*1000.0)/1000.0)));
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
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((operators == null) ? 0 : operators.hashCode());
		return result;
	}

}
