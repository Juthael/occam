package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.utils;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionSetReducer {

	public static Set<IContextualizedProduction> reduce(Set<IContextualizedProduction> productions) {
		Set<IContextualizedProduction> transitiveReduction = new HashSet<>();
		DirectedAcyclicGraph<IDenotation, DefaultEdge> sparseReductionGraph = buildSparseReductionGraph(productions);
		for (IContextualizedProduction production : productions) {
			if (sparseReductionGraph.containsEdge(production.getSource(), production.getTarget()))
					transitiveReduction.add(production);
		}
		return transitiveReduction;
	}

	private static DirectedAcyclicGraph<IDenotation, DefaultEdge> buildSparseReductionGraph(Set<IContextualizedProduction> productions) {
		DirectedAcyclicGraph<IDenotation, DefaultEdge> sparseReductionGraph =
				new DirectedAcyclicGraph<>(null, DefaultEdge::new, false);
		for (IContextualizedProduction production : productions) {
			IDenotation source = production.getSource();
			IDenotation target = production.getTarget();
			sparseReductionGraph.addVertex(source);
			sparseReductionGraph.addVertex(target);
			sparseReductionGraph.addEdge(source, target);
		}
		TransitiveReduction.INSTANCE.reduce(sparseReductionGraph);
		return sparseReductionGraph;
	}

}
