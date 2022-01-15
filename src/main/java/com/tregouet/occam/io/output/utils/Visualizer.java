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

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.IReframerRule;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
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
	
	public static void visualizeDenotationGraph(DirectedAcyclicGraph<IDenotation, IProductionAsEdge> graph, 
			String fileName) throws IOException {
		//convert in DOT format
		DOTExporter<IDenotation,IProductionAsEdge> exporter = new DOTExporter<>();
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
	
	public static void visualizePorphyrianTree(IAutomaton automaton, 
			String fileName) throws IOException {
		Tree<IState, IGenusDifferentiaDefinition> prophyrianTree = 
				automaton.getPorphyrianTree();
		//convert in DOT format
		DOTExporter<IState,IGenusDifferentiaDefinition> exporter = new DOTExporter<>();
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
		DOTExporter<IState,ITransitionRule> simpleGraphExporter = new DOTExporter<>();
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
	
	private static  String buildGenDiffStringDesc(IGenusDifferentiaDefinition def) {
		StringBuilder sB = new StringBuilder();
		sB.append("Cost : ");
		sB.append(round(def.getCost()) + System.lineSeparator());
		for (IConjunctiveTransition conjTrans : def.getDifferentiae()) {
			sB.append(operatorAsString(conjTrans));
		}
		return sB.toString();
	}
	
	private static String operatorAsString(ITransitionRule transitionRule) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		if (!transitionRule.isBlank()) {
			if (transitionRule instanceof IConjunctiveTransition) {
				IConjunctiveTransition conjTrans = (IConjunctiveTransition) transitionRule;
				IReframerRule reframerRule = conjTrans.getReframer();
				if (reframerRule != null)
					sB.append("FRAME " + reframerRule.toString() +  nL);
				for (IBasicOperator operator : conjTrans.getOperators()) {
					sB.append(operatorAsString(operator) + nL);
				}
			}
			else if (transitionRule instanceof IBasicOperator) {
				IBasicOperator operator = (IBasicOperator) transitionRule;
				sB.append(operator.getName() + " : ");
				List<IProductionAsEdge> productionAsEdges = operator.operation();
				if (productionAsEdges.size() > 1)
					sB.append(nL);
				for (int i = 0 ; i < productionAsEdges.size() ; i++) {
					sB.append(productionAsEdges.get(i).toString());
					if (i < productionAsEdges.size() - 1)
						sB.append(nL);
				}
			}
			else if (transitionRule instanceof IReframerRule) {
				IReframerRule reframerRule = (IReframerRule) transitionRule;
				sB.append(reframerRule.getName());
			}
		}
		return sB.toString();
	}	
	
	private static String round(double nb) {
		return df.format(nb).toString();
	}
	
}
