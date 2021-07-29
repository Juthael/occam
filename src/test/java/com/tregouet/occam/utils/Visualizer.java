package com.tregouet.occam.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.occam.data.categories.ICategory;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class Visualizer {

	private Visualizer() {
	}
	
	public static void visualize(DirectedAcyclicGraph<ICategory, DefaultEdge> graph, String fileName) throws IOException {
		//convert in DOT format
		DOTExporter<ICategory,DefaultEdge> exporter = new DOTExporter<>();
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
		Graphviz.fromGraph(dotGraph).width(graph.vertexSet().size()*100)
			.render(Format.PNG).toFile(new File("D:\\ProjetDocs\\essais_viz\\" + fileName));
	}
	

}
