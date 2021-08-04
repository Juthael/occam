package com.tregouet.occam.transition_function.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
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
	InTree<ICategory, DefaultEdge> categories;
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
		operators = buildOperators(new ArrayList<>(constructs.edgeSet()));
		double currCost = 0;
		for (IOperator operator : operators)
			currCost += operator.getCost();
		cost = currCost;
	}

	@Override
	public double getCost() {
		return cost;
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
	public String getTransitionFunctionAsDOTFile() {
		DOTExporter<IState,IOperator> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(s.getIntent().toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((o) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(o.getName()));
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
	public List<IState> getStates() {
		return new ArrayList<>(categoryToState.values());
	}

	@Override
	public List<IOperator> getTransitions() {
		return operators;
	}

	@Override
	public IDSLanguageDisplayer getDomainSpecificLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompiler getCompiler() {
		return new Compiler(objects, this);
	}
	
	public List<IOperator> buildOperators(List<IProduction> productions){
		List<IOperator> operators = new ArrayList<>();
		List<Integer> skipIdx = new ArrayList<>();	
		Map<Integer, List<Integer>> productionToSameOperatorProd = new HashMap<>();
		for (int i = 0 ; i < productions.size() ; i++) {
			if (!skipIdx.contains(i)) {
				productionToSameOperatorProd.put(i, new ArrayList<>());
				IProduction iProduction = productions.get(i);
				for (int j = i+1 ; j < productions.size() ; j++) {
					if (!skipIdx.contains(j)) {
						IProduction jProduction = productions.get(j);
						if (iProduction.getSourceCategory().equals(jProduction.getSourceCategory())
								&& iProduction.getTargetCategory().equals(jProduction.getTargetCategory())
								&& (iProduction.getTarget().equals(jProduction.getTarget())
									|| new ArrayList<>(iProduction.getValues()).removeAll(jProduction.getValues()))) {
							productionToSameOperatorProd.get(i).add(j);
							skipIdx.add(j);
						}
					}
				}	
			}
		}
		for (Integer idx : productionToSameOperatorProd.keySet()) {
			List<IProduction> operation = new ArrayList<>();
			IProduction production = productions.get(idx);
			IState activeState = categoryToState.get(production.getSourceCategory());
			IState nextState = categoryToState.get(production.getTargetCategory());
			operation.add(production);
			for (Integer associatedIdx : productionToSameOperatorProd.get(idx)) {
				operation.add(productions.get(associatedIdx));
			}
			operators.add(new Operator(activeState, operation, nextState));
		}
		return operators;
	}

}
