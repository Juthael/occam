package com.tregouet.occam.alg.builders.pb_space.representations.partitions.as_graphs.impl;

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
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.builder.impl.RecursiveForkExploration;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveForkExplorationTest {
	
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
		growTrees();
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
	public void whenPartitionGraphsRequestedThenValidTreesReturned() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IDescription description : descriptions) {
			Set<Tree<Integer, AbstractDifferentiae>> partitionGraphs = RecursiveForkExploration.INSTANCE.apply(description.asGraph());
			if (partitionGraphs == null || partitionGraphs.isEmpty())
				asExpected = false;
			for (Tree<Integer, AbstractDifferentiae> graph : partitionGraphs) {
				try {
					graph.validate();
				}
				catch (Exception e) {
					asExpected = false;
				}
			}
			nbOfChecks++;
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	private void growTrees() {
		trees = GeneratorsAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		boolean newTreesBuilt = true;
		Set<InvertedTree<IConcept, IIsA>> previouslyFoundTrees = new HashSet<>();
		previouslyFoundTrees.addAll(trees);
		while (newTreesBuilt) {
			Set<InvertedTree<IConcept, IIsA>> foundTrees = new HashSet<>();
			for (InvertedTree<IConcept, IIsA> tree : previouslyFoundTrees)
				foundTrees.addAll(GeneratorsAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree));
			newTreesBuilt = !(foundTrees.isEmpty());
			trees.addAll(foundTrees);
			previouslyFoundTrees = foundTrees;
		}
	}	

}
