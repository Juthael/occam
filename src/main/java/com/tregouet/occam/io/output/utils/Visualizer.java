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
import org.jgrapht.opt.graph.sparse.SparseIntDirectedWeightedGraph;

import com.tregouet.occam.alg.conceptual_structure_gen.IOntologist;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.TransitionFunctionGraphType;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class Visualizer {

	private static String location = "D:\\ProjetDocs\\essais_viz\\";
	
	private Visualizer() {
	}
	
	public static void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public static void visualizeConceptGraph(DirectedAcyclicGraph<IConcept, IIsA> graph, String fileName) 
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
		/*
		 System.out.println(writer.toString());
		 */
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}	
	
	public static void visualizeConstructGraph(DirectedAcyclicGraph<IIntentConstruct, IProduction> graph, 
			String fileName) throws IOException {
		//convert in DOT format
		DOTExporter<IIntentConstruct,IProduction> exporter = new DOTExporter<>();
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
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(e.getLabel()));
			return map;
		}); 
		Writer writer = new StringWriter();
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		/*
		 System.out.println(writer.toString());
		*/ 
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		Graphviz.fromGraph(dotGraph)
			.render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}
	
	public static void visualizeTransitionFunction(ITransitionFunction tF, String fileName, 
			TransitionFunctionGraphType graphType) throws IOException {
		MutableGraph dotGraph = new Parser().read(tF.getTransitionFunctionAsDOTFile(graphType));
		Graphviz.fromGraph(dotGraph)
			.render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}
	
	public static void visualizeWeightedSparseGraph(SparseIntDirectedWeightedGraph graph, String fileName) 
			throws IOException {
		//convert in DOT format
		DOTExporter<Integer, Integer> exporter = new DOTExporter<>();
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
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Double.toString(graph.getEdgeWeight(e))));
			return map;
		}); 
		Writer writer = new StringWriter();
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		/*
		 System.out.println(writer.toString());
		*/ 
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		Graphviz.fromGraph(dotGraph)
			.render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}
	
	public static void visualizePorphyrianTree(ITransitionFunction transitionFunction, 
			String fileName) throws IOException {
		Tree<IConcept, IGenusDifferentiaDefinition> prophyrianTree = 
				IOntologist.getPorphyrianTree(transitionFunction);
		IClassification classification = transitionFunction.getClassification();
		//convert in DOT format
		DOTExporter<IConcept,IGenusDifferentiaDefinition> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Integer.toString(v.getID())));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(buildGenDiffStringDesc(e, classification)));
			return map;
		}); 		
		Writer writer = new StringWriter();
		exporter.exportGraph(prophyrianTree, writer);
		String stringDOT = writer.toString();
		/*
		 System.out.println(writer.toString());
		 */
		//display graph
		MutableGraph dotGraph = new Parser().read(stringDOT);
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}
	
	private static  String buildGenDiffStringDesc(IGenusDifferentiaDefinition def, 
			IClassification classification) {
		StringBuilder sB = new StringBuilder();
		sB.append("Informativity : ");
		sB.append(Double.toString(classification.getCostOf(def)) + System.lineSeparator());
		sB.append(def.toString());
		return sB.toString();
	}
	

}
