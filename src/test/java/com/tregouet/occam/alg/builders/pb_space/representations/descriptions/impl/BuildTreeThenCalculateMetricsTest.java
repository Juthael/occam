package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.impl;

import static org.junit.Assert.*;

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
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.impl.BuildTreeThenCalculateMetrics;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

@SuppressWarnings("unused")
public class BuildTreeThenCalculateMetricsTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Map<IRepresentationTransitionFunction, InvertedTree<IConcept, IIsA>> transFunc2Tree = 
			new HashMap<IRepresentationTransitionFunction, InvertedTree<IConcept,IIsA>>();	

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
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			IRepresentationTransitionFunction transFunc = transFuncBldr.apply(tree, productions);
			transFunc2Tree.put(transFunc, tree);
		}
	}

	@Test
	public void whenDescriptionsRequestedThenReturned() {
		Set<IDescription> descriptions = new HashSet<>();
		int checkIdx = 0;
		for (IRepresentationTransitionFunction transFunc : transFunc2Tree.keySet()) {
			IDescription description = 
					BuildTreeThenCalculateMetrics.INSTANCE.apply(transFunc, transFunc2Tree.get(transFunc));
			/*
			String descriptionPath = 
					VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(
							description, "BuildTreeThenCalcTest_" + Integer.toString(checkIdx));
			System.out.println("Description graph n." + Integer.toString(checkIdx) + " available at" + descriptionPath);
			*/
			descriptions.add(description);
			checkIdx++;
		}
		assertTrue(descriptions.size() == transFunc2Tree.size());
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
