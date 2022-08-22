package com.tregouet.occam.alg.scorers.difference.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class TwoLeafTreeTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private List<Integer> extentIDs = new ArrayList<>();
	private IConceptLattice conceptLattice;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		for (IContextObject obj : context)
			extentIDs.add(obj.iD());
		extentIDs.sort((x, y) -> Integer.compare(x, y));
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
	}

	@Test
	public void whenDifferenceMatrixRequestedThenReturned() {
		int ctxtSize = context.size();
		double[][] differenceMatrix = new double[ctxtSize][ctxtSize];
		for (int i = 0 ; i < ctxtSize - 1 ; i++) {
			for (int j = i + 1 ; j < ctxtSize ; j++) {
				double difference =
						ScorersAbstractFactory.INSTANCE.getDifferenceScorer().score(
								extentIDs.get(i), extentIDs.get(j), conceptLattice);
				differenceMatrix[i][j] = difference;
				differenceMatrix[j][i] = difference;
			}
		}
		boolean asExpected = true;
		for (int i = 0 ; i < ctxtSize ; i++) {
			for (int j = 0 ; j < ctxtSize ; j++) {
				if (differenceMatrix[i][j] == 0 && i != j)
					asExpected = false;
			}
		}
		assertTrue(asExpected);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
