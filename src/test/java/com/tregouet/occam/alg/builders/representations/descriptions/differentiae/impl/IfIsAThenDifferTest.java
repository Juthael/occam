package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

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
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.graph_labellers.LabellersAbstractFactory;
import com.tregouet.occam.alg.displayers.graph_visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
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
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = GeneratorsAbstractFactory.INSTANCE.getConceptTreeBuilder().apply(conceptLattice);
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			transFunctions.add(transFuncBldr.apply(tree, productions));
		}
	}
	
	@Test
	public void whenDifferentiaeRequestedThenDifferentiaeGraphIsTree() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			IfIsAThenDiffer diffBldr = new IfIsAThenDiffer();
			Set<AbstractDifferentiae> differentiae = diffBldr.apply(transFunc);
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> graph = new DirectedAcyclicGraph<>(null, null, false);
			for (AbstractDifferentiae diff : differentiae) {
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
	
	private String visualize(DirectedAcyclicGraph<Integer, AbstractDifferentiae> description, int idx) {
		String fileName = "IfIsAThenDifferTest_Desc_" + Integer.toString(idx);
		// convert in DOT format
		DOTExporter<Integer, AbstractDifferentiae> exporter = new DOTExporter<>();
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
	
	private String label(AbstractDifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		for (IProperty prop : differentiae.getProperties()) {
			sB.append(LabellersAbstractFactory.INSTANCE.getPropertyDisplayer().apply(prop) + nL);
		}
		return sB.toString();
	}

}
