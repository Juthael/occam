package com.tregouet.occam.io.output.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class Visualizer {

	private static String location = "D:\\ProjetDocs\\essais_viz\\";
	private static final DecimalFormat df = new DecimalFormat("#.####");
	
	private Visualizer() {
	}
	
	public static void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public static void visualizeDenotationSetGraph(DirectedAcyclicGraph<IDenotationSet, IIsA> graph, String fileName) 
			throws IOException {
		//convert in DOT format
		DOTExporter<IDenotationSet,IIsA> exporter = new DOTExporter<>();
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
	
	public static void visualizeDenotationGraph(DirectedAcyclicGraph<IDenotation, IProduction> graph, 
			String fileName) throws IOException {
		//convert in DOT format
		DOTExporter<IDenotation,IProduction> exporter = new DOTExporter<>();
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
	
	public static void visualizePorphyrianTree(ITransitionFunction transitionFunction, 
			String fileName) throws IOException {
		Tree<IState, IGenusDifferentiaDefinition> prophyrianTree = 
				transitionFunction.getPorphyrianTree();
		//convert in DOT format
		DOTExporter<IState,IGenusDifferentiaDefinition> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Integer.toString(v.getStateID())));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(buildGenDiffStringDesc(e)));
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
	
	public static void visualizeTransitionFunction(ITransitionFunction tF, String fileName) throws IOException {
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
		simpleGraphExporter.exportGraph(tF.getFiniteAutomatonGraph(), simpleGraphWriter);
		String dOTFile = simpleGraphWriter.toString();
		MutableGraph dotGraph = new Parser().read(dOTFile);
		Graphviz.fromGraph(dotGraph)
			.render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}
	
	public static void visualizeTransitionFunctionMultiGraph(ITransitionFunction tF, String fileName) 
			throws IOException {
		DOTExporter<IState,ITransition> simpleGraphExporter = new DOTExporter<>();
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
		simpleGraphExporter.exportGraph(tF.getFiniteAutomatonMultigraph(), simpleGraphWriter);
		String dOTFile = simpleGraphWriter.toString();
		MutableGraph dotGraph = new Parser().read(dOTFile);
		Graphviz.fromGraph(dotGraph)
			.render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}	
	
	private static  String buildGenDiffStringDesc(IGenusDifferentiaDefinition def) {
		StringBuilder sB = new StringBuilder();
		sB.append("Cost : ");
		sB.append(round(def.getCost()) + System.lineSeparator());
		for (IConjunctiveTransition conjTrans : def.getDifferentiae()) {
			sB.append(operatorAsString(conjTrans));
		}
		return sB.toString();
	}
	
	private static String operatorAsString(ITransition transition) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		if (!transition.isBlank()) {
			if (transition instanceof IConjunctiveTransition) {
				IConjunctiveTransition conjTrans = (IConjunctiveTransition) transition;
				IReframer reframer = conjTrans.getReframer();
				if (reframer != null)
					sB.append("FRAME " + reframer.toString() +  nL);
				for (IBasicOperator operator : conjTrans.getOperators()) {
					sB.append(operatorAsString(operator) + nL);
				}
			}
			else if (transition instanceof IBasicOperator) {
				IBasicOperator operator = (IBasicOperator) transition;
				sB.append(operator.getName() + " : ");
				List<IProduction> productions = operator.operation();
				if (productions.size() > 1)
					sB.append(nL);
				for (int i = 0 ; i < productions.size() ; i++) {
					sB.append(productions.get(i).toString());
					if (i < productions.size() - 1)
						sB.append(nL);
				}
			}
			else if (transition instanceof IReframer) {
				IReframer reframer = (IReframer) transition;
				sB.append(reframer.getName());
			}
		}
		return sB.toString();
	}	
	
	private static String round(double nb) {
		return df.format(nb).toString();
	}
	
}
