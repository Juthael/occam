package com.tregouet.occam.alg.displayers.visualizers.transition_functions.impl;

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

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.alg.displayers.visualizers.transition_functions.TransitionFunctionViz;
import com.tregouet.occam.data.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.io.output.LocalPaths;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class BasicTransitionFunctionViz implements TransitionFunctionViz {

	public static final BasicTransitionFunctionViz INSTANCE = new BasicTransitionFunctionViz();

	private BasicTransitionFunctionViz() {
	}

	@Override
	public String apply(IRepresentationTransitionFunction transFunc, String fileName) {
		TransitionFunctionLabeller displayer = FormattersAbstractFactory.INSTANCE.getTransitionFunctionDisplayer();
		DirectedAcyclicGraph<Integer, AConceptTransitionSet> transFuncGraph = displayer.apply(transFunc);
		// convert in DOT format
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
		// display graph
		try {
			MutableGraph dotGraph = new Parser().read(dOTFile);
			String filePath = LocalPaths.INSTANCE.getTargetFolderPath() + "\\" + fileName;
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
			return filePath + ".png";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
