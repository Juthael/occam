package com.tregouet.occam.alg.displayers.formatters.sortings.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class RecursiveFramingTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Map<IRepresentationTransitionFunction, InvertedTree<IConcept, IIsA>> transFunc2Tree = 
			new HashMap<IRepresentationTransitionFunction, InvertedTree<IConcept,IIsA>>();
	private Set<IDescription> descriptions = new HashSet<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			IRepresentationTransitionFunction transFunc = transFuncBldr.apply(tree, productions);
			transFunc2Tree.put(transFunc, tree);
		}
		for (IRepresentationTransitionFunction transFunc : transFunc2Tree.keySet()) {
			descriptions.add(GeneratorsAbstractFactory.INSTANCE
					.getDescriptionBuilder().apply(transFunc, transFunc2Tree.get(transFunc)));
		}		
	}

	@Test
	public void whenStringPatternRequestedThenEachDescriptionOfContextGeneratesDistinctPattern() {
		boolean asExpected = true;
		RecursiveFraming patternBldr;
		Set<String> patterns = new HashSet<>();
		int checkIdx = 0;
		for (IDescription description : descriptions) {
			patternBldr = new RecursiveFraming();
			String pattern = patternBldr.apply(description.asGraph());
			/*
			System.out.println("Pattern n." + Integer.toString(idx) + " : " + pattern);
			*/
			if (!patterns.add(pattern))
				asExpected = false;
			checkIdx++;
		}
		assertTrue(checkIdx > 0 && asExpected);
	}
	
	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = GeneratorsAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						GeneratorsAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}

}
