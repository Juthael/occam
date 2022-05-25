package com.tregouet.occam.alg.builders.pb_space.concept_lattices.impl;

import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.impl.GaloisConnection;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class GaloisConnectionTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
	}

	@Test
	public void whenConceptLatticeRequestedThenReturned() {
		IConceptLattice returned = new GaloisConnection().apply(context);
		/*
		String path = 
				VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(returned.getLatticeOfConcepts(), "GaloisConnectionTest");
		System.out.println("Concept lattice graph available at " + path);
		*/
		assertNotNull(returned);
	}

}
