package com.tregouet.occam.alg.builders.pb_space.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
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

@SuppressWarnings("unused")
public class RemoveUninformativeTest {
	
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
		pbSpaceExplorer = new RemoveUninformative();
	}

	@Test
	public void whenPbSpaceExplorationRequestedThenBuildNoUninformativeState() {
		boolean asExpected = true;
		pbSpaceExplorer.initialize(context);
		randomlyExpandPbSpace();
		Set<IRepresentation> pbStates = pbSpaceExplorer.getProblemSpaceGraph().vertexSet();
		for (IRepresentation representation : pbStates) {
			/*
			System.out.println("score " + Integer.toString(representation.iD()) + " : " + Double.toString(representation.score().value()));
			*/
			if (representation.score().value() == 0.0)
				asExpected = false;
		}
		/*
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpaceExplorer.getProblemSpaceGraph(), "RemoveMeaninglessTest");
		*/
		assertTrue(!pbStates.isEmpty() && asExpected);
	}	
	
	private void randomlyExpandPbSpace() {
		int maxNbOfIterations = 5;
		int maxNbOfSortingsAtEachIteration = 15;
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
