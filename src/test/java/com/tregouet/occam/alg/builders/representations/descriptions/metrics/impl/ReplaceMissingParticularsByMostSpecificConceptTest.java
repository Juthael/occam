package com.tregouet.occam.alg.builders.representations.descriptions.metrics.impl;

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
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorer;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class ReplaceMissingParticularsByMostSpecificConceptTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private ProblemSpaceExplorer pbSpaceExplorer;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpaceExplorer = BuildersAbstractFactory.INSTANCE.getProblemSpaceExplorer();
		pbSpaceExplorer.initialize(context);
		randomlyExpandPbSpace();
	}

	@Test
	public void whenAsymetricalSimilarityMatrixRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {
			IDescription description = representation.getDescription();
			IRelativeSimilarityMetrics metrics = description.getSimilarityMetricsDEP();
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
	public void whenSimilarityMatrixRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {
			IDescription description = representation.getDescription();
			IRelativeSimilarityMetrics metrics = description.getSimilarityMetricsDEP();
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
	public void whenTypicalityVectorRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : pbSpaceExplorer.getProblemSpaceGraph()) {
			IDescription description = representation.getDescription();
			IRelativeSimilarityMetrics metrics = description.getSimilarityMetricsDEP();
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
		int maxNbOfIterations = 3;
		int maxNbOfSortingsAtEachIteration = 5;
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
					pbSpaceExplorer.develop(leaf.iD());
					nbOfSortings ++;
				}
			}
			iterationIdx++;
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
