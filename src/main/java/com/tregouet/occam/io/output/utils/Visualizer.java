package com.tregouet.occam.io.output.utils;

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
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class Visualizer {

	private static String location = "C:\\Users\\TREGOUET\\Documents\\Sandbox";
	
	private Visualizer() {
	}
	
	public static void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public static String vizualizeProblemSpaceGraph(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> pbSpaceGraph, 
			String fileName) throws IOException {
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
		exporter.exportGraph(pbSpaceGraph, writer);
		String stringDOT = writer.toString();
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		String filePath = location + "\\" + fileName;
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
		return filePath;
	}
	
	public static String visualizeConceptGraph(DirectedAcyclicGraph<IConcept, IIsA> graph, String fileName) 
			throws IOException {
		//convert in DOT format
		DOTExporter<IConcept,IIsA> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		String filePath = location + "\\" + fileName;
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
		return filePath;
	}	
	
	public static String visualizeTransitionFunction(IRepresentationTransitionFunction transFunc, 
			String fileName) throws IOException {
		TransitionFunctionLabeller displayer = LabellersAbstractFactory.INSTANCE.getTransitionFunctionDisplayer();
		DirectedAcyclicGraph<Integer, AConceptTransitionSet> transFuncGraph = displayer.apply(transFunc);
		//convert in DOT format
		DOTExporter<Integer, AConceptTransitionSet> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(e.toString()));
			return map;
		});		
		Writer writer = new StringWriter();
		exporter.exportGraph(transFuncGraph, writer);
		String dOTFile = writer.toString();
		//display graph
		MutableGraph dotGraph = new Parser().read(dOTFile);
		String filePath = location + "\\" + fileName;
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
		return filePath;
	}
	
	public static String visualizeRepresentationDescription(IDescription description, String fileName) 
			throws IOException {
		DifferentiaeLabeller diffDisplayer = LabellersAbstractFactory.INSTANCE.getDifferentiaeDisplayer();
		Tree<Integer, AbstractDifferentiae> descGraph = description.asGraph();
		//convert in DOT format
		DOTExporter<Integer, AbstractDifferentiae> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(diffDisplayer.apply(e)));
			return map;
		});		
		Writer writer = new StringWriter();
		exporter.exportGraph(descGraph, writer);
		String dOTFile = writer.toString();
		//display graph
		MutableGraph dotGraph = new Parser().read(dOTFile);
		String filePath = location + "\\" + fileName;
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
		return filePath;
	}
	
}
