package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

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

}
