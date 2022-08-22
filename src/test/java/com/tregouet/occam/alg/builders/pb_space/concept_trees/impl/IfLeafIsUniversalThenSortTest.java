package com.tregouet.occam.alg.builders.pb_space.concept_trees.impl;

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
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class IfLeafIsUniversalThenSortTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		*/
	}

	@Test
	public void whenTreeExpansionRequestedThenProceeded() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		IfLeafIsUniversalThenSort grower = new IfLeafIsUniversalThenSort();
		expandedTreesFromLastIteration = new HashSet<>(grower.apply(conceptLattice, null).keySet());
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				grower = new IfLeafIsUniversalThenSort();
				expandedTreesFromLastIteration.addAll(new HashSet<>(grower.apply(conceptLattice, tree).keySet()));
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		int count = 0;
		for (InvertedTree<IConcept, IIsA> expTree : expandedTrees) {
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(expTree, "IfLeafIsUniversal_tree" + Integer.toString(count++));
		}
		*/
		assertTrue(!expandedTrees.isEmpty());
	}

	@Test
	public void whenTreeExpansionRequestedThenTheSameTreeCanBeBuiltManyTimes() {
		boolean asExpected = false;
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		IfLeafIsUniversalThenSort grower = new IfLeafIsUniversalThenSort();
		expandedTreesFromLastIteration = new HashSet<>(grower.apply(conceptLattice, null).keySet());
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				grower = new IfLeafIsUniversalThenSort();
				for (InvertedTree<IConcept, IIsA> returned : new HashSet<>(grower.apply(conceptLattice, tree).keySet())) {
					boolean newTree = expandedTreesFromLastIteration.add(returned);
					if (!newTree)
						asExpected = true;
				}
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		int count = 0;
		for (InvertedTree<IConcept, IIsA> expTree : expandedTrees) {
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(expTree, "IfLeafIsUniversal_tree" + Integer.toString(count++));
		}
		*/
		assertTrue(asExpected);
	}

	@Test
	public void whenTreeHasALeafWhichIsNotParticularThenCanBeExpandedOtherwiseCannot() {
		boolean asExpected = true;
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		IfLeafIsUniversalThenSort grower = new IfLeafIsUniversalThenSort();
		expandedTreesFromLastIteration = new HashSet<>(grower.apply(conceptLattice, null).keySet());
		int nbOfChecks = 0;
		do {
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> iTree : expandable) {
				boolean expectedExpansion = false;
				for (IConcept leaf : iTree.getLeaves()) {
					if (leaf.type() != ConceptType.PARTICULAR)
						expectedExpansion = true;
				}
				grower = new IfLeafIsUniversalThenSort();
				Set<InvertedTree<IConcept, IIsA>> iExpandedTrees = new HashSet<>(grower.apply(conceptLattice, iTree).keySet());
				if (expectedExpansion == iExpandedTrees.isEmpty())
					asExpected = false;
				expandedTreesFromLastIteration.addAll(iExpandedTrees);
				nbOfChecks++;
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		assertTrue(asExpected && nbOfChecks > 0);
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
