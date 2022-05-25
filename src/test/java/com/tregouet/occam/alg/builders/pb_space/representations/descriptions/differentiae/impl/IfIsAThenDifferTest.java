package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.impl.IfIsAThenDiffer;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
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
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Set<IRepresentationTransitionFunction> transFunctions = new HashSet<>();	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			transFunctions.add(transFuncBldr.apply(tree, productions));
		}
	}
	
	@Test
	public void whenDifferentiaeRequestedThenDifferentiaeGraphIsTree() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			IfIsAThenDiffer diffBldr = new IfIsAThenDiffer();
			Set<ADifferentiae> differentiae = diffBldr.apply(transFunc);
			DirectedAcyclicGraph<Integer, ADifferentiae> graph = new DirectedAcyclicGraph<>(null, null, false);
			for (ADifferentiae diff : differentiae) {
				graph.addVertex(diff.getSource());
				graph.addVertex(diff.getTarget());
				graph.addEdge(diff.getSource(), diff.getTarget(), diff);
			}
			/*
			String transFuncFullPath = 
					VisualizersAbstractFactory.INSTANCE.getTransitionFunctionViz().apply(
							transFunc, "IfIsAThenDifferTest_TF_" + Integer.toString(nbOfChecks));
			String descFullPath = visualize(graph, nbOfChecks);
			System.out.println("Transition function graph n." + Integer.toString(nbOfChecks) + " is available at " 
			+ transFuncFullPath);
			System.out.println("Description graph n." + Integer.toString(nbOfChecks) + " is available at " 
			+ descFullPath);
			*/
			if (!StructureInspector.isATree(graph))
				asExpected = false;
			nbOfChecks++;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
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
	
	private String label(ADifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		for (IProperty prop : differentiae.getProperties()) {
			sB.append(FormattersAbstractFactory.INSTANCE.getPropertyDisplayer().apply(prop) + nL);
		}
		return sB.toString();
	}
	
	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}	

}
