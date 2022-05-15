package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.formatters.problem_states.impl.AsNestedFrames;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl.NoLabel;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class RemoveMeaninglessTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private RebuildFromScratch pbSpaceExplorer;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpaceExplorer = new RemoveMeaningless();
	}

	@Test
	public void whenPbSpaceExplorationRequestedThenBuildNoMeaninglessState() {
		boolean asExpected = true;
		pbSpaceExplorer.initialize(context);
		Set<Integer> incompleteSortings = pbSpaceExplorer.getIDsOfRepresentationsWithIncompleteSorting();
		while (!incompleteSortings.isEmpty()) {
			for (Integer repID : incompleteSortings)
				pbSpaceExplorer.apply(repID);
			incompleteSortings = pbSpaceExplorer.getIDsOfRepresentationsWithIncompleteSorting();
		}
		//HERE
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "RebuildFromScratchTest");
		//HERE
		Set<IRepresentation> pbStates = pbSpaceExplorer.getProblemSpaceGraph().vertexSet();
		for (IRepresentation pbState : pbStates) {
			if (pbState.score().value() == 0.0)
				asExpected = false;
		}
		assertTrue(asExpected && !pbStates.isEmpty());
	}	
	
	private void randomlyExpandPbSpace() {
		int maxNbOfIterations = 5;
		int maxNbOfSortingsAtEachIteration = 8;
		int iterationIdx = 0;
		while (iterationIdx < maxNbOfIterations) {
			List<IRepresentation> topoOrderOverStates = new ArrayList<>();
			new TopologicalOrderIterator<>(pbSpaceExplorer.getProblemSpaceGraph())
				.forEachRemaining(topoOrderOverStates::add);
			topoOrderOverStates = new ArrayList<>(Lists.reverse(topoOrderOverStates));
			Iterator<IRepresentation> repIte = topoOrderOverStates.iterator();
			int nbOfSortings = 0;
			while (repIte.hasNext() && nbOfSortings < maxNbOfSortingsAtEachIteration) {
				IRepresentation rep = repIte.next();
				if (!rep.isFullyDeveloped()) {
					pbSpaceExplorer.apply(rep.iD());
					nbOfSortings ++;
				}
			}
			/*
			String path = visualize(pbSpaceExplorer.getProblemSpaceGraph(), "RebuildFromScratchTest_pb_graph");
			System.out.println("Problem space graph available at : " + path);
			*/
			iterationIdx++;
		}
	}	

}
