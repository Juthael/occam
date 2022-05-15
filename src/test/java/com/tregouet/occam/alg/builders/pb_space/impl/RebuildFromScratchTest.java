package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class RebuildFromScratchTest {
	
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
		pbSpaceExplorer = new RebuildFromScratch();
	}

	@Test
	public void whenPbSpaceExplorationRequestedThenProceeded() {
		boolean asExpected = true;
		pbSpaceExplorer.initialize(context);
		Set<Integer> incompleteSortings = pbSpaceExplorer.getIDsOfRepresentationsWithIncompleteSorting();
		int nbOfIterations = 2;
		int iterationIdx = 0;
		while (!incompleteSortings.isEmpty() && iterationIdx < nbOfIterations) {
			for (Integer repID : incompleteSortings)
				pbSpaceExplorer.apply(repID);
			//HERE
			VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "RebuildFromScratchTest");
			//HERE
			incompleteSortings = pbSpaceExplorer.getIDsOfRepresentationsWithIncompleteSorting();
			iterationIdx++;
		}
		Set<IRepresentation> pbStates = pbSpaceExplorer.getProblemSpaceGraph().vertexSet();
		for (IRepresentation pbState : pbStates) {
			if (!pbState.isFullyDeveloped())
				asExpected = false;
		}
		assertTrue(asExpected && !pbStates.isEmpty());
	}

}
