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

import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IBasicOperator;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IReframerRule;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ITransition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.evaluation.facts.IStronglyContextualized;
import com.tregouet.tree_finder.data.InvertedTree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class Visualizer {

	private static String location = "C:\\Users\\TREGOUET\\Documents\\Sandbox";
	private static final DecimalFormat df = new DecimalFormat("#.####");
	
	private Visualizer() {
	}
	
	public static void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public static void visualizeDenotationSetGraph(DirectedAcyclicGraph<IConcept, IIsA> graph, String fileName) 
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
		Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(location + "\\" + fileName));
	}	
	
	public static void visualizePorphyrianTree(IAutomaton automaton, 
			String fileName) throws IOException {
		InvertedTree<IState, IGenusDifferentiaDefinition_dep> prophyrianTree = 
				automaton.getPorphyrianTree();
		//convert in DOT format
		DOTExporter<IState,IGenusDifferentiaDefinition_dep> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Integer.toString(v.iD())));
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
	
	public static void visualizeTransitionFunction(IAutomaton tF, String fileName) throws IOException {
		DOTExporter<IState,IConjunctiveTransition> simpleGraphExporter = new DOTExporter<>();
		simpleGraphExporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		simpleGraphExporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Integer.toString(s.iD())));
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
	
	public static void visualizeTransitionFunctionMultiGraph(IAutomaton tF, String fileName) 
			throws IOException {
		DOTExporter<IState,ITransition> simpleGraphExporter = new DOTExporter<>();
		simpleGraphExporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		simpleGraphExporter.setVertexAttributeProvider((s) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(Integer.toString(s.iD())));
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
	
	private static  String buildGenDiffStringDesc(IGenusDifferentiaDefinition_dep def) {
		StringBuilder sB = new StringBuilder();
		sB.append("Cost : ");
		sB.print(round(def.getCost()) + System.lineSeparator());
		for (IConjunctiveTransition conjTrans : def.getDifferentiae()) {
			sB.print(operatorAsString(conjTrans));
		}
		return sB.toString();
	}
	
	private static String operatorAsString(ITransition transition) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		if (!transition.isBlank()) {
			if (transition instanceof IConjunctiveTransition) {
				IConjunctiveTransition conjTrans = (IConjunctiveTransition) transition;
				IReframerRule reframerRule = conjTrans.getReframer();
				if (reframerRule != null)
					sB.print("FRAME " + reframerRule.toString() +  nL);
				for (IBasicOperator operator : conjTrans.getOperators()) {
					sB.print(operatorAsString(operator) + nL);
				}
			}
			else if (transition instanceof IBasicOperator) {
				IBasicOperator operator = (IBasicOperator) transition;
				sB.print(operator.getName() + " : ");
				List<IStronglyContextualized> stronglyContextualizeds = operator.operation();
				if (stronglyContextualizeds.size() > 1)
					sB.append(nL);
				for (int i = 0 ; i < stronglyContextualizeds.size() ; i++) {
					sB.print(stronglyContextualizeds.get(i).toString());
					if (i < stronglyContextualizeds.size() - 1)
						sB.append(nL);
				}
			}
			else if (transition instanceof IReframerRule) {
				IReframerRule reframerRule = (IReframerRule) transition;
				sB.print(reframerRule.getName());
			}
		}
		return sB.toString();
	}	
	
	private static String round(double nb) {
		return df.format(nb).toString();
	}
	
}
