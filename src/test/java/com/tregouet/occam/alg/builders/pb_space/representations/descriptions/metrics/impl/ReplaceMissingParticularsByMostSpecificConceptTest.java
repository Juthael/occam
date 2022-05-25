package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.impl;

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
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class ReplaceMissingParticularsByMostSpecificConceptTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private ProblemSpaceExplorer pbSpaceExplorer;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpaceExplorer = GeneratorsAbstractFactory.INSTANCE.getProblemSpaceExplorer();
		pbSpaceExplorer.initialize(context);
		randomlyExpandPbSpace();
	}
	
	@Test
	public void whenSimilarityMatrixRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {
			IDescription description = representation.getDescription();
			ISimilarityMetrics metrics = description.getSimilarityMetrics();
			double[][] similarityMatrix = metrics.getSimilarityMatrix();
			if (similarityMatrix == null)
				asExpected = false;
			/*
			System.out.println(toString(similarityMatrix) + System.lineSeparator());
			*/
		}
		assertTrue(asExpected);
	}
	
	@Test
	public void whenAsymetricalSimilarityMatrixRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {			
			IDescription description = representation.getDescription();
			ISimilarityMetrics metrics = description.getSimilarityMetrics();
			double[][] asymmetricalSimilarityMatrix = metrics.getAsymmetricalSimilarityMatrix();
			if (asymmetricalSimilarityMatrix == null)
				asExpected = false;
			/*
			System.out.println(toString(asymmetricalSimilarityMatrix) + System.lineSeparator());
			*/
		}
		assertTrue(asExpected);
	}
	
	@Test
	public void whenTypicalityVectorRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {			
			IDescription description = representation.getDescription();
			ISimilarityMetrics metrics = description.getSimilarityMetrics();
			double[] typicalityVector = metrics.getTypicalityVector();
			if (typicalityVector == null)
				asExpected = false;
			/*
			System.out.println(Arrays.toString(typicalityVector) + System.lineSeparator());
			*/
		}
		assertTrue(asExpected);
	}	
	
	private void randomlyExpandPbSpace() {
		int maxNbOfIterations = 4;
		int maxNbOfSortingsAtEachIteration = 6;
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
