package com.tregouet.occam.alg.builders.problem_spaces.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class GaloisLatticeOfRepresentationsTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;	
	private ICompleteRepresentations completeRepresentations;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);	
		completeRepresentations = GeneratorsAbstractFactory.INSTANCE.getRepresentationSortedSetBuilder().apply(context);
	}

	@Test
	public void whenProblemSpaceRequestedThenReturned() {
		//HERE
		for (ICompleteRepresentation representation : completeRepresentations.getSortedRepresentations()) {
			VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(
					representation.getDescription(), 
					"GaloisLatt_Desc" + Integer.toString(representation.id()));
		}
		//HERE
		IProblemSpace problemSpace = new GaloisLatticeOfRepresentations().apply(completeRepresentations);
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(problemSpace.asGraph(), "Problem_Space");
		assertNotNull(problemSpace);
	}

}
