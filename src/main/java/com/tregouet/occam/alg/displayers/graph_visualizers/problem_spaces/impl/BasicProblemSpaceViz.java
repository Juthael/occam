package com.tregouet.occam.alg.displayers.graph_visualizers.problem_spaces.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.occam.alg.displayers.graph_labellers.LabellersAbstractFactory;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.graph_visualizers.problem_spaces.ProblemSpaceViz;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.io.output.Paths;

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
		//convert in DOT format
		DOTExporter<IProblemState, AProblemStateTransition> exporter = new DOTExporter<>();
		ProblemStateLabeller stateDisplayer = LabellersAbstractFactory.INSTANCE.getProblemStateDisplayer();
		ProblemTransitionLabeller transitionDisplayer = LabellersAbstractFactory.INSTANCE.getProblemTransitionDisplayer();
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
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		//display graph
		try {		
			MutableGraph dotGraph = new Parser().read(stringDOT);
			String filePath = Paths.INSTANCE.getTargetFolderPath() + "\\" + fileName;
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
			return filePath;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}

}
