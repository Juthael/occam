package com.tregouet.occam.alg.builders.comparator.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.comparator.ComparatorSetter;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class SystemicPressureTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
	}

	@Test
	public void whenDichotomiesRequestedThenSetOfExpectedSizeReturned() {
		ComparatorSetter comparatorSetter = new SystemicPressure().accept(context);
		Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies = comparatorSetter.getDichotomies();
		int contextSize = context.size();
		int expectedNbOfComparisons = (contextSize * (contextSize - 1)) / 2;
		/*
		for (Entry<UnorderedPair<Integer, Integer>, IRepresentation> dichotomy : dichotomies.entrySet()) {
			UnorderedPair<Integer, Integer> comparedIDs = dichotomy.getKey();
			IRepresentation rep = dichotomy.getValue();
			VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(rep.getDescription(), "similarity_" + comparedIDs.getFirst().toString() + "_" + comparedIDs.getSecond().toString());
		}
		*/
		assertTrue(dichotomies.size() == expectedNbOfComparisons);
	}

	@Test
	public void whenDifferencesRequestedThenReturned() {
		ComparatorSetter comparatorSetter = new SystemicPressure().accept(context);
		Map<UnorderedPair<Integer, Integer>, IRepresentation> differences = comparatorSetter.getDifferences();
		int contextSize = context.size();
		int expectedNbOfComparisons = (contextSize * (contextSize - 1)) / 2;
		/*
		for (Entry<UnorderedPair<Integer, Integer>, IRepresentation> diff : differences.entrySet()) {
			UnorderedPair<Integer, Integer> comparedIDs = diff.getKey();
			IRepresentation rep = diff.getValue();
			VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(rep.getDescription(), "comparison_" + comparedIDs.getFirst().toString() + "_" + comparedIDs.getSecond().toString());
		}
		*/
		assertTrue(differences.size() == expectedNbOfComparisons);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
