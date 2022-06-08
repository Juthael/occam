package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.tree_finder.data.Tree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class NormalizeClassificationThenBuildProductionsTest {
	
	private static final Path TABLETOP1B = Paths.get(".", "src", "test", "java", "files", "tabletop1b.txt");
	private List<IContextObject> context;
	private NormalizeClassificationThenBuildProductions pbSpaceExplorer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(OverallStrategy.OVERALL_STRATEGY_3);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(TABLETOP1B);
		pbSpaceExplorer = new NormalizeClassificationThenBuildProductions();
	}
	
	@Test
	public void whenPbSpaceExplorationProceededThenNoLeafStateIsTrivial() {
		boolean asExpected = true;
		pbSpaceExplorer.initialize(context);
		randomlyExpandPbSpace();
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbSpaceGraph = pbSpaceExplorer.getProblemSpaceGraph();
		Set<IRepresentation> pbStates = pbSpaceGraph.vertexSet();
		for (IRepresentation representation : pbStates) {
			if (pbSpaceGraph.outDegreeOf(representation) == 0) {
				IClassification classification = representation.getClassification();
				for (IConcept leaf : classification.getMostSpecificConcepts()) {
					if (classification.getExtentIDs(leaf.iD()).size() == 2)
						asExpected = false;
				}
			}
		}
		/*
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "AutomaticallyExpandTrivialLeaves");
		*/
		assertTrue(!pbStates.isEmpty() && asExpected);
	}

	private void randomlyExpandPbSpace() {
		int maxNbOfIterations = 5;
		int maxNbOfSortingsAtEachIteration = 8;
		int iterationIdx = 0;
		while (iterationIdx < maxNbOfIterations) {
			Set<IRepresentation> leaves = new HashSet<>();
			for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph().vertexSet()) {
				if (pbSpaceExplorer.getProblemSpaceGraph().outDegreeOf(representation) == 0)
					leaves.add(representation);
			}
			Iterator<IRepresentation> leafIte = leaves.iterator();
			int nbOfSortings = 0;
			while (leafIte.hasNext() && nbOfSortings < maxNbOfSortingsAtEachIteration) {
				IRepresentation leaf = leafIte.next();
				if (!leaf.isFullyDeveloped()) {
					pbSpaceExplorer.apply(leaf.iD());
					nbOfSortings ++;
				}
			}
			iterationIdx++;
		}
	}
	
	@SuppressWarnings("unused")
	private String visualize(Tree<Integer, ADifferentiae> descGraph, String fileName) {
		DifferentiaeLabeller diffDisplayer = FormattersAbstractFactory.INSTANCE.getDifferentiaeDisplayer();
		// convert in DOT format
		DOTExporter<Integer, ADifferentiae> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(toString(e)));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(descGraph, writer);
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
	
	private String toString(ADifferentiae differentiae) {
		String nl = System.lineSeparator();
		StringBuilder sB = new StringBuilder();
		for (IProperty prop : differentiae.getProperties()) {
			sB.append("***" + nl);
			sB.append("*Property : " + nl);
			sB.append("*function : " + prop.getFunction().toString() + nl);
			sB.append("*productions :" + nl);
			for (IContextualizedProduction production : prop.getProductions()) {
				sB.append(production.toString() + nl);
			}
			sB.append("*values : " + nl);
			for (IDenotation denotation : prop.getResultingValues()) {
				sB.append(denotation.toString() + nl);
			}
		}
		return sB.toString();
	}

}
