package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.*;

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

import com.google.common.collect.Sets;
import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.transitions.IApplication;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

@SuppressWarnings("unused")
public class AutomaticallyExpandTrivialLeavesTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private RemoveUninformative pbSpaceExplorer;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpaceExplorer = new AutomaticallyExpandTrivialLeaves();
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
				InvertedTree<IConcept, IIsA> conceptTree = representation.getTreeOfConcepts();
				Set<IConcept> conceptLeaves = conceptTree.getLeaves();
				for (IConcept leaf : conceptLeaves) {
					if (leaf.getExtentIDs().size() == 2)
						asExpected = false;
				}
			}
		}
		/*
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "AutomaticallyExpandTrivialLeaves");
		*/
		assertTrue(!pbStates.isEmpty() && asExpected);
	}	
	
	@Test
	public void debuggingTest() throws IOException {
		Path TABLETOP1B = Paths.get(".", "src", "test", "java", "files", "tabletop1b.txt");
		List<IContextObject> context = GenericFileReader.getContextObjects(TABLETOP1B);
		pbSpaceExplorer.initialize(context);
		String nl = System.lineSeparator();
		pbSpaceExplorer.apply(1);
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(
				pbSpaceExplorer.getProblemSpaceGraph(), "AutomaticallyExpandTrivialLeavesTest_debugging");
		StringBuilder sB = new StringBuilder();
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph().vertexSet()) {
			sB.append("Representation n." + representation.iD() + " : " + nl);
			for (IPartition partition : representation.getPartitions()) {
				sB.append(FormattersAbstractFactory.INSTANCE.getSorting2StringConverter().apply(partition.asGraph()) + nl);
			}
			sB.append(nl + nl);
		}
		IRepresentation r6 = pbSpaceExplorer.getRepresentationWithID(6);
		IRepresentation r14 = pbSpaceExplorer.getRepresentationWithID(14);
		IRepresentation r15 = pbSpaceExplorer.getRepresentationWithID(15);
		IRepresentation r16 = pbSpaceExplorer.getRepresentationWithID(16);
		Integer compare16to6 = r16.compareTo(r6);
		Integer compare16to14 = r16.compareTo(r14);
		Integer compare16to15 = r16.compareTo(r15);
		sB.append("comparison 16, 6 : ");
		sB.append(compare16to6 == null ? "uncomparable" : compare16to6);
		sB.append(nl);
		sB.append(nl + "*****" + nl);
		sB.append("comparison 16, 14 : ");
		sB.append(compare16to14 == null ? "uncomparable" : compare16to14);
		sB.append(nl);
		sB.append("16 / 14 : ");
		int idx16minus14 = 0;
		for (IPartition partition : Sets.difference(r16.getPartitions(), r14.getPartitions())) {
			sB.append(partition.toString() + " ");
			visualize(partition.asGraph(), "16minus14" + idx16minus14++);
		}
		sB.append(nl);
		sB.append("14 / 16 : ");
		int idx14minus16 = 0;
		for (IPartition partition : Sets.difference(r14.getPartitions(), r16.getPartitions())) {
			sB.append(partition.toString() + " ");
			visualize(partition.asGraph(), "14minus16" + idx14minus16++);
		}
		sB.append(nl);
		sB.append(nl + "*****" + nl);
		sB.append("comparison 16, 15 : ");
		sB.append(compare16to15 == null ? "uncomparable" : compare16to15);
		sB.append(nl);
		sB.append("16 / 15 : ");
		for (IPartition partition : Sets.difference(r16.getPartitions(), r15.getPartitions())) {
			sB.append(partition.toString() + " ");
		}
		sB.append(nl);
		sB.append("15 / 16 : ");
		for (IPartition partition : Sets.difference(r15.getPartitions(), r16.getPartitions())) {
			sB.append(partition.toString() + " ");
		}
		sB.append(nl);
		sB.append(nl + "*****" + nl);
		System.out.println(sB.toString());
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
			sB.append("*applications :" + nl);
			for (IApplication application : prop.getApplications()) {
				sB.append(application.toString() + nl);
			}
			sB.append("*values : " + nl);
			for (IDenotation denotation : prop.getResultingValues()) {
				sB.append(denotation.toString() + nl);
			}
		}
		return sB.toString();
	}

}
