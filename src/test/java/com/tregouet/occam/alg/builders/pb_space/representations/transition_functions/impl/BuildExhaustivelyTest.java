package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
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
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = growTrees();
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
	
	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}	

}
