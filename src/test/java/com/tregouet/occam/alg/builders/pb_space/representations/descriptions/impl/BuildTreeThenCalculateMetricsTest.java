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
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.impl.BuildTreeThenCalculateMetrics;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
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
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = growTrees();
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			IRepresentationTransitionFunction transFunc = transFuncBldr.apply(tree, productions);
			transFunc2Tree.put(transFunc, tree);
		}
	}

	@Test
	public void whenDescriptionsRequestedThenReturned() {
		Set<IDescription> descriptions = new HashSet<>();
		int checkIdx = 0;
		for (IRepresentationTransitionFunction transFunc : transFunc2Tree.keySet()) {
			Map<Integer, Integer> contextParticularID2MostSpecificConceptID = 
					mapContextParticularID2MostSpecificConceptID(transFunc2Tree.get(transFunc));
			IDescription description = 
					BuildTreeThenCalculateMetrics.INSTANCE.apply(transFunc, contextParticularID2MostSpecificConceptID);
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
	
	private Map<Integer, Integer> mapContextParticularID2MostSpecificConceptID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> particularID2MostSpecificConceptID = new HashMap<>();
		for (IConcept particular : conceptLattice.getOntologicalUpperSemilattice().getLeaves())
			particularID2MostSpecificConceptID.put(particular.iD(), mostSpecificConceptInTree(particular, conceptTree));
		return particularID2MostSpecificConceptID;
	}
	
	private static Integer mostSpecificConceptInTree(IConcept particular, InvertedTree<IConcept, IIsA> conceptTree) {
		if (conceptTree.containsVertex(particular))
			return particular.iD();
		Integer particularID = particular.iD();
		for (IConcept leaf : conceptTree.getLeaves()) {
			if (leaf.getExtentIDs().contains(particularID))
				return leaf.iD();
		}
		return null; //never happens
	}		

}
