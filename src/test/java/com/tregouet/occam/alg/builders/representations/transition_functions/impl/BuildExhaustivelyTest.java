package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

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
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class BuildExhaustivelyTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = GeneratorsAbstractFactory.INSTANCE.getConceptTreeBuilder().apply(conceptLattice);
	}

	@Test
	public void whenTransitionFunctionRequestedThenReturned() {
		boolean asExpected = true;
		BuildExhaustively transFuncBldr;
		int nbOfChecks = 0;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = new BuildExhaustively();
			IRepresentationTransitionFunction transFunc = transFuncBldr.apply(tree, productions);
			/*
			System.out.println(report(transFunc, tree, nbOfChecks));
			*/
			nbOfChecks++;
			if (transFunc == null)
				asExpected = false;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	@SuppressWarnings("unused")
	private String report(IRepresentationTransitionFunction transFunc, InvertedTree<IConcept, IIsA> tree, int idx) {
		StringBuilder sB = new StringBuilder();
		String treePath = 
				VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(
						tree, "BuildExhaustivelyTest_tree" + Integer.toString(idx));
		String tfPath = 
				VisualizersAbstractFactory.INSTANCE.getTransitionFunctionViz().apply(
						transFunc, "BuildExhaustivelyTest_TF" + Integer.toString(idx));
		sB.append("Concept tree n." + Integer.toString(idx) + " is available at : " + treePath + nL)
			.append("Transition function n." + Integer.toString(idx) + " is available at : " + tfPath);
		return sB.toString();
	}


}
