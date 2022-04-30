package com.tregouet.occam.alg.builders.representations.descriptions.metrics.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class DeferredMatricesInstantiationTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private ICompleteRepresentations compRep;
	private IProblemSpace problemSpace;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		compRep = GeneratorsAbstractFactory.INSTANCE.getRepresentationSortedSetBuilder().apply(context);
		problemSpace = GeneratorsAbstractFactory.INSTANCE.getProblemSpaceBuilder().apply(compRep);
		for (IRepresentation representation : problemSpace.getSortedSetOfStates()) {
			if (representation instanceof IPartialRepresentation)
				GeneratorsAbstractFactory.INSTANCE
					.getPartialRepresentationLateSetter()
					.accept((IPartialRepresentation) representation);
		}
	}

	@Test
	public void whenSimilarityMatrixRequestedThenReturned() {
		boolean asExpected = true;
		for (IRepresentation representation : problemSpace.getSortedSetOfStates()) {
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
		for (IRepresentation representation : problemSpace.getSortedSetOfStates()) {			
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
		for (IRepresentation representation : problemSpace.getSortedSetOfStates()) {			
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
	
	private String toString(double[][] array) {
		StringBuilder sB = new StringBuilder();
		sB.append("{" + nL);
		for (double[] row : array) {
			sB.append(Arrays.toString(row) + nL);
		}
		sB.append("}" + nL);
		return sB.toString();
	}

}
