package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.traverse.TopologicalOrderIterator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class RemoveMeaninglessTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private RemoveMeaningless pbSpaceExplorer;

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
	public void whenPbStatesRequestedThenReturned() {
		pbSpaceExplorer.initialize(context);
		randomlyExpandPbSpace();
		Set<IRepresentation> pbStates = pbSpaceExplorer.getProblemSpaceGraph().vertexSet();
		/*
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "RebuildFromScratchTest");
		*/
		assertTrue(!pbStates.isEmpty());
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
			iterationIdx++;
		}
	}

}
