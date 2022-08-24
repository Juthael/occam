package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
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

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.classifications.impl.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.classifications.impl.Classification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.StructureInspector;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

@SuppressWarnings("unused")
public class IfIsAThenDifferTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Map<Set<IContextualizedProduction>, IClassification> classProd2Classification =	new HashMap<>();

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			boolean fullyDeveloped = isFullyDeveloped(tree);
			IClassification classification =
					new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID,
							conceptLattice.getParticularID2Particular(), fullyDeveloped);
			Set<IContextualizedProduction> classProds = BuildersAbstractFactory.INSTANCE.getProductionSetBuilder().apply(classification);
			classProd2Classification.put(classProds, classification);
		}
	}

	@Test
	public void whenDifferentiaeRequestedThenDifferentiaeGraphIsTree() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (Set<IContextualizedProduction> classProds : classProd2Classification.keySet()) {
			IClassification classification = classProd2Classification.get(classProds);
			IfIsAThenDiffer diffBldr = new IfIsAThenDiffer();
			Set<ADifferentiae> differentiae = diffBldr.apply(classification, classProds);
			DirectedAcyclicGraph<Integer, ADifferentiae> graph = new DirectedAcyclicGraph<>(null, null, false);
			for (ADifferentiae diff : differentiae) {
				graph.addVertex(diff.getSource());
				graph.addVertex(diff.getTarget());
				graph.addEdge(diff.getSource(), diff.getTarget(), diff);
			}
			/*
			String descFullPath = visualize(graph, nbOfChecks);
			System.out.println("Description graph n." + Integer.toString(nbOfChecks) + " is available at "
			+ descFullPath);
			*/
			if (!StructureInspector.isATree(graph))
				asExpected = false;
			nbOfChecks++;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}

	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration =
				new HashSet<>(BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null).keySet());
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(new HashSet<>(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree).keySet()));
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}

	private String label(ADifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		for (IProperty prop : differentiae.getProperties()) {
			sB.append(FormattersAbstractFactory.INSTANCE.getPropertyDisplayer().apply(prop) + nL);
		}
		return sB.toString();
	}

	private String visualize(DirectedAcyclicGraph<Integer, ADifferentiae> description, int idx) {
		String fileName = "IfIsAThenDifferTest_Desc_" + Integer.toString(idx);
		// convert in DOT format
		DOTExporter<Integer, ADifferentiae> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		exporter.setEdgeAttributeProvider((e) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(label(e)));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(description, writer);
		String dOTFile = writer.toString();
		// display graph
		try {
			MutableGraph dotGraph = new Parser().read(dOTFile);
			String filePath = LocalPaths.INSTANCE.getTargetFolderPath() + "\\" + fileName;
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

	private static boolean isFullyDeveloped(InvertedTree<IConcept, IIsA> conceptTree) {
		for (IConcept concept : conceptTree.getLeaves()) {
			if (concept.type() != ConceptType.PARTICULAR)
				return false;
		}
		return true;
	}

	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}

}
