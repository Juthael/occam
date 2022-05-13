package com.tregouet.occam.alg.displayers.visualizers.problem_spaces.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.DepthFirstIterator;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.ProblemSpaceViz;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.io.output.LocalPaths;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class BasicProblemSpaceViz implements ProblemSpaceViz {

	public static final BasicProblemSpaceViz INSTANCE = new BasicProblemSpaceViz();

	private BasicProblemSpaceViz() {
	}

	@Override
	public String apply(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> graph, String fileName) {
		// trim if needed
		DirectedAcyclicGraph<IProblemState, AProblemStateTransition> trimmedGraph = trimIfNeeded(graph);
		// convert in DOT format
		DOTExporter<IProblemState, AProblemStateTransition> exporter = new DOTExporter<>();
		ProblemStateLabeller stateDisplayer = FormattersAbstractFactory.INSTANCE.getProblemStateDisplayer();
		ProblemTransitionLabeller transitionDisplayer = FormattersAbstractFactory.INSTANCE
				.getProblemTransitionDisplayer();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(stateDisplayer.apply(v)));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(transitionDisplayer.apply(e)));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(trimmedGraph, writer);
		String stringDOT = writer.toString();
		// display graph
		try {
			MutableGraph dotGraph = new Parser().read(stringDOT);
			String filePath = LocalPaths.INSTANCE.getTargetFolderPath() + "\\" + fileName;
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
			return filePath + ".png";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private DirectedAcyclicGraph<IProblemState, AProblemStateTransition> trimIfNeeded(
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> graph){
		int maxNb = ProblemSpaceViz.getMaxNbOfProblemStatesDisplayed();
		if (graph.vertexSet().size() > maxNb) {
			Set<IProblemState> retainedStates = new HashSet<>();
			Set<AProblemStateTransition> retainedTransitions = new HashSet<>();
			DepthFirstIterator<IProblemState, AProblemStateTransition> dfIte = new DepthFirstIterator<>(graph);
			int idx = 0;
			while (idx < maxNb) {
				retainedStates.add(dfIte.next());
				idx++;
			}
			for (AProblemStateTransition transition : graph.edgeSet()) {
				if (retainedStates.contains(transition.getSource()) && retainedStates.contains(transition.getTarget()))
					retainedTransitions.add(transition);
			}
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> trimmedGraph = new DirectedAcyclicGraph<>(null, null, false);
			for (IProblemState retainedState : retainedStates)
				trimmedGraph.addVertex(retainedState);
			for (AProblemStateTransition retainedTransition : retainedTransitions) {
				trimmedGraph.addEdge(retainedTransition.getSource(), retainedTransition.getTarget(), retainedTransition);
			}
			return trimmedGraph;
		}
		else return graph;
	}

}
